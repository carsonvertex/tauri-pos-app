#!/usr/bin/env pwsh

Write-Host "Tauri POS Application Launcher" -ForegroundColor Green
Write-Host "=============================" -ForegroundColor Cyan
Write-Host ""

# Set Java environment
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$env:JAVA_HOME = "$scriptDir\jre"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Check if Java is available
try {
    java -version | Out-Null
    Write-Host "??Java environment ready" -ForegroundColor Green
} catch {
    Write-Host "??Error: Java not found!" -ForegroundColor Red
    Write-Host "Please ensure Java 17+ is installed and in your PATH." -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Start backend
Write-Host "?? Starting backend server..." -ForegroundColor Yellow
$backendProcess = Start-Process -FilePath "java" -ArgumentList "-jar", "$scriptDir\backend.jar", "--server.port=8080" -PassThru -WindowStyle Hidden

# Wait for backend to start
Write-Host "??Waiting for backend to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 8

# Check if backend is running
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/pos/health" -TimeoutSec 5
    Write-Host "??Backend is running" -ForegroundColor Green
} catch {
    Write-Host "?? Backend may not be fully ready, but continuing..." -ForegroundColor Yellow
}

# Start frontend
Write-Host "?儭?Starting frontend application..." -ForegroundColor Yellow
Start-Process -FilePath "$scriptDir\TauriPOS.exe" -Wait

# Cleanup
Write-Host "?? Shutting down backend..." -ForegroundColor Yellow
if ($backendProcess -and !$backendProcess.HasExited) {
    $backendProcess.Kill()
}

Write-Host "??Application closed" -ForegroundColor Green
