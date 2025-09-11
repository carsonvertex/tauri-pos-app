#!/usr/bin/env pwsh

Write-Host "SQLite Database Access Tool" -ForegroundColor Green
Write-Host "=========================" -ForegroundColor Cyan
Write-Host ""

# Check if database exists
$dbPath = ".\data\pos_local.db"
if (Test-Path $dbPath) {
    Write-Host "Database found at: $dbPath" -ForegroundColor Green
    Write-Host "Size: $([math]::Round((Get-Item $dbPath).Length/1KB, 2)) KB" -ForegroundColor Yellow
    Write-Host ""
    
    # Show database info
    Write-Host "Database Information:" -ForegroundColor Cyan
    Write-Host "===================" -ForegroundColor Cyan
    
    # Try to query the database if SQLite is available
    try {
        $sqliteCmd = Get-Command sqlite3 -ErrorAction SilentlyContinue
        if ($sqliteCmd) {
            Write-Host "SQLite CLI found. Running queries..." -ForegroundColor Green
            
            # Get table count
            $tables = sqlite3 $dbPath ".tables" 2>$null
            if ($tables) {
                Write-Host "Tables: $tables" -ForegroundColor White
            }
            
            # Get product count
            $productCount = sqlite3 $dbPath "SELECT COUNT(*) FROM local_products;" 2>$null
            if ($productCount) {
                Write-Host "Products: $productCount" -ForegroundColor White
            }
            
            # Get order count
            $orderCount = sqlite3 $dbPath "SELECT COUNT(*) FROM local_orders;" 2>$null
            if ($orderCount) {
                Write-Host "Orders: $orderCount" -ForegroundColor White
            }
            
            # Get sync status summary
            $pendingProducts = sqlite3 $dbPath "SELECT COUNT(*) FROM local_products WHERE sync_status = 'PENDING';" 2>$null
            $pendingOrders = sqlite3 $dbPath "SELECT COUNT(*) FROM local_orders WHERE sync_status = 'PENDING';" 2>$null
            
            if ($pendingProducts -or $pendingOrders) {
                Write-Host ""
                Write-Host "Sync Status:" -ForegroundColor Cyan
                Write-Host "Pending Products: $pendingProducts" -ForegroundColor Yellow
                Write-Host "Pending Orders: $pendingOrders" -ForegroundColor Yellow
            }
        } else {
            Write-Host "SQLite CLI not found. Install SQLite to run queries." -ForegroundColor Yellow
        }
    } catch {
        Write-Host "Could not query database: $($_.Exception.Message)" -ForegroundColor Red
    }
    
    Write-Host ""
    Write-Host "Access Options:" -ForegroundColor Cyan
    Write-Host "==============" -ForegroundColor Cyan
    Write-Host "1. DB Browser for SQLite (Recommended)" -ForegroundColor White
    Write-Host "   Download: https://sqlitebrowser.org/" -ForegroundColor Gray
    Write-Host "   Then open: $dbPath" -ForegroundColor Gray
    Write-Host ""
    Write-Host "2. VS Code SQLite Extension" -ForegroundColor White
    Write-Host "   Install 'SQLite Viewer' extension" -ForegroundColor Gray
    Write-Host "   Right-click on $dbPath" -ForegroundColor Gray
    Write-Host ""
    Write-Host "3. Command Line (if SQLite installed)" -ForegroundColor White
    Write-Host "   sqlite3 `"$dbPath`"" -ForegroundColor Gray
    Write-Host ""
    Write-Host "4. Backend API" -ForegroundColor White
    Write-Host "   curl http://localhost:8080/api/offline/products" -ForegroundColor Gray
    Write-Host "   curl http://localhost:8080/api/offline/sync/status" -ForegroundColor Gray
    
    # Try to open with default application
    Write-Host ""
    $openChoice = Read-Host "Would you like to try opening the database file? (y/n)"
    if ($openChoice -eq 'y' -or $openChoice -eq 'Y') {
        try {
            Start-Process $dbPath
            Write-Host "Database file opened with default application." -ForegroundColor Green
        } catch {
            Write-Host "Could not open database file automatically." -ForegroundColor Yellow
            Write-Host "Please use one of the access methods above." -ForegroundColor Yellow
        }
    }
    
} else {
    Write-Host "Database not found at: $dbPath" -ForegroundColor Red
    Write-Host ""
    Write-Host "This means:" -ForegroundColor Yellow
    Write-Host "1. Backend hasn't been started yet" -ForegroundColor White
    Write-Host "2. No offline operations have been performed" -ForegroundColor White
    Write-Host "3. Database is in a different location" -ForegroundColor White
    Write-Host ""
    Write-Host "To create the database:" -ForegroundColor Cyan
    Write-Host "1. Start the backend: mvn spring-boot:run" -ForegroundColor White
    Write-Host "2. Perform some offline operations" -ForegroundColor White
    Write-Host "3. Run this script again" -ForegroundColor White
}

Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
