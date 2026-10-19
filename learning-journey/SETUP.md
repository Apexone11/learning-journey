# Setup Guide (do this once on EACH machine)

Goal: the laptop and the main computer have the exact same tools, so code
written on one always runs on the other.

## 1. One time installs (Windows, PowerShell)

Open **PowerShell** (not as admin) and run:

```powershell
winget install Git.Git
winget install Schniz.fnm
winget install Microsoft.VisualStudioCode
```

Close PowerShell and open a new one.

### Make fnm switch Node automatically

fnm is a Node version manager. It reads the `.nvmrc` file in this repo and
uses that exact Node version, so both machines always match.

```powershell
if (!(Test-Path $PROFILE)) { New-Item $PROFILE -Force }
Add-Content $PROFILE 'fnm env --use-on-cd --version-file-strategy=recursive --shell powershell | Out-String | Invoke-Expression'
```

Close PowerShell and open a new one again.

### Install the pinned Node version

```powershell
cd path\to\learning-journey
fnm install
fnm use
node -v
```

`node -v` should start with **v24**.

### Rust and Java

* Rust: install from https://rustup.rs (you already know this part).
* Java: use the **same JDK version** on both machines. Check with `java -version`.

### VS Code extensions

Open the repo folder in VS Code. It will pop up "install recommended
extensions". Click **Install**. (rust-analyzer and the TOML and EditorConfig helpers.)

## 2. First time on a machine

```powershell
git clone https://github.com/YOUR_USERNAME/learning-journey.git
cd learning-journey
powershell -ExecutionPolicy Bypass -File .\check-setup.ps1
```

Everything should say **[OK]**.

## 3. EVERY time you switch machines

```powershell
git pull
powershell -ExecutionPolicy Bypass -File .\check-setup.ps1
```

Then code. When you are done:

```powershell
git add .
git commit -m "what you did"
git push
```

**Golden rule: pull before you start, push before you leave.**

## 4. Adding a TypeScript package (rare)

Only ever add packages from inside the `typescript` folder:

```powershell
cd typescript
npm install -D package-name
```

Then commit **both** `package.json` and `package-lock.json`. The other
machine picks it up automatically the next time you run `check-setup.ps1`.

Never commit `node_modules` (the .gitignore already blocks it).

## 5. Running TypeScript

```powershell
cd typescript
npx tsx src/hello.ts      # run a file
npm run check             # type check everything in src/
```

## Why each file exists

| File | What it does |
|---|---|
| `.nvmrc` | Pins Node to version 24 on both machines |
| `typescript/package-lock.json` | Pins the exact TypeScript and tool versions |
| `rust/rust-toolchain.toml` | Pins the Rust toolchain plus rustfmt and clippy |
| `.gitattributes` | Same line endings everywhere, so Git never shows fake changes |
| `.editorconfig` | Same indentation in every editor |
| `.gitignore` | Keeps build junk (node_modules, target, out) off GitHub |
| `check-setup.ps1` | Checks everything and fixes the TypeScript packages for you |
