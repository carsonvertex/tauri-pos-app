# Tauri POS Application

A professional Point of Sale (POS) system built with Tauri, React, and Spring Boot.

## 🚀 Quick Start

### For New Users (First Time Setup)

1. **Install Dependencies**
   - **Option A (Most User-Friendly):** Double-click `setup-gui.bat` for beautiful GUI
   - **Option B (Command-Line):** Run `.\setup.ps1` in PowerShell
   - **Option C:** Double-click `setup.bat`
   - **Option D:** Double-click `install-dependencies.bat` (legacy)

2. **Launch the App**
   - **Option A (Automatic):** The setup will launch the app automatically!
   - **Option B (Manual):** `npm run tauri:with-backend`
   - **Option C:** Double-click `pos-launcher.bat`
   - **Option D:** Use the beautiful GUI launcher

### For Existing Users

- **Quick Start:** `npm run tauri:with-backend`
- **Legacy:** Double-click `pos-launcher.bat`

## 📋 System Requirements

- **Windows 10/11** (64-bit)
- **Internet connection** (for first-time dependency installation)
- **Administrator privileges** (recommended for installation)

## 🛠️ What Gets Installed

- **Java (OpenJDK 17)** - Backend runtime
- **Maven 3.9.5** - Build tool for Java
- **Rust** - Tauri framework
- **Node.js 18.19.0** - Frontend runtime
- **NPM Dependencies** - React and Tauri packages
- **Maven Dependencies** - Spring Boot packages

## 📁 Project Structure

```
tauri-pos-app/
├── setup-gui.ps1                 ← **NEW: Beautiful GUI setup (most user-friendly)**
├── setup-gui.bat                 ← **NEW: GUI setup batch wrapper**
├── setup.ps1                     ← **NEW: Smart command-line setup script**
├── setup.bat                     ← **NEW: Command-line setup batch wrapper**
├── SETUP_GUIDE.md               ← **NEW: Comprehensive setup guide**
├── install-dependencies.bat      ← **Legacy: Install dependencies**
├── pos-launcher.bat              ← **Legacy: Launch the app**
├── pos-launcher-gui.ps1          ← **Legacy: Beautiful GUI launcher**
├── backend/                      ← Spring Boot backend
├── frontend/                     ← React + Tauri frontend
└── tauri/                        ← Tauri configuration
```

## 🔧 New Smart Setup & Launcher (Recommended)

### **Option 1: Beautiful GUI Setup (Most User-Friendly)**
The new `setup-gui.ps1` provides a **beautiful graphical interface** that:
- 🎨 **Beautiful GUI** with progress bars and status indicators
- 🔍 **Detects** if everything is already set up
- 🚀 **Launches the app directly** if ready
- ⚙️ **Sets up the environment** if needed
- 📦 **Installs** all project dependencies
- ✅ **Verifies** backend compilation
- 🔧 **Generates** environment setup scripts
- 📚 **Provides** detailed error messages and solutions

**Usage:**
```powershell
# Beautiful GUI setup - most user-friendly!
.\setup-gui.ps1

# Or double-click the batch file
setup-gui.bat
```

### **Option 2: Command-Line Setup**
The `setup.ps1` script is a **smart launcher** that automatically:
- 🔍 **Detects** if everything is already set up
- 🚀 **Launches the app directly** if ready
- ⚙️ **Sets up the environment** if needed
- 📦 **Installs** all project dependencies
- ✅ **Verifies** backend compilation
- 🔧 **Generates** environment setup scripts
- 📚 **Provides** detailed error messages and solutions

**Usage:**
```powershell
# Smart setup/launcher - does everything automatically!
.\setup.ps1

# Or use the batch file for easy double-clicking
setup.bat
```

**What happens:**
1. **If everything is ready** → App launches automatically
2. **If setup is needed** → Environment is configured, then app launches
3. **User choice** → Can choose to launch now or later

**For detailed setup instructions, see:** [SETUP_GUIDE.md](SETUP_GUIDE.md)

## 🎯 Usage Flow

1. **First Time:** `install-dependencies.bat` → `pos-launcher.bat`
2. **Every Time After:** `pos-launcher.bat`

## 🔧 Troubleshooting

### "Java not found" Error
- Run `install-dependencies.bat` as Administrator
- Restart your computer after installation

### "Maven not found" Error
- Ensure you ran the dependency installer
- Check if `%USERPROFILE%\maven` folder exists

### "Rust not found" Error
- Run `install-dependencies.bat` again
- Check if `%USERPROFILE%\.cargo` folder exists

## 📞 Support

If you encounter issues:
1. Run `install-dependencies.bat` as Administrator
2. Restart your computer
3. Try launching with `pos-launcher.bat`

## 🎨 Features

- **Beautiful GUI Launcher** with progress bars
- **Professional POS Interface**
- **Spring Boot Backend** (port 8080)
- **Tauri Desktop App** (cross-platform)
- **Real-time Progress Tracking**
- **Auto-closing Launcher**

---

**Made with ❤️ using Tauri, React, and Spring Boot**
