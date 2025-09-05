#!/usr/bin/env pwsh

Write-Host "Starting Tauri POS App with Backend..." -ForegroundColor Green

# Auto-detect environment using integrated functions
Write-Host "Auto-detecting environment..." -ForegroundColor Cyan

# Load detection functions from setup GUI
$setupScript = Join-Path $PSScriptRoot "setup-gui.ps1"
if (Test-Path $setupScript) {
    # Extract just the detection functions without running the GUI
    $detectionCode = @"
function Find-Java {
    if (`$env:JAVA_HOME -and (Test-Path "`$env:JAVA_HOME\bin\java.exe")) {
        try {
            `$version = & "`$env:JAVA_HOME\bin\java.exe" -version 2>&1 | Select-String "version"
            if (`$version -match "17|18|19|20|21|22|23|24") {
                return `$env:JAVA_HOME
            }
        } catch { }
    }
    
    try {
        `$version = java -version 2>&1 | Select-String "version"
        if (`$version -match "17|18|19|20|21|22|23|24") {
            `$javaPath = (Get-Command java -ErrorAction SilentlyContinue).Source
            `$javaHome = Split-Path (Split-Path `$javaPath -Parent) -Parent
            return `$javaHome
        }
    } catch { }
    
    `$searchPaths = @(
        "`$env:USERPROFILE\.jdks",
        "`$env:PROGRAMFILES\Java",
        "`$env:PROGRAMFILES(X86)\Java",
        "`$env:LOCALAPPDATA\Programs\Java"
    )
    
    foreach (`$basePath in `$searchPaths) {
        if (Test-Path `$basePath) {
            `$javaDirs = Get-ChildItem -Path `$basePath -Directory -ErrorAction SilentlyContinue | Where-Object { 
                `$_.Name -match "(jdk|jre|corretto|openjdk|ms-)" -and 
                (Test-Path "`$(`$_.FullName)\bin\java.exe")
            }
            
            foreach (`$javaDir in `$javaDirs) {
                try {
                    `$version = & "`$(`$javaDir.FullName)\bin\java.exe" -version 2>&1 | Select-String "version"
                    if (`$version -match "17|18|19|20|21|22|23|24") {
                        return `$javaDir.FullName
                    }
                } catch { continue }
            }
        }
    }
    return `$null
}

function Find-Maven {
    try {
        `$version = mvn -version 2>&1 | Select-String "Apache Maven"
        if (`$version) {
            `$mvnPath = (Get-Command mvn -ErrorAction SilentlyContinue).Source
            `$mavenHome = Split-Path (Split-Path `$mvnPath -Parent) -Parent
            return "`$mavenHome\bin"
        }
    } catch { }
    
    `$searchPaths = @(
        "`$env:USERPROFILE\maven",
        "`$env:PROGRAMFILES\Apache\maven",
        "`$env:PROGRAMFILES(X86)\Apache\maven"
    )
    
    foreach (`$basePath in `$searchPaths) {
        if (Test-Path `$basePath) {
            `$mavenDirs = Get-ChildItem -Path `$basePath -Directory -ErrorAction SilentlyContinue | Where-Object { 
                `$_.Name -match "apache-maven" -and 
                (Test-Path "`$(`$_.FullName)\bin\mvn.cmd")
            }
            
            foreach (`$mavenDir in `$mavenDirs) {
                try {
                    `$version = & "`$(`$mavenDir.FullName)\bin\mvn.cmd" -version 2>&1 | Select-String "Apache Maven"
                    if (`$version) {
                        return "`$(`$mavenDir.FullName)\bin"
                    }
                } catch { continue }
            }
        }
    }
    return `$null
}
"@
    
    Invoke-Expression $detectionCode
    
    # Use the detection functions
    $javaHome = Find-Java
    $mavenBin = Find-Maven
    
    if ($javaHome -and $mavenBin) {
        $env:JAVA_HOME = $javaHome
        $env:PATH = "$javaHome\bin;$mavenBin;$env:USERPROFILE\.cargo\bin;$env:PATH"
        Write-Host "Environment auto-detected successfully" -ForegroundColor Green
    } else {
        Write-Host "Auto-detection failed. Using fallback..." -ForegroundColor Yellow
        $env:JAVA_HOME = "$env:USERPROFILE\.jdks\corretto-17.0.15"
        $env:PATH = "$env:JAVA_HOME\bin;$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
        Write-Host "Using fallback configuration" -ForegroundColor Green
    }
} else {
    Write-Host "Setup script not found. Using fallback..." -ForegroundColor Yellow
    $env:JAVA_HOME = "$env:USERPROFILE\.jdks\corretto-17.0.15"
    $env:PATH = "$env:JAVA_HOME\bin;$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
    Write-Host "Using fallback configuration" -ForegroundColor Green
}

Write-Host ""
Write-Host "Environment configured:" -ForegroundColor Cyan
Write-Host "JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Yellow

Write-Host ""
Write-Host "Verifying tools..." -ForegroundColor Cyan

# Verify Java
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "Java: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "Java not found!" -ForegroundColor Red
    exit 1
}

# Verify Maven
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    Write-Host "Maven: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "Maven not found!" -ForegroundColor Red
    exit 1
}

# Verify Rust
try {
    $cargoVersion = cargo --version
    Write-Host "Cargo: $cargoVersion" -ForegroundColor Green
} catch {
    Write-Host "Rust/Cargo not found!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Starting both services concurrently..." -ForegroundColor Green
Write-Host "Backend: Spring Boot on http://localhost:8080" -ForegroundColor Cyan
Write-Host "Frontend: Tauri desktop app" -ForegroundColor Cyan
Write-Host ""

# Start both services concurrently
Start-Job -ScriptBlock {
    Set-Location $using:PWD
    Set-Location backend
    mvn spring-boot:run
} -Name "Backend"

Start-Job -ScriptBlock {
    Set-Location $using:PWD
    npm run tauri:dev
} -Name "Frontend"

# Monitor the jobs
Get-Job | ForEach-Object {
    Write-Host "[$($_.Name)]" -ForegroundColor Yellow
    Receive-Job $_.Name -Keep
}

Write-Host "Starting Tauri POS App with Backend..." -ForegroundColor Green


# Wait for jobs to complete
Get-Job | Wait-Job

# Clean up
Get-Job | Remove-Job
