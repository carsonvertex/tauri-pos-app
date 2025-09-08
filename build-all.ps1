#!/usr/bin/env pwsh

Write-Host "Building Complete Tauri POS Application..." -ForegroundColor Green
Write-Host "===========================================" -ForegroundColor Green
Write-Host ""

# Auto-detect environment functions
function Find-Java {
    if ($env:JAVA_HOME -and (Test-Path "$env:JAVA_HOME\bin\java.exe")) {
        try {
            $version = & "$env:JAVA_HOME\bin\java.exe" -version 2>&1 | Select-String "version"
            if ($version -match "17|18|19|20|21|22|23|24") {
                return $env:JAVA_HOME
            }
        } catch { }
    }
    
    try {
        $version = java -version 2>&1 | Select-String "version"
        if ($version -match "17|18|19|20|21|22|23|24") {
            $javaPath = (Get-Command java -ErrorAction SilentlyContinue).Source
            $javaHome = Split-Path (Split-Path $javaPath -Parent) -Parent
            return $javaHome
        }
    } catch { }
    
    $searchPaths = @(
        "$env:USERPROFILE\.jdks",
        "$env:PROGRAMFILES\Java",
        "$env:PROGRAMFILES(X86)\Java",
        "$env:LOCALAPPDATA\Programs\Java"
    )
    
    foreach ($basePath in $searchPaths) {
        if (Test-Path $basePath) {
            $javaDirs = Get-ChildItem -Path $basePath -Directory -ErrorAction SilentlyContinue | Where-Object { 
                $_.Name -match "(jdk|jre|corretto|openjdk|ms-)" -and 
                (Test-Path "$($_.FullName)\bin\java.exe")
            }
            
            foreach ($javaDir in $javaDirs) {
                try {
                    $version = & "$($javaDir.FullName)\bin\java.exe" -version 2>&1 | Select-String "version"
                    if ($version -match "17|18|19|20|21|22|23|24") {
                        return $javaDir.FullName
                    }
                } catch { continue }
            }
        }
    }
    return $null
}

function Find-Maven {
    try {
        $version = mvn -version 2>&1 | Select-String "Apache Maven"
        if ($version) {
            $mvnPath = (Get-Command mvn -ErrorAction SilentlyContinue).Source
            $mavenHome = Split-Path (Split-Path $mvnPath -Parent) -Parent
            return "$mavenHome\bin"
        }
    } catch { }
    
    $searchPaths = @(
        "$env:USERPROFILE\maven",
        "$env:PROGRAMFILES\Apache\maven",
        "$env:PROGRAMFILES(X86)\Apache\maven"
    )
    
    foreach ($basePath in $searchPaths) {
        if (Test-Path $basePath) {
            $mavenDirs = Get-ChildItem -Path $basePath -Directory -ErrorAction SilentlyContinue | Where-Object { 
                $_.Name -match "apache-maven" -and 
                (Test-Path "$($_.FullName)\bin\mvn.cmd")
            }
            
            foreach ($mavenDir in $mavenDirs) {
                try {
                    $version = & "$($mavenDir.FullName)\bin\mvn.cmd" -version 2>&1 | Select-String "Apache Maven"
                    if ($version) {
                        return "$($mavenDir.FullName)\bin"
                    }
                } catch { continue }
            }
        }
    }
    return $null
}

function Find-Rust {
    try {
        $version = cargo --version 2>&1
        if ($version -match "cargo") {
            $cargoPath = (Get-Command cargo -ErrorAction SilentlyContinue).Source
            $rustBin = Split-Path $cargoPath -Parent
            return $rustBin
        }
    } catch { }
    
    $searchPaths = @(
        "$env:USERPROFILE\.cargo\bin",
        "$env:PROGRAMFILES\Rust stable MSVC 64-bit\bin",
        "$env:PROGRAMFILES(X86)\Rust stable MSVC 64-bit\bin"
    )
    
    foreach ($rustPath in $searchPaths) {
        if (Test-Path "$rustPath\cargo.exe") {
            return $rustPath
        }
    }
    return $null
}

# Auto-detect and set environment
Write-Host "Auto-detecting environment..." -ForegroundColor Cyan

$javaHome = Find-Java
$mavenBin = Find-Maven
$rustBin = Find-Rust

if ($javaHome -and $mavenBin -and $rustBin) {
    $env:JAVA_HOME = $javaHome
    $env:PATH = "$javaHome\bin;$mavenBin;$rustBin;$env:PATH"
    Write-Host "Environment auto-detected successfully" -ForegroundColor Green
} else {
    Write-Host "Auto-detection failed. Using fallback..." -ForegroundColor Yellow
    $env:JAVA_HOME = "$env:USERPROFILE\.jdks\corretto-17.0.15"
    $env:PATH = "$env:JAVA_HOME\bin;$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
    Write-Host "Using fallback configuration" -ForegroundColor Green
}

Write-Host ""
Write-Host "Verifying tools..." -ForegroundColor Cyan

# Verify all tools
$tools = @(
    @{Name="Node.js"; Command="node"},
    @{Name="npm"; Command="npm"},
    @{Name="Java"; Command="java"},
    @{Name="Maven"; Command="mvn"},
    @{Name="Rust/Cargo"; Command="cargo"}
)

foreach ($tool in $tools) {
    try {
        $version = & $tool.Command --version 2>&1 | Select-String -Pattern "version|npm|cargo" | Select-Object -First 1
        if ($version) {
            Write-Host "✓ $($tool.Name): $version" -ForegroundColor Green
        } else {
            Write-Host "✓ $($tool.Name): Found" -ForegroundColor Green
        }
    } catch {
        Write-Host "❌ $($tool.Name) not found!" -ForegroundColor Red
        exit 1
    }
}

Write-Host ""
Write-Host "Starting build process..." -ForegroundColor Green
Write-Host ""

# Step 1: Build Frontend
Write-Host "Step 1: Building Frontend..." -ForegroundColor Yellow
npm run frontend:build
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Frontend build failed!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Frontend build successful!" -ForegroundColor Green
Write-Host ""

# Step 2: Build Backend
Write-Host "Step 2: Building Backend..." -ForegroundColor Yellow
Set-Location backend
mvn clean package
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Backend build failed!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Backend build successful!" -ForegroundColor Green
Set-Location ..
Write-Host ""

# Step 3: Build Tauri
Write-Host "Step 3: Building Tauri Application..." -ForegroundColor Yellow
npm run tauri:build
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Tauri build failed!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Tauri build successful!" -ForegroundColor Green
Write-Host ""

# Step 4: Update dist-standalone
Write-Host "Step 4: Updating dist-standalone..." -ForegroundColor Yellow
if (Test-Path "dist-standalone") {
    # Copy backend JAR
    $jarFile = Get-ChildItem -Path "backend\target" -Filter "*.jar" | Where-Object { $_.Name -notlike "*-sources.jar" -and $_.Name -notlike "*-javadoc.jar" } | Select-Object -First 1
    if ($jarFile) {
        Copy-Item $jarFile.FullName "dist-standalone\backend.jar" -Force
        Write-Host "✓ Backend JAR updated" -ForegroundColor Green
    }
    
    # Copy Tauri executable
    $tauriExe = Get-ChildItem -Path "src-tauri\target\release" -Filter "*.exe" | Select-Object -First 1
    if ($tauriExe) {
        Copy-Item $tauriExe.FullName "dist-standalone\TauriPOS.exe" -Force
        Write-Host "✓ Tauri executable updated" -ForegroundColor Green
    }
} else {
    Write-Host "⚠️  dist-standalone folder not found, skipping update" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "🎉 Build completed successfully!" -ForegroundColor Green
Write-Host "=================================" -ForegroundColor Green
Write-Host ""
Write-Host "Build outputs:" -ForegroundColor Cyan
Write-Host "• Frontend: frontend/dist/" -ForegroundColor White
Write-Host "• Backend: backend/target/tauri-pos-app-0.1.0.jar" -ForegroundColor White
Write-Host "• Tauri: src-tauri/target/release/" -ForegroundColor White
if (Test-Path "dist-standalone") {
    Write-Host "• Standalone: dist-standalone/ (updated)" -ForegroundColor White
}
Write-Host ""
Write-Host "To test the standalone version:" -ForegroundColor Yellow
Write-Host "cd dist-standalone && start-pos.bat" -ForegroundColor White
