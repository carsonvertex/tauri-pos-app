@echo off
echo ========================================
echo    Tauri POS Application Installer
echo ========================================
echo.
echo This installer will:
echo 1. Install Java 17 (OpenJDK)
echo 2. Install Maven 3.9.5
echo 3. Install Rust (for Tauri)
echo 4. Set up environment variables
echo 5. Create desktop shortcut
echo 6. Configure the application
echo.
echo Press any key to continue...
pause >nul

echo.
echo Starting installation process...
echo.

REM Check if running as administrator
net session >nul 2>&1
if %errorLevel% == 0 (
    echo Running as administrator - good!
) else (
    echo WARNING: Not running as administrator
    echo Some installations may fail. Consider running as administrator.
    echo.
    pause
)

echo.
echo ========================================
echo Installing Java 17 (OpenJDK)...
echo ========================================
echo.

REM Download and install Java 17
echo Downloading Microsoft OpenJDK 17...
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://aka.ms/download-jdk/microsoft-jdk-17.0.15-windows-x64.msi' -OutFile '%TEMP%\microsoft-jdk-17.msi'}"

if exist "%TEMP%\microsoft-jdk-17.msi" (
    echo Installing Java 17...
    msiexec /i "%TEMP%\microsoft-jdk-17.msi" /quiet /norestart
    echo Java 17 installation completed.
    del "%TEMP%\microsoft-jdk-17.msi"
) else (
    echo Failed to download Java 17. Please install manually.
    echo Download from: https://aka.ms/download-jdk/microsoft-jdk-17.0.15-windows-x64.msi
)

echo.
echo ========================================
echo Installing Maven 3.9.5...
echo ========================================
echo.

REM Create Maven directory
if not exist "%USERPROFILE%\maven" mkdir "%USERPROFILE%\maven"

REM Download and extract Maven
echo Downloading Apache Maven 3.9.5...
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip' -OutFile '%TEMP%\maven.zip'}"

if exist "%TEMP%\maven.zip" (
    echo Extracting Maven...
    powershell -Command "Expand-Archive -Path '%TEMP%\maven.zip' -DestinationPath '%USERPROFILE%\maven' -Force"
    echo Maven installation completed.
    del "%TEMP%\maven.zip"
) else (
    echo Failed to download Maven. Please install manually.
    echo Download from: https://maven.apache.org/download.cgi
)

echo.
echo ========================================
echo Installing Rust...
echo ========================================
echo.

REM Download and install Rust
echo Downloading Rust installer...
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://win.rustup.rs/x86_64' -OutFile '%TEMP%\rustup-init.exe'}"

if exist "%TEMP%\rustup-init.exe" (
    echo Installing Rust...
    "%TEMP%\rustup-init.exe" --default-toolchain stable --profile default -y
    echo Rust installation completed.
    del "%TEMP%\rustup-init.exe"
) else (
    echo Failed to download Rust. Please install manually.
    echo Download from: https://rustup.rs/
)

echo.
echo ========================================
echo Setting up environment variables...
echo ========================================
echo.

REM Set environment variables for current session
set JAVA_HOME=%USERPROFILE%\.jdks\ms-17.0.15
set PATH=%USERPROFILE%\maven\apache-maven-3.9.5\bin;%USERPROFILE%\.cargo\bin;%PATH%

REM Add to system environment variables (requires admin)
echo Adding environment variables to system...
setx JAVA_HOME "%USERPROFILE%\.jdks\ms-17.0.15" /M
setx PATH "%PATH%" /M

echo.
echo ========================================
echo Installing Node.js dependencies...
echo ========================================
echo.

REM Install npm dependencies
echo Installing project dependencies...
npm install
cd frontend && npm install && cd ..
cd backend && mvn dependency:resolve && cd ..

echo.
echo ========================================
echo Creating desktop shortcut...
echo ========================================
echo.

REM Create desktop shortcut
echo Creating desktop shortcut...
powershell -Command "& {$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%USERPROFILE%\Desktop\Tauri POS App.lnk'); $Shortcut.TargetPath = '%CD%\start-tauri-with-backend.bat'; $Shortcut.WorkingDirectory = '%CD%'; $Shortcut.Description = 'Tauri POS Application'; $Shortcut.IconLocation = '%CD%\tauri\icons\icon.ico'; $Shortcut.Save()}"

echo.
echo ========================================
echo Installation Complete!
echo ========================================
echo.
echo Your Tauri POS application is now ready!
echo.
echo What was installed:
echo - Java 17 (OpenJDK)
echo - Maven 3.9.5
echo - Rust (with Cargo)
echo - All project dependencies
echo.
echo Desktop shortcut created: "Tauri POS App"
echo.
echo To start the application:
echo 1. Double-click "Tauri POS App" on your desktop
echo 2. Or run: npm run tauri:with-backend
echo.
echo Note: You may need to restart your computer for all
echo environment variables to take effect.
echo.
pause
