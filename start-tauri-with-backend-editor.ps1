#!/usr/bin/env pwsh

Write-Host "Starting Tauri POS App with Backend..." -ForegroundColor Green

# Set environment variables
$env:JAVA_HOME = "C:\Users\user\.jdks\corretto-17.0.12"
$env:PATH = "C:\Users\user\.jdks\corretto-17.0.12\bin;C:\Users\user\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"

Write-Host ""
Write-Host "Environment variables set:" -ForegroundColor Cyan
Write-Host "JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Yellow
Write-Host "Maven and Rust added to PATH" -ForegroundColor Yellow

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

# Wait for jobs to complete
Get-Job | Wait-Job

# Clean up
Get-Job | Remove-Job
