# Create Windows Shortcut for Tauri POS App
# This script creates a shortcut that launches the app directly

# Get the current directory
$currentDir = Get-Location

# Create the shortcut
$WshShell = New-Object -comObject WScript.Shell
$Shortcut = $WshShell.CreateShortcut("$currentDir\Start Tauri POS App.lnk")

# Set shortcut properties
$Shortcut.TargetPath = "$currentDir\pos-launcher-gui.bat"
$Shortcut.WorkingDirectory = $currentDir
$Shortcut.Description = "Start Tauri POS Application"
$Shortcut.IconLocation = "$currentDir\tauri\icons\icon.ico"

# Save the shortcut
$Shortcut.Save()

Write-Host "Shortcut created successfully!" -ForegroundColor Green
Write-Host "Location: $currentDir\Start Tauri POS App.lnk" -ForegroundColor Yellow
Write-Host "Double-click the shortcut to start your POS application!" -ForegroundColor Cyan
