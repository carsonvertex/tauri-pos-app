@echo off
title Tauri POS App - Dependency Installer
color 0A

echo ========================================
echo    Tauri POS App - Dependency Installer
echo ========================================
echo.
echo This will install all required programs:
echo - Java (OpenJDK 17)
echo - Maven (Build tool)
echo - Rust (Programming language)
echo - Node.js (JavaScript runtime)
echo.
echo Press any key to continue...
pause >nul

echo.
echo Checking if running as Administrator...
net session >nul 2>&1
if %errorLevel% == 0 (
    echo ✓ Running as Administrator
) else (
    echo ⚠️  Not running as Administrator
    echo Some installations may fail. Right-click and "Run as Administrator" for best results.
    echo.
    pause
)

echo.
echo ========================================
echo Installing Java (OpenJDK 17)...
echo ========================================
if exist "%USERPROFILE%\.jdks\ms-17.0.15" (
    echo ✓ Java already installed
) else (
    echo Downloading Java...
    powershell -Command "& { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://aka.ms/download-jdk/microsoft-jdk-17.0.15-windows-x64.msi' -OutFile '%TEMP%\jdk17.msi' }"
    echo Installing Java...
    msiexec /i "%TEMP%\jdk17.msi" /quiet /norestart
    echo ✓ Java installation started
)

echo.
echo ========================================
echo Installing Maven...
echo ========================================
if exist "%USERPROFILE%\maven\apache-maven-3.9.5" (
    echo ✓ Maven already installed
) else (
    echo Downloading Maven...
    powershell -Command "& { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip' -OutFile '%TEMP%\maven.zip' }"
    echo Extracting Maven...
    powershell -Command "& { Expand-Archive -Path '%TEMP%\maven.zip' -DestinationPath '%USERPROFILE%\maven' -Force }"
    echo ✓ Maven installed
)

echo.
echo ========================================
echo Installing Rust...
echo ========================================
if exist "%USERPROFILE%\.cargo" (
    echo ✓ Rust already installed
) else (
    echo Downloading Rust...
    powershell -Command "& { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://win.rustup.rs/x86_64' -OutFile '%TEMP%\rustup-init.exe' }"
    echo Installing Rust...
    "%TEMP%\rustup-init.exe" -y --quiet
    echo ✓ Rust installed
)

echo.
echo ========================================
echo Installing Node.js...
echo ========================================
node --version >nul 2>&1
if %errorLevel% == 0 (
    echo ✓ Node.js already installed
) else (
    echo Downloading Node.js...
    powershell -Command "& { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://nodejs.org/dist/v18.19.0/node-v18.19.0-x64.msi' -OutFile '%TEMP%\nodejs.msi' }"
    echo Installing Node.js...
    msiexec /i "%TEMP%\nodejs.msi" /quiet /norestart
    echo ✓ Node.js installation started
)

echo.
echo ========================================
echo Setting Environment Variables...
echo ========================================
echo Setting JAVA_HOME...
setx JAVA_HOME "%USERPROFILE%\.jdks\ms-17.0.15" /M
echo Setting PATH...
setx PATH "%USERPROFILE%\maven\apache-maven-3.9.5\bin;%USERPROFILE%\.cargo\bin;%PATH%" /M
echo ✓ Environment variables set

echo.
echo ========================================
echo Installing NPM Dependencies...
echo ========================================
echo Installing frontend dependencies...
npm install
echo ✓ Frontend dependencies installed

echo.
echo ========================================
echo Installing Maven Dependencies...
echo ========================================
echo Installing backend dependencies...
cd backend
mvn dependency:resolve
cd ..
echo ✓ Backend dependencies installed

echo.
echo ========================================
echo Installation Complete!
echo ========================================
echo.
echo All required programs have been installed:
echo ✓ Java (OpenJDK 17)
echo ✓ Maven (Build tool)
echo ✓ Rust (Programming language)
echo ✓ Node.js (JavaScript runtime)
echo ✓ NPM dependencies
echo ✓ Maven dependencies
echo.
echo You can now launch the app by double-clicking:
echo   pos-launcher.bat
echo.
echo Note: You may need to restart your computer for
echo environment variables to take effect.
echo.
pause
