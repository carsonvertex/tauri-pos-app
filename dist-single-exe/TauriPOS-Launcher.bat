@echo off
title Tauri POS - Standalone Application
color 0A
echo.
echo  ????????????????????????????????????????????????????????????????????????????????????????????????
echo  ??                   Tauri POS Application                    ??
echo  ??                  Standalone Launcher                       ??
echo  ????????????????????????????????????????????????????????????????????????????????????????????????
echo.

REM Set working directory
cd /d "%~dp0"

REM Set Java environment (try portable JRE first, then system)
if exist "jre\bin\java.exe" (
    set JAVA_HOME=%~dp0jre
    set PATH=%JAVA_HOME%\bin;%PATH%
    echo ??Using portable Java runtime
) else (
    echo ??? Portable JRE not found, using system Java
    echo    Please ensure Java 17+ is installed
)

REM Check Java availability
java -version >nul 2>&1
if errorlevel 1 (
    echo.
    echo ??ERROR: Java not found!
    echo.
    echo Please install Java 17 or later from:
    echo https://adoptium.net/
    echo.
    echo Or run this application from a machine with Java installed.
    echo.
    pause
    exit /b 1
)

REM Show Java version
echo ??Java environment ready
java -version 2>&1 | findstr "version"

REM Create data directory if it doesn't exist
if not exist "data" mkdir "data"

REM Start backend
echo.
echo ?? Starting backend server...
echo    This may take a few moments on first run...
start /B /MIN java -jar "%~dp0backend.jar" --server.port=8080 --spring.datasource.local.url=jdbc:sqlite:./data/pos_local.db

REM Wait for backend to initialize
echo ??Initializing backend server...
timeout /t 8 /nobreak >nul

REM Check backend health
echo ?? Checking backend status...
curl -s http://localhost:8080/api/pos/health >nul 2>&1
if errorlevel 1 (
    echo ??? Backend may still be starting, continuing anyway...
) else (
    echo ??Backend is ready
)

REM Start frontend
echo.
echo ????Launching Tauri POS Application...
echo.
start "" "%~dp0TauriPOS.exe"

REM Wait a moment for the app to start
timeout /t 3 /nobreak >nul

REM Hide this window
if exist "%~dp0hide-console.vbs" (
    cscript //nologo "%~dp0hide-console.vbs"
) else (
    echo.
    echo ??Application started successfully!
    echo.
    echo ?? Tip: You can close this window - the application will continue running.
    echo    To stop the application, close the Tauri POS window.
    echo.
    pause
)
