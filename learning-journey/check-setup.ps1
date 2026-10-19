# Run this every time you switch machines, right after "git pull".
#   powershell -ExecutionPolicy Bypass -File .\check-setup.ps1
# It checks every tool, then installs the exact TypeScript packages from the lockfile.

$root = $PSScriptRoot
$allGood = $true

function Test-Tool($label, $command, $fixHint) {
    $exe = $command.Split(" ")[0]
    if (Get-Command $exe -ErrorAction SilentlyContinue) {
        $out = (Invoke-Expression "$command 2>&1" | Where-Object { "$_" -notmatch "^Picked up" } | Select-Object -First 1)
        Write-Host "[OK]      $label  $out" -ForegroundColor Green
        return $true
    } else {
        Write-Host "[MISSING] $label  ->  $fixHint" -ForegroundColor Red
        return $false
    }
}

Write-Host ""
Write-Host "Checking tools..." -ForegroundColor Cyan

if (-not (Test-Tool "Git    " "git --version" "winget install Git.Git")) { $allGood = $false }
if (-not (Test-Tool "fnm    " "fnm --version" "winget install Schniz.fnm (see SETUP.md)")) { $allGood = $false }
if (-not (Test-Tool "Rust   " "rustc --version" "install from https://rustup.rs")) { $allGood = $false }
if (-not (Test-Tool "Cargo  " "cargo --version" "comes with Rust, reinstall from https://rustup.rs")) { $allGood = $false }
if (-not (Test-Tool "Java   " "java -version" "install the same JDK you use on your other machine")) { $allGood = $false }
if (-not (Test-Tool "javac  " "javac -version" "JDK missing or not on PATH")) { $allGood = $false }

# Node must match the version pinned in .nvmrc
$wanted = (Get-Content (Join-Path $root ".nvmrc") -Raw).Trim()
$nodeOk = $false
if (Get-Command node -ErrorAction SilentlyContinue) {
    $have = (node -v).TrimStart("v")
    $haveMajor = $have.Split(".")[0]
    if ($haveMajor -eq $wanted) {
        Write-Host "[OK]      Node     v$have (pinned: $wanted)" -ForegroundColor Green
        $nodeOk = $true
    } else {
        Write-Host "[WRONG]   Node     v$have but this repo wants $wanted  ->  run: fnm install; fnm use" -ForegroundColor Yellow
        $allGood = $false
    }
} else {
    Write-Host "[MISSING] Node     ->  run: fnm install $wanted" -ForegroundColor Red
    $allGood = $false
}

# Install exact TypeScript packages from package-lock.json
if ($nodeOk) {
    $ts = Join-Path $root "typescript"
    $lock = Join-Path $ts "package-lock.json"
    $stamp = Join-Path $ts "node_modules\.lock-hash"
    $hash = (Get-FileHash $lock -Algorithm SHA256).Hash
    $needInstall = (-not (Test-Path $stamp)) -or ((Get-Content $stamp -Raw).Trim() -ne $hash)
    if ($needInstall) {
        Write-Host ""
        Write-Host "TypeScript packages changed or missing. Running npm ci..." -ForegroundColor Cyan
        Push-Location $ts
        npm ci
        $installed = ($LASTEXITCODE -eq 0)
        Pop-Location
        if ($installed) { Set-Content -Path $stamp -Value $hash } else { $allGood = $false }
    } else {
        Write-Host "[OK]      TypeScript packages are up to date" -ForegroundColor Green
    }
}

# Let rustup install anything rust-toolchain.toml asks for
if (Get-Command cargo -ErrorAction SilentlyContinue) {
    Push-Location (Join-Path $root "rust")
    cargo --version | Out-Null
    Pop-Location
}

Write-Host ""
if ($allGood) {
    Write-Host "All set. Happy coding." -ForegroundColor Green
} else {
    Write-Host "Fix the items above, then run this script again." -ForegroundColor Yellow
}
