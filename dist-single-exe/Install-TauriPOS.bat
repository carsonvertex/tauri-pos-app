@echo off
title Tauri POS - Professional Installer
color 0B
echo.
echo  ????????????????????????????????????????????????????????????????????????????????????????????????
echo  ??               Tauri POS Professional Installer             ??
echo  ????????????????????????????????????????????????????????????????????????????????????????????????
echo.

set INSTALL_DIR=%PROGRAMFILES%\TauriPOS
set DESKTOP=%USERPROFILE%\Desktop
set START_MENU=%APPDATA%\Microsoft\Windows\Start Menu\Programs

echo ?? Installing to: %INSTALL_DIR%
echo.

REM Check for admin privileges
net session >nul 2>&1
if errorlevel 1 (
    echo ??? Administrator privileges required for system installation
    echo    Installing to user directory instead...
    set INSTALL_DIR=%USERPROFILE%\TauriPOS
)

REM Create installation directory
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"

REM Copy application files
echo ?? Copying application files...
xcopy /E /I /Y "%~dp0*" "%INSTALL_DIR%\"

REM Create desktop shortcut
echo ?? Creating desktop shortcut...
set SHORTCUT_PATH=%DESKTOP%\Tauri POS.lnk
powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%SHORTCUT_PATH%'); $Shortcut.TargetPath = '%INSTALL_DIR%\TauriPOS-Silent.vbs'; $Shortcut.WorkingDirectory = '%INSTALL_DIR%'; $Shortcut.IconLocation = '%INSTALL_DIR%\TauriPOS.exe'; $Shortcut.Save()"

REM Create start menu shortcut
echo ?? Creating start menu shortcut...
if not exist "%START_MENU%\TauriPOS" mkdir "%START_MENU%\TauriPOS"
set START_SHORTCUT=%START_MENU%\TauriPOS\Tauri POS.lnk
powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%START_SHORTCUT%'); $Shortcut.TargetPath = '%INSTALL_DIR%\TauriPOS-Silent.vbs'; $Shortcut.WorkingDirectory = '%INSTALL_DIR%'; $Shortcut.IconLocation = '%INSTALL_DIR%\TauriPOS.exe'; $Shortcut.Save()"

REM Create uninstaller
echo ????Creating uninstaller...
echo @echo off > "%INSTALL_DIR%\uninstall.bat"
echo title Tauri POS Uninstaller >> "%INSTALL_DIR%\uninstall.bat"
echo echo Uninstalling Tauri POS... >> "%INSTALL_DIR%\uninstall.bat"
echo taskkill /F /IM TauriPOS.exe ^>nul 2^>^&1 >> "%INSTALL_DIR%\uninstall.bat"
echo taskkill /F /IM java.exe ^>nul 2^>^&1 >> "%INSTALL_DIR%\uninstall.bat"
echo del "%DESKTOP%\Tauri POS.lnk" >> "%INSTALL_DIR%\uninstall.bat"
echo rmdir /S /Q "%START_MENU%\TauriPOS" >> "%INSTALL_DIR%\uninstall.bat"
echo rmdir /S /Q "%INSTALL_DIR%" >> "%INSTALL_DIR%\uninstall.bat"
echo echo Uninstallation complete! >> "%INSTALL_DIR%\uninstall.bat"
echo pause >> "%INSTALL_DIR%\uninstall.bat"

echo.
echo ??Installation completed successfully!
echo.
echo ?? Installation location: %INSTALL_DIR%
echo ?? Desktop shortcut created
echo ?? Start menu shortcut created
echo ????Uninstaller available in installation folder
echo.
echo ?? You can now run Tauri POS from:
echo    - Desktop shortcut
echo    - Start menu
echo    - Installation folder
echo.
echo ?? The application includes:
echo    - Portable Java runtime (if available)
echo    - SQLite database for offline operation
echo    - Automatic backend startup
echo    - Professional installation
echo.
pause
