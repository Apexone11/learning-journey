# Upgrades every dev tool on this machine to its latest version.
# Run it in PowerShell AS ADMINISTRATOR, from the learning-journey folder:
#   powershell -ExecutionPolicy Bypass -File .\upgrade-tools.ps1
# Safe to run again any time. Close IntelliJ and VS Code first.

function Step($text) { Write-Host ""; Write-Host "==> $text" -ForegroundColor Cyan }

Step "1/5  Upgrading everything winget manages (Git, VS Code, PowerShell, Wireshark, and more)"
winget source update
winget upgrade --all --include-unknown --accept-source-agreements --accept-package-agreements --silent

Step "2/5  Java: installing Temurin JDK 25 (LTS, auto updates through winget from now on)"
winget install --id EclipseAdoptium.Temurin.25.JDK --accept-source-agreements --accept-package-agreements --silent
Write-Host "Optional: the newest (non LTS) Java 27 is available with:" -ForegroundColor DarkGray
Write-Host "  winget install EclipseAdoptium.Temurin.27.JDK" -ForegroundColor DarkGray

Step "3/5  Rust: updating rustup and the stable toolchain"
if (Get-Command rustup -ErrorAction SilentlyContinue) {
    rustup self update
    rustup update stable
} else {
    Write-Host "rustup not found. Install from https://rustup.rs" -ForegroundColor Yellow
}

Step "4/5  Node.js: installing the version pinned in .nvmrc (Node 26 LTS)"
if (Get-Command fnm -ErrorAction SilentlyContinue) {
    Push-Location $PSScriptRoot
    fnm install
    fnm use
    fnm default (Get-Content .nvmrc -Raw).Trim()
    Pop-Location
} else {
    Write-Host "fnm not found. Run: winget install Schniz.fnm" -ForegroundColor Yellow
}

Step "5/5  TypeScript packages: refreshing to the newest compatible versions"
if (Get-Command npm -ErrorAction SilentlyContinue) {
    Push-Location (Join-Path $PSScriptRoot "typescript")
    npm install -D typescript@latest tsx@latest "@types/node@^26"
    Pop-Location
}

Step "Done. Versions now:"
foreach ($c in @("git --version", "java -version", "rustc --version", "cargo --version", "node -v", "npm -v")) {
    $exe = $c.Split(" ")[0]
    if (Get-Command $exe -ErrorAction SilentlyContinue) {
        $out = Invoke-Expression "$c 2>&1" | Select-Object -First 1
        Write-Host ("  {0,-8} {1}" -f $exe, $out)
    }
}
Write-Host ""
Write-Host "Next: commit the changed package.json, package-lock.json and .nvmrc, then push." -ForegroundColor Green
Write-Host "On the other computer: git pull, then run this same script there." -ForegroundColor Green
