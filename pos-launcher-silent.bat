@echo off
REM Launch Tauri POS GUI Launcher completely silently
REM This will show only the beautiful GUI interface

REM Use start command to launch PowerShell silently
start "" /min powershell -ExecutionPolicy Bypass -WindowStyle Hidden -File "pos-launcher-gui.ps1"

REM Exit immediately to hide the batch window
exit
