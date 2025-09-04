@echo off
setlocal enabledelayedexpansion

echo 🚀 Tauri POS App Smart Setup & Launcher
echo =========================================
echo.

REM Check if PowerShell is available
powershell -Command "Get-Host" >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ PowerShell is required to run this setup script.
    echo    Please install PowerShell Core from: https://github.com/PowerShell/PowerShell/releases
    pause
    exit /b 1
)

REM Run the PowerShell setup script
echo 🔧 Running smart setup script...
echo    This will either set up the environment or launch the app directly!
echo.
powershell -ExecutionPolicy Bypass -File "%~dp0setup.ps1"

if %errorlevel% equ 0 (
    echo.
    echo 🎉 Setup/Launch completed successfully!
    echo.
) else (
    echo.
    echo ❌ Setup/Launch failed. Please check the error messages above.
    echo.
)

pause
