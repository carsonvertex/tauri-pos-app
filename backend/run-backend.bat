@echo off
title Tauri POS Backend
color 0A
echo.
echo ========================================
echo    Tauri POS Backend Launcher
echo ========================================
echo.

REM Check if PowerShell is available
powershell -Command "Get-Host" >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] PowerShell is required to run this backend launcher.
    echo    Please install PowerShell Core from: https://github.com/PowerShell/PowerShell/releases
    pause
    exit /b 1
)

echo [INFO] Launching backend with auto-detection...
echo    This will automatically find Java and Maven installations.
echo.

REM Run the PowerShell backend script
powershell -ExecutionPolicy Bypass -File "%~dp0run-backend.ps1"

if %errorlevel% equ 0 (
    echo.
    echo [SUCCESS] Backend stopped normally.
    echo.
) else (
    echo.
    echo [ERROR] Backend failed to start or stopped with errors.
    echo.
)

pause
