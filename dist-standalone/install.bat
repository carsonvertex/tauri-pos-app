@echo off
title Tauri POS Installer
echo Tauri POS Application Installer
echo ===============================
echo.

REM Create installation directory
set INSTALL_DIR=%USERPROFILE%\TauriPOS
echo Installing to: %INSTALL_DIR%

REM Create directory
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"

REM Copy files
echo Copying application files...
xcopy /E /I /Y "%~dp0*" "%INSTALL_DIR%\"

REM Create desktop shortcut
echo Creating desktop shortcut...
set SHORTCUT_PATH=%USERPROFILE%\Desktop\Tauri POS.lnk
powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%SHORTCUT_PATH%'); $Shortcut.TargetPath = '%INSTALL_DIR%\start-pos.bat'; $Shortcut.WorkingDirectory = '%INSTALL_DIR%'; $Shortcut.Save()"

echo.
echo ??Installation complete!
echo.
echo The application has been installed to: %INSTALL_DIR%
echo A desktop shortcut has been created.
echo.
echo You can now run Tauri POS from the desktop shortcut or by double-clicking start-pos.bat in the installation folder.
echo.
pause
