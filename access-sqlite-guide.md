# SQLite Database Access Guide

## 📍 **Database Location**
Your SQLite database is located at:
```
./data/pos_local.db
```
(Relative to your backend application directory)

## 🛠️ **Access Methods**

### **1. SQLite Browser (Recommended)**
Download and install **DB Browser for SQLite**:
- **Download**: https://sqlitebrowser.org/
- **Features**: GUI interface, easy to use
- **Steps**:
  1. Install DB Browser for SQLite
  2. Open the application
  3. Click "Open Database"
  4. Navigate to your project folder
  5. Go to `./data/pos_local.db`
  6. Open the file

### **2. Command Line (SQLite CLI)**
If you have SQLite installed:
```bash
# Navigate to your project directory
cd C:\Users\carson.lee\vtx\test-tauri\tauri-pos-app

# Open SQLite database
sqlite3 data/pos_local.db

# Common commands:
.tables                    # List all tables
.schema                    # Show database schema
SELECT * FROM local_products;  # Query products
SELECT * FROM local_orders;    # Query orders
.quit                      # Exit SQLite
```

### **3. VS Code Extension**
Install **SQLite Viewer** extension in VS Code:
- **Extension**: "SQLite Viewer" by qwtel
- **Steps**:
  1. Install the extension
  2. Open VS Code in your project directory
  3. Right-click on `data/pos_local.db`
  4. Select "Open with SQLite Viewer"

### **4. Online SQLite Viewer**
Upload your database file to online viewers:
- **SQLite Online**: https://sqliteonline.com/
- **Steps**:
  1. Go to the website
  2. Click "Open DB"
  3. Upload your `pos_local.db` file

## 📊 **Database Schema**

### **Tables Created:**
- `local_products` - Local product data
- `local_orders` - Local order data  
- `local_order_items` - Local order item data

### **Key Columns:**
- `id` - Primary key
- `remote_id` - ID from remote database
- `sync_status` - PENDING, SYNCING, SYNCED, FAILED, CONFLICT
- `last_sync` - Last sync timestamp
- `created_at` - Creation timestamp
- `updated_at` - Last update timestamp

## 🔍 **Useful Queries**

### **View All Products:**
```sql
SELECT * FROM local_products;
```

### **View Products by Sync Status:**
```sql
SELECT * FROM local_products WHERE sync_status = 'PENDING';
```

### **View All Orders:**
```sql
SELECT * FROM local_orders;
```

### **View Order Items:**
```sql
SELECT * FROM local_order_items;
```

### **Count by Sync Status:**
```sql
SELECT sync_status, COUNT(*) FROM local_products GROUP BY sync_status;
SELECT sync_status, COUNT(*) FROM local_orders GROUP BY sync_status;
```

### **View Recent Orders:**
```sql
SELECT * FROM local_orders ORDER BY created_at DESC LIMIT 10;
```

## 🚀 **Quick Access Script**

Create a PowerShell script to quickly open the database:

```powershell
# open-sqlite.ps1
$dbPath = ".\data\pos_local.db"
if (Test-Path $dbPath) {
    Write-Host "Opening SQLite database: $dbPath"
    # Try to open with default SQLite application
    Start-Process $dbPath
} else {
    Write-Host "Database not found at: $dbPath"
    Write-Host "Make sure the backend has been started at least once."
}
```

## 🔧 **Backend API Access**

You can also access data through the backend API:

### **Get All Products:**
```bash
curl http://localhost:8080/api/offline/products
```

### **Get Sync Status:**
```bash
curl http://localhost:8080/api/offline/sync/status
```

### **Get Pending Sync Count:**
```bash
curl http://localhost:8080/api/offline/sync/pending-count
```

## 📝 **Notes**

- Database is created automatically when backend starts
- All offline operations are logged in the database
- Sync status is tracked for each record
- Database file grows as you add more data
- Backup the database file for data safety

## 🛡️ **Backup & Restore**

### **Backup:**
```bash
# Copy the database file
copy data\pos_local.db data\pos_local_backup.db
```

### **Restore:**
```bash
# Restore from backup
copy data\pos_local_backup.db data\pos_local.db
```
