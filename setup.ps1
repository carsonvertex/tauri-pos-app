#!/usr/bin/env pwsh

Write-Host "🚀 Tauri POS App Setup Script" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host ""

# Function to check if a command exists
function Test-Command($cmdname) {
    return [bool](Get-Command -Name $cmdname -ErrorAction SilentlyContinue)
}

# Function to find Java installation
function Find-JavaInstallation {
    Write-Host "🔍 Detecting Java installation..." -ForegroundColor Cyan
    
    # Check if JAVA_HOME is already set
    if ($env:JAVA_HOME) {
        Write-Host "✓ JAVA_HOME already set: $env:JAVA_HOME" -ForegroundColor Green
        return $env:JAVA_HOME
    }
    
    # Check common Java installation paths
    $possiblePaths = @(
        "$env:USERPROFILE\.jdks",
        "$env:PROGRAMFILES\Java",
        "$env:PROGRAMFILES(X86)\Java",
        "$env:LOCALAPPDATA\Programs\Java"
    )
    
    foreach ($basePath in $possiblePaths) {
        if (Test-Path $basePath) {
            $javaDirs = Get-ChildItem -Path $basePath -Directory | Where-Object { 
                $_.Name -match "(jdk|jre|corretto|openjdk|ms-)" -and 
                (Test-Path "$($_.FullName)\bin\java.exe")
            }
            
            if ($javaDirs) {
                $javaHome = $javaDirs[0].FullName
                Write-Host "✓ Found Java at: $javaHome" -ForegroundColor Green
                return $javaHome
            }
        }
    }
    
    Write-Host "✗ No Java installation found" -ForegroundColor Red
    return $null
}

# Function to find Maven installation
function Find-MavenInstallation {
    Write-Host "🔍 Detecting Maven installation..." -ForegroundColor Cyan
    
    # Check if Maven is in PATH
    if (Test-Command "mvn") {
        Write-Host "✓ Maven found in PATH" -ForegroundColor Green
        return $true
    }
    
    # Check common Maven installation paths
    $possiblePaths = @(
        "$env:USERPROFILE\maven",
        "$env:PROGRAMFILES\Apache\maven",
        "$env:PROGRAMFILES(X86)\Apache\maven"
    )
    
    foreach ($basePath in $possiblePaths) {
        if (Test-Path $basePath) {
            $mavenDirs = Get-ChildItem -Path $basePath -Directory | Where-Object { 
                $_.Name -match "apache-maven" -and 
                (Test-Path "$($_.FullName)\bin\mvn.cmd")
            }
            
            if ($mavenDirs) {
                $mavenPath = "$($mavenDirs[0].FullName)\bin"
                Write-Host "✓ Found Maven at: $mavenPath" -ForegroundColor Green
                $env:PATH = "$mavenPath;$env:PATH"
                return $true
            }
        }
    }
    
    Write-Host "✗ No Maven installation found" -ForegroundColor Red
    return $false
}

# Function to check and install Node.js dependencies
function Install-NodeDependencies {
    Write-Host "🔍 Checking Node.js and npm..." -ForegroundColor Cyan
    
    if (-not (Test-Command "node")) {
        Write-Host "✗ Node.js not found. Please install Node.js from https://nodejs.org/" -ForegroundColor Red
        return $false
    }
    
    if (-not (Test-Command "npm")) {
        Write-Host "✗ npm not found. Please install Node.js from https://nodejs.org/" -ForegroundColor Red
        return $false
    }
    
    $nodeVersion = node --version
    $npmVersion = npm --version
    Write-Host "✓ Node.js: $nodeVersion" -ForegroundColor Green
    Write-Host "✓ npm: $npmVersion" -ForegroundColor Green
    
    Write-Host ""
    Write-Host "📦 Installing root dependencies..." -ForegroundColor Cyan
    npm install
    
    Write-Host ""
    Write-Host "📦 Installing frontend dependencies..." -ForegroundColor Cyan
    Set-Location frontend
    npm install
    Set-Location ..
    
    return $true
}

# Function to check Rust installation
function Test-RustInstallation {
    Write-Host "🔍 Checking Rust installation..." -ForegroundColor Cyan
    
    if (-not (Test-Command "cargo")) {
        Write-Host "✗ Rust not found. Please install Rust from https://rustup.rs/" -ForegroundColor Red
        return $false
    }
    
    $rustVersion = cargo --version
    Write-Host "✓ Rust: $rustVersion" -ForegroundColor Green
    return $true
}

# Function to verify backend compilation
function Test-BackendCompilation {
    Write-Host "🔍 Testing backend compilation..." -ForegroundColor Cyan
    
    if (-not $env:JAVA_HOME) {
        Write-Host "✗ JAVA_HOME not set, skipping backend test" -ForegroundColor Yellow
        return $false
    }
    
    try {
        Set-Location backend
        Write-Host "  Compiling backend..." -ForegroundColor Gray
        mvn compile -q
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ Backend compiles successfully" -ForegroundColor Green
            Set-Location ..
            return $true
        } else {
            Write-Host "✗ Backend compilation failed" -ForegroundColor Red
            Set-Location ..
            return $false
        }
    } catch {
        Write-Host "✗ Backend compilation error: $_" -ForegroundColor Red
        Set-Location ..
        return $false
    }
}

# Function to create environment setup script
function Create-EnvironmentScript {
    Write-Host "🔧 Creating environment setup script..." -ForegroundColor Cyan
    
    $scriptContent = @"
# Environment setup for Tauri POS App
# Run this script before starting the application

# Set Java environment
`$env:JAVA_HOME = "$env:JAVA_HOME"
`$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Set Maven environment (if not in PATH)
if (Test-Path "$env:USERPROFILE\maven\apache-maven-3.9.5\bin") {
    `$env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;`$env:PATH"
}

# Set Rust environment (if not in PATH)
if (Test-Path "$env:USERPROFILE\.cargo\bin") {
    `$env:PATH = "$env:USERPROFILE\.cargo\bin;`$env:PATH"
}

Write-Host "Environment variables set:" -ForegroundColor Green
Write-Host "JAVA_HOME: `$env:JAVA_HOME" -ForegroundColor Cyan
Write-Host "Java version: $(java -version 2>&1 | Select-String 'version')" -ForegroundColor Cyan
Write-Host "Maven version: $(mvn -version 2>&1 | Select-String 'Apache Maven')" -ForegroundColor Cyan
Write-Host "Rust version: $(cargo --version)" -ForegroundColor Cyan
"@
    
    $scriptContent | Out-File -FilePath "setup-env.ps1" -Encoding UTF8
    Write-Host "✓ Created setup-env.ps1" -ForegroundColor Green
}

# Function to check if everything is already set up
function Test-CompleteSetup {
    Write-Host "🔍 Checking if everything is already set up..." -ForegroundColor Cyan
    
    # Check if all tools are available
    $javaOk = Test-Command "java"
    $mvnOk = Test-Command "mvn"
    $cargoOk = Test-Command "cargo"
    $nodeOk = Test-Command "node"
    $npmOk = Test-Command "npm"
    
    # Check if dependencies are installed
    $depsOk = Test-Path "node_modules" -and Test-Path "frontend/node_modules"
    
    # Check if backend compiles
    $backendOk = $false
    if ($javaOk -and $mvnOk) {
        try {
            Set-Location backend
            $backendOk = Test-Path "target/classes"
            Set-Location ..
        } catch {
            $backendOk = $false
        }
    }
    
    return $javaOk -and $mvnOk -and $cargoOk -and $nodeOk -and $npmOk -and $depsOk -and $backendOk
}

# Function to start the application
function Start-Application {
    Write-Host ""
    Write-Host "🚀 Everything is set up! Starting Tauri POS App..." -ForegroundColor Green
    Write-Host ""
    
    # Ask user if they want to start the app
    $response = Read-Host "Do you want to start the application now? (y/n)"
    if ($response -eq "y" -or $response -eq "Y" -or $response -eq "yes" -or $response -eq "Yes") {
        Write-Host ""
        Write-Host "🎯 Starting Tauri POS App with backend..." -ForegroundColor Green
        Write-Host "   This will start both the Spring Boot backend and Tauri desktop app" -ForegroundColor Cyan
        Write-Host ""
        
        try {
            # Start the application
            npm run tauri:with-backend
        } catch {
            Write-Host "❌ Failed to start the application: $_" -ForegroundColor Red
            Write-Host ""
            Write-Host "📋 Manual start commands:" -ForegroundColor Yellow
            Write-Host "   npm run tauri:with-backend" -ForegroundColor White
        }
    } else {
        Write-Host ""
        Write-Host "📋 To start the application later, run:" -ForegroundColor Yellow
        Write-Host "   npm run tauri:with-backend" -ForegroundColor White
    }
}

# Main setup process
function Start-Setup {
    Write-Host "🚀 Starting Tauri POS App setup..." -ForegroundColor Green
    Write-Host ""
    
    # Check if everything is already set up
    if (Test-CompleteSetup) {
        Write-Host "✅ Everything is already set up and ready to go!" -ForegroundColor Green
        Start-Application
        return $true
    }
    
    Write-Host "🔧 Some setup is required. Let's get everything configured..." -ForegroundColor Yellow
    Write-Host ""
    
    # Step 1: Check Java
    $javaHome = Find-JavaInstallation
    if ($javaHome) {
        $env:JAVA_HOME = $javaHome
        $env:PATH = "$javaHome\bin;$env:PATH"
    } else {
        Write-Host "❌ Java installation required. Please install Java 17 or later." -ForegroundColor Red
        Write-Host "   Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/" -ForegroundColor Yellow
        return $false
    }
    
    # Step 2: Check Maven
    if (-not (Find-MavenInstallation)) {
        Write-Host "❌ Maven installation required. Please install Apache Maven." -ForegroundColor Red
        Write-Host "   Download from: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
        return $false
    }
    
    # Step 3: Check Rust
    if (-not (Test-RustInstallation)) {
        Write-Host "❌ Rust installation required. Please install Rust." -ForegroundColor Red
        Write-Host "   Run: winget install Rustlang.Rust.MVC or visit https://rustup.rs/" -ForegroundColor Yellow
        return $false
    }
    
    # Step 4: Install Node.js dependencies
    if (-not (Install-NodeDependencies)) {
        return $false
    }
    
    # Step 5: Test backend compilation
    Test-BackendCompilation
    
    # Step 6: Create environment script
    Create-EnvironmentScript
    
    Write-Host ""
    Write-Host "🎉 Setup completed successfully!" -ForegroundColor Green
    Write-Host ""
    
    # Ask if user wants to start the app now
    Start-Application
    
    Write-Host ""
    Write-Host "🔗 Useful URLs:" -ForegroundColor Yellow
    Write-Host "   - Frontend: http://localhost:5173" -ForegroundColor White
    Write-Host "   - Backend: http://localhost:8080/api" -ForegroundColor White
    Write-Host "   - H2 Console: http://localhost:8080/api/h2-console" -ForegroundColor White
    Write-Host ""
    
    return $true
}

# Run setup if script is executed directly
if ($MyInvocation.InvocationName -eq $MyInvocation.MyCommand.Name) {
    Start-Setup
}
