#!/usr/bin/env pwsh

param(
    [switch]$build
)

if ($build) {
    Write-Host "Building Tauri POS Backend..." -ForegroundColor Green
    Write-Host "Auto-detecting Maven installation..." -ForegroundColor Cyan
} else {
    Write-Host "Starting Tauri POS Backend..." -ForegroundColor Green
    Write-Host "Auto-detecting Maven installation..." -ForegroundColor Cyan
}

# Auto-detect Maven function
function Find-Maven {
    # Check if mvn is in PATH
    try {
        $version = mvn -version 2>&1 | Select-String "Apache Maven"
        if ($version) {
            $mvnPath = (Get-Command mvn -ErrorAction SilentlyContinue).Source
            $mavenHome = Split-Path (Split-Path $mvnPath -Parent) -Parent
            return "$mavenHome\bin"
        }
    } catch {
        # Maven not in PATH, continue searching
    }
    
    # Search common locations
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
                } catch {
                    continue
                }
            }
        }
    }
    
    return $null
}

# Auto-detect Java function
function Find-Java {
    # Check if JAVA_HOME is already set and working
    if ($env:JAVA_HOME -and (Test-Path "$env:JAVA_HOME\bin\java.exe")) {
        try {
            $version = & "$env:JAVA_HOME\bin\java.exe" -version 2>&1 | Select-String "version"
            if ($version -match "17|18|19|20|21|22|23|24") {
                return $env:JAVA_HOME
            }
        } catch {
            # JAVA_HOME is set but not working, continue searching
        }
    }
    
    # Check if java is in PATH
    try {
        $version = java -version 2>&1 | Select-String "version"
        if ($version -match "17|18|19|20|21|22|23|24") {
            $javaPath = (Get-Command java -ErrorAction SilentlyContinue).Source
            $javaHome = Split-Path (Split-Path $javaPath -Parent) -Parent
            return $javaHome
        }
    } catch {
        # Java not in PATH, continue searching
    }
    
    # Search common locations
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
                } catch {
                    continue
                }
            }
        }
    }
    
    return $null
}

# Find and set up Java
Write-Host "Detecting Java..." -ForegroundColor Yellow
$javaHome = Find-Java
if ($javaHome) {
    $env:JAVA_HOME = $javaHome
    $env:PATH = "$javaHome\bin;$env:PATH"
    Write-Host "✓ Java found: $javaHome" -ForegroundColor Green
} else {
    Write-Host "✗ Java not found! Please install Java 17 or later." -ForegroundColor Red
    Write-Host "Download from: https://adoptium.net/" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Find and set up Maven
Write-Host "Detecting Maven..." -ForegroundColor Yellow
$mavenBin = Find-Maven
if ($mavenBin) {
    $env:PATH = "$mavenBin;$env:PATH"
    Write-Host "✓ Maven found: $mavenBin" -ForegroundColor Green
} else {
    Write-Host "✗ Maven not found! Please install Apache Maven." -ForegroundColor Red
    Write-Host "Download from: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Verify Maven is working
Write-Host "Verifying Maven installation..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    Write-Host "✓ Maven version: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven verification failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Change to the backend directory
$backendDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $backendDir
Write-Host "Working directory: $backendDir" -ForegroundColor Gray

# Start the backend or build
if ($build) {
    Write-Host ""
    Write-Host "Building Spring Boot backend..." -ForegroundColor Green
    Write-Host "This will create a JAR file in target/ directory" -ForegroundColor Cyan
    Write-Host ""
    
    # Run Maven build
    mvn clean package
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "✓ Backend built successfully!" -ForegroundColor Green
        Write-Host "JAR file location: target/tauri-pos-app-0.1.0.jar" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "To run the built JAR:" -ForegroundColor Yellow
        Write-Host "  npm run backend:production" -ForegroundColor White
        Write-Host "  or" -ForegroundColor Yellow
        Write-Host "  java -jar target/tauri-pos-app-0.1.0.jar" -ForegroundColor White
    } else {
        Write-Host ""
        Write-Host "✗ Backend build failed!" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host ""
    Write-Host "Starting Spring Boot backend..." -ForegroundColor Green
    Write-Host "Backend will be available at: http://localhost:8080/api" -ForegroundColor Cyan
    Write-Host "Health check: http://localhost:8080/api/pos/health" -ForegroundColor Cyan
    Write-Host "H2 Console: http://localhost:8080/api/h2-console" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Press Ctrl+C to stop the backend" -ForegroundColor Yellow
    Write-Host ""
    
    # Run Maven Spring Boot
    Write-Host "Starting Spring Boot application..." -ForegroundColor Yellow
    
    # First, ensure dependencies are downloaded
    Write-Host "Downloading dependencies..." -ForegroundColor Yellow
    mvn dependency:resolve -q
    
    # Now run Spring Boot
    Write-Host "Launching Spring Boot..." -ForegroundColor Yellow
    mvn spring-boot:run
}
