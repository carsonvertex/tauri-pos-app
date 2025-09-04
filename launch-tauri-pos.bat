@echo off
title Tauri POS Application Launcher
color 0A

echo ========================================
echo    Tauri POS Application Launcher
echo ========================================
echo.
echo Starting your POS application...
echo.

REM Set environment variables
set JAVA_HOME=%USERPROFILE%\.jdks\ms-17.0.15
set PATH=%USERPROFILE%\maven\apache-maven-3.9.5\bin;%USERPROFILE%\.cargo\bin;%PATH%

REM Check if required tools are available
echo Checking required tools...
java -version >nul 2>&1
if %errorLevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please run the installer first: install-tauri-pos.bat
    pause
    exit /b 1
)

mvn --version >nul 2>&1
if %errorLevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please run the installer first: install-tauri-pos.bat
    pause
    exit /b 1
)

cargo --version >nul 2>&1
if %errorLevel% neq 0 (
    echo ERROR: Rust/Cargo is not installed or not in PATH
    echo Please run the installer first: install-tauri-pos.bat
    pause
    exit /b 1
)

echo All required tools are available!
echo.

REM Start the application
echo Starting Tauri POS Application...
echo.
echo This will launch your desktop application with:
echo - Spring Boot backend (API server)
echo - React frontend (user interface)
echo - Tauri desktop wrapper
echo.
echo Please wait while the application starts...
echo.

npm run tauri:with-backend

echo.
echo Application has been closed.
echo Press any key to exit...
pause >nul
