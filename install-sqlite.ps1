#!/usr/bin/env pwsh

Write-Host "SQLite Installation Guide" -ForegroundColor Green
Write-Host "========================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Option 1: Download SQLite CLI (Recommended)" -ForegroundColor Yellow
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "1. Go to: https://www.sqlite.org/download.html" -ForegroundColor White
Write-Host "2. Download: sqlite-tools-win32-x64-*.zip" -ForegroundColor White
Write-Host "3. Extract to: C:\sqlite" -ForegroundColor White
Write-Host "4. Add C:\sqlite to your PATH environment variable" -ForegroundColor White
Write-Host ""

Write-Host "Option 2: Use Chocolatey (if installed)" -ForegroundColor Yellow
Write-Host "=======================================" -ForegroundColor Cyan
Write-Host "choco install sqlite" -ForegroundColor White
Write-Host ""

Write-Host "Option 3: Use Winget (Windows 10/11)" -ForegroundColor Yellow
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "winget install SQLite.SQLite" -ForegroundColor White
Write-Host ""

Write-Host "Option 4: Use DB Browser for SQLite (No CLI needed)" -ForegroundColor Yellow
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "1. Download: https://sqlitebrowser.org/" -ForegroundColor White
Write-Host "2. Install and use GUI interface" -ForegroundColor White
Write-Host ""

Write-Host "Current Status:" -ForegroundColor Cyan
Write-Host "==============" -ForegroundColor Cyan
Write-Host " SQLite CLI: Not installed" -ForegroundColor Red
Write-Host " SQLite JDBC: Available (in your Java app)" -ForegroundColor Green
Write-Host " Database: Will be created automatically" -ForegroundColor Green
Write-Host ""

Write-Host "Quick Test:" -ForegroundColor Cyan
Write-Host "===========" -ForegroundColor Cyan
Write-Host "After installing SQLite CLI, run:" -ForegroundColor White
Write-Host "sqlite3 --version" -ForegroundColor Gray
Write-Host ""

Write-Host "Press any key to exit..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
