@echo off
echo ========================================
echo   Database Initialization Script
echo ========================================
echo.

REM Check if PowerShell is available
powershell -Command "Get-Host" >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: PowerShell is not available on this system.
    echo Please install PowerShell or run the script manually.
    pause
    exit /b 1
)

REM Check if the PowerShell script exists
if not exist "init-database.ps1" (
    echo ERROR: init-database.ps1 not found in current directory.
    echo Please make sure you're running this from the project root.
    pause
    exit /b 1
)

echo Running database initialization...
echo.

REM Run the PowerShell script
powershell -ExecutionPolicy Bypass -File "init-database.ps1"

REM Check if the script ran successfully
if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo   Database initialization completed!
    echo ========================================
) else (
    echo.
    echo ========================================
    echo   Database initialization failed!
    echo ========================================
)

echo.
echo Press any key to exit...
pause >nul
