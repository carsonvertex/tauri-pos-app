@echo off
echo Creating Windows Shortcut for Tauri POS App...
echo.

echo Setting up shortcut...
set SHORTCUT_PATH=%USERPROFILE%\Desktop\Tauri POS App.lnk
set TARGET_PATH=%CD%\start-tauri-with-backend.bat
set WORKING_DIR=%CD%

echo Creating shortcut at: %SHORTCUT_PATH%
echo Target: %TARGET_PATH%
echo Working Directory: %WORKING_DIR%
echo.

REM Create VBS script to generate shortcut
echo Set oWS = WScript.CreateObject("WScript.Shell") > CreateShortcut.vbs
echo sLinkFile = "%SHORTCUT_PATH%" >> CreateShortcut.vbs
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs
echo oLink.TargetPath = "%TARGET_PATH%" >> CreateShortcut.vbs
echo oLink.WorkingDirectory = "%WORKING_DIR%" >> CreateShortcut.vbs
echo oLink.Description = "Tauri POS Application with Backend" >> CreateShortcut.vbs
echo oLink.IconLocation = "%CD%\tauri\icons\icon.ico" >> CreateShortcut.vbs
echo oLink.WindowStyle = 7 >> CreateShortcut.vbs
echo oLink.Save >> CreateShortcut.vbs

REM Execute VBS script
cscript //nologo CreateShortcut.vbs

REM Clean up
del CreateShortcut.vbs

echo.
echo Shortcut created successfully!
echo You can now double-click "Tauri POS App" on your desktop
echo to launch the application with backend running.
echo.
pause
