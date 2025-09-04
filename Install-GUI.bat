@echo off
REM Launch GUI installer silently without showing terminal
powershell -ExecutionPolicy Bypass -WindowStyle Hidden -File "install-tauri-pos-gui.ps1"
