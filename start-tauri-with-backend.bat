@echo off
echo Starting Tauri POS App...
echo.

echo Setting environment variables...
set JAVA_HOME=%USERPROFILE%\.jdks\ms-17.0.15
set PATH=%USERPROFILE%\maven\apache-maven-3.9.5\bin;%USERPROFILE%\.cargo\bin;%PATH%

echo Starting Spring Boot Backend silently...
start /min "Backend" cmd /c "cd /d %CD% && set JAVA_HOME=%JAVA_HOME% && set PATH=%PATH% && npm run backend:dev"

echo Waiting for backend to start...
timeout /t 15 /nobreak >nul

echo Starting Tauri Desktop App...
npm run tauri:dev

echo.
echo Tauri app launched! Backend is running silently in the background.
echo.
pause
