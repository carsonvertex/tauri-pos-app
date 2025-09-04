@echo off
setlocal enabledelayedexpansion

echo Tauri POS App GUI Setup & Launcher
echo ======================================
echo.

REM Check if PowerShell is available
powershell -Command "Get-Host" >nul 2>&1
if %errorlevel% neq 0 (
    echo PowerShell is required to run this GUI setup.
    echo    Please install PowerShell Core from: https://github.com/PowerShell/PowerShell/releases
    pause
    exit /b 1
)

REM Run the PowerShell GUI setup script
echo Launching GUI setup application...
echo    This will open a beautiful setup interface!
echo.
powershell -ExecutionPolicy Bypass -File "%~dp0setup-gui.ps1"

if %errorlevel% equ 0 (
    echo.
    echo Setup/Launch completed successfully!
    echo.
) else (
    echo.
    echo Setup/Launch failed. Please check the error messages above.
    echo.
)

pause
