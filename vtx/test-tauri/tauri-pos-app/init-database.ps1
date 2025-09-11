# Database Initialization Script
# This script initializes the SQLite database with new product tables
# while preserving only the user table data

param(
    [string]$DbPath = "data/pos_local.db",
    [switch]$Force = $false
)

Write-Host "🚀 Initializing SQLite Database..." -ForegroundColor Green
Write-Host "Database Path: $DbPath" -ForegroundColor Yellow

# Check if SQLite3 is available
try {
    $sqliteVersion = sqlite3 --version
    Write-Host "✅ SQLite3 found: $sqliteVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ SQLite3 not found. Please install SQLite3 first." -ForegroundColor Red
    Write-Host "Download from: https://www.sqlite.org/download.html" -ForegroundColor Yellow
    exit 1
}

# Check if database file exists
if (Test-Path $DbPath) {
    if ($Force) {
        Write-Host "⚠️  Force mode enabled. Backing up existing database..." -ForegroundColor Yellow
        $backupPath = "$DbPath.backup.$(Get-Date -Format 'yyyyMMdd-HHmmss')"
        Copy-Item $DbPath $backupPath
        Write-Host "📁 Backup created: $backupPath" -ForegroundColor Cyan
    } else {
        Write-Host "⚠️  Database file already exists: $DbPath" -ForegroundColor Yellow
        $response = Read-Host "Do you want to continue? This will recreate the database. (y/N)"
        if ($response -ne 'y' -and $response -ne 'Y') {
            Write-Host "❌ Operation cancelled by user." -ForegroundColor Red
            exit 0
        }
        
        # Create backup
        $backupPath = "$DbPath.backup.$(Get-Date -Format 'yyyyMMdd-HHmmss')"
        Copy-Item $DbPath $backupPath
        Write-Host "📁 Backup created: $backupPath" -ForegroundColor Cyan
    }
}

# Create data directory if it doesn't exist
$dataDir = Split-Path $DbPath -Parent
if (!(Test-Path $dataDir)) {
    Write-Host "📁 Creating data directory: $dataDir" -ForegroundColor Cyan
    New-Item -ItemType Directory -Path $dataDir -Force | Out-Null
}

# Create the SQL initialization script
$sqlScript = @"
-- Drop all existing tables
DROP TABLE IF EXISTS local_order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_barcode;
DROP TABLE IF EXISTS product_description;
DROP TABLE IF EXISTS local_product;
DROP TABLE IF EXISTS local_product_barcode;
DROP TABLE IF EXISTS local_product_description;
DROP TABLE IF EXISTS product;

-- Create the users table (preserve existing structure) - only if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
    userid INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    hashed_password TEXT NOT NULL,
    permission TEXT NOT NULL DEFAULT 'user',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Insert default user data only if they don't exist
INSERT OR IGNORE INTO users (userid, username, hashed_password, permission, created_at, updated_at) VALUES
(2, 'testuser', '\\\.IjdQj8Kz8Kz8Kz8Kz8Kz8Kz8Kz8Kz8K', 'user', '2025-09-10 07:30:41', '2025-09-10 07:30:41'),
(3, 'admin', '$2a$10$WMoSdoKBdONd0Nq39g8AJ.XQ28MjrdS09SbIZVdm0qarEM2QT3DjW', 'admin', NULL, NULL),
(4, 'user1', '$2a$10$WMoSdoKBdONd0Nq39g8AJ.XQ28MjrdS09SbIZVdm0qarEM2QT3DjW', 'user', '2025-09-10 07:51:43', '2025-09-10 07:51:43');

-- Create local SQLite tables (mirroring MySQL entities with local_ prefix)
CREATE TABLE local_product (
    product_id INTEGER PRIMARY KEY AUTOINCREMENT,
    status INTEGER NOT NULL DEFAULT 1,
    brand_id INTEGER,
    ebay_id INTEGER,
    model_number VARCHAR(255),
    sku VARCHAR(255),
    date_available DATE,
    date_backorder DATE,
    date_preorder DATE,
    qty_preorder INTEGER DEFAULT 0,
    barcode VARCHAR(45),
    weight DECIMAL(10,3),
    weight_class_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER NOT NULL,
    updated_by INTEGER NOT NULL
);

CREATE TABLE local_product_barcode (
    product_id INTEGER NOT NULL,
    barcode VARCHAR(45) NOT NULL,
    status INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by INTEGER NOT NULL,
    updated_by INTEGER NOT NULL,
    PRIMARY KEY (product_id, barcode),
    FOREIGN KEY (product_id) REFERENCES local_product(product_id) ON DELETE CASCADE
);

CREATE TABLE local_product_description (
    product_id INTEGER NOT NULL,
    site_id INTEGER NOT NULL,
    language_id INTEGER NOT NULL,
    name VARCHAR(255),
    description TEXT,
    feature TEXT,
    specification TEXT,
    include TEXT,
    required TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by INTEGER NOT NULL,
    updated_by INTEGER NOT NULL,
    PRIMARY KEY (product_id, site_id, language_id),
    FOREIGN KEY (product_id) REFERENCES local_product(product_id) ON DELETE CASCADE
);
"@

# Write SQL script to temporary file
$tempSqlFile = [System.IO.Path]::GetTempFileName() + ".sql"
$sqlScript | Out-File -FilePath $tempSqlFile -Encoding UTF8

try {
    Write-Host "🔧 Running database initialization..." -ForegroundColor Green
    
    # Execute SQL script using PowerShell's Get-Content and pipe
    Get-Content $tempSqlFile | sqlite3 $DbPath
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Database initialization completed successfully!" -ForegroundColor Green
    } else {
        Write-Host "❌ Database initialization failed!" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "❌ Error during database initialization: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
} finally {
    # Clean up temporary file
    if (Test-Path $tempSqlFile) {
        Remove-Item $tempSqlFile -Force
    }
}

# Verify the setup
Write-Host "🔍 Verifying database setup..." -ForegroundColor Green
try {
    $tables = sqlite3 $DbPath "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;"
    Write-Host "📋 Tables created:" -ForegroundColor Cyan
    $tables | ForEach-Object { Write-Host "  - $_" -ForegroundColor White }
    
    $userCount = sqlite3 $DbPath "SELECT COUNT(*) FROM users;"
    Write-Host "👥 Users in database: $userCount" -ForegroundColor Cyan
    
    $localProductCount = sqlite3 $DbPath "SELECT COUNT(*) FROM local_product;"
    Write-Host "📦 Local products created: $localProductCount" -ForegroundColor Cyan
    
} catch {
    Write-Host "⚠️  Could not verify database setup: $($_.Exception.Message)" -ForegroundColor Yellow
}

Write-Host "🎉 Database initialization completed!" -ForegroundColor Green
Write-Host "📍 Database location: $DbPath" -ForegroundColor Yellow
Write-Host "💡 You can now start your application with the new database structure." -ForegroundColor Cyan
