Write-Host "Starting Tauri POS App with Backend..." -ForegroundColor Green
Write-Host ""

# Set environment variables
$env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
$env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"

Write-Host "JAVA_HOME set to: $env:JAVA_HOME" -ForegroundColor Yellow
Write-Host "Maven added to PATH" -ForegroundColor Yellow
Write-Host "Rust/Cargo added to PATH" -ForegroundColor Yellow

# Verify tools are available
Write-Host "Verifying tools..." -ForegroundColor Cyan
try {
    $mvnVersion = mvn --version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
    Write-Host "✓ Maven: $mvnVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven not found" -ForegroundColor Red
}

try {
    $cargoVersion = cargo --version 2>&1
    Write-Host "✓ Cargo: $cargoVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Cargo not found" -ForegroundColor Red
}

Write-Host ""
Write-Host "Starting Spring Boot Backend..." -ForegroundColor Cyan
Write-Host "This will open in a new terminal window" -ForegroundColor Yellow

# Start backend in new terminal
$backendCommand = "cd '$PWD'; `$env:JAVA_HOME='$env:USERPROFILE\.jdks\ms-17.0.15'; `$env:PATH='$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;`$env:PATH'; npm run backend:dev"
Start-Process powershell -ArgumentList "-NoExit", "-Command", $backendCommand

Write-Host "Backend terminal opened. Waiting 15 seconds for startup..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

Write-Host ""
Write-Host "Starting Tauri Frontend..." -ForegroundColor Cyan
Write-Host "This will open in another new terminal window" -ForegroundColor Yellow

# Start frontend in new terminal
$frontendCommand = "cd '$PWD'; `$env:JAVA_HOME='$env:USERPROFILE\.jdks\ms-17.0.15'; `$env:PATH='$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;`$env:PATH'; npm run tauri:dev"
Start-Process powershell -ArgumentList "-NoExit", "-Command", $frontendCommand

Write-Host ""
Write-Host "Both services started in separate terminals!" -ForegroundColor Green
Write-Host "Backend terminal: Spring Boot will be running on http://localhost:8080" -ForegroundColor Cyan
Write-Host "Frontend terminal: Tauri desktop app will launch" -ForegroundColor Cyan
Write-Host ""
Write-Host "Press any key to close this launcher..." -ForegroundColor Yellow
Read-Host
