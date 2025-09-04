@echo off
echo Creating Windows Shortcut for Tauri POS App...
echo.

REM Create the shortcut using PowerShell
powershell -ExecutionPolicy Bypass -Command "& { $WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%CD%\Start Tauri POS App.lnk'); $Shortcut.TargetPath = '%CD%\launch-tauri-pos.bat'; $Shortcut.WorkingDirectory = '%CD%'; $Shortcut.Description = 'Start Tauri POS Application'; $Shortcut.IconLocation = '%CD%\tauri\icons\icon.ico'; $Shortcut.Save(); Write-Host 'Shortcut created successfully!' -ForegroundColor Green }"

echo.
echo Shortcut created: "Start Tauri POS App.lnk"
echo Double-click the shortcut to start your POS application!
echo.
pause
