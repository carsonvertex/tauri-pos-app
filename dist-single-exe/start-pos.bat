@echo off
title Tauri POS App
echo Starting Tauri POS Application...
echo.

REM Set Java environment
set JAVA_HOME=%~dp0jre
set PATH=%JAVA_HOME%\bin;%PATH%

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java not found!
    echo Please ensure Java 17+ is installed and in your PATH.
    echo.
    pause
    exit /b 1
)

REM Start backend in background
echo Starting backend server...
start /B java -jar "%~dp0backend.jar" --server.port=8080

REM Wait for backend to start
echo Waiting for backend to start...
timeout /t 5 /nobreak >nul

REM Start Tauri frontend
echo Starting frontend application...
"%~dp0TauriPOS.exe"

REM Cleanup on exit
echo.
echo Shutting down...
taskkill /F /IM java.exe >nul 2>&1
