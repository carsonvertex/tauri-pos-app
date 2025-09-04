Write-Host "Starting Tauri POS App with Backend..." -ForegroundColor Green
Write-Host ""

# Set environment variables
$env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
$env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"

Write-Host "Environment variables set:" -ForegroundColor Yellow
Write-Host "JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Cyan
Write-Host "Maven and Rust added to PATH" -ForegroundColor Cyan
Write-Host ""

# Verify tools are available
Write-Host "Verifying tools..." -ForegroundColor Cyan
try {
    $mvnVersion = mvn --version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
    Write-Host "✓ Maven: $mvnVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven not found" -ForegroundColor Red
    exit 1
}

try {
    $cargoVersion = cargo --version 2>&1
    Write-Host "✓ Cargo: $cargoVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Cargo not found" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Starting both services concurrently..." -ForegroundColor Green
Write-Host "Backend: Spring Boot on http://localhost:8080" -ForegroundColor Blue
Write-Host "Frontend: Tauri desktop app" -ForegroundColor Green
Write-Host ""

# Run both services concurrently
concurrently --names "Backend,Frontend" --prefix-colors "blue,green" "npm run backend:dev" "npm run tauri:dev"
