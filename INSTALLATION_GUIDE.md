# Tauri POS Application - Installation Guide

## 🚀 **Quick Start for End Users**

This guide will help you install and run the Tauri POS Application on your Windows computer.

## 📋 **What You'll Get**

- **Desktop POS Application** - Full-screen, professional interface
- **Spring Boot Backend** - Robust API server for business logic
- **React Frontend** - Modern, responsive user interface
- **Tauri Desktop Wrapper** - Native Windows application

## 🔧 **System Requirements**

- **Windows 10/11** (64-bit)
- **4GB RAM** minimum (8GB recommended)
- **2GB free disk space**
- **Internet connection** (for downloading dependencies)

## 📥 **Installation Steps**

### **Step 1: Extract the Project**
1. Copy the project folder from USB to your computer
2. Extract/unzip if it's compressed
3. Place in a permanent location (e.g., `C:\TauriPOS`)

### **Step 2: Run the Installer**

**Option A: GUI Installer (Recommended)**
1. **Double-click** `Install-GUI.bat` or `install-tauri-pos-gui.ps1`
2. **Right-click** and select **"Run as administrator"** (recommended)
3. Use the modern GUI interface with progress bars and real-time logs
4. Click **"Install Now"** and watch the progress

**Option B: Command Line Installer**
1. **Right-click** on `install-tauri-pos.bat`
2. Select **"Run as administrator"** (recommended)
3. Follow the on-screen instructions
4. Wait for all components to install

### **Step 3: Launch the Application**
1. **Double-click** `launch-tauri-pos.bat`
2. Or use the desktop shortcut: **"Tauri POS App"**
3. Wait for the application to start
4. Enjoy your POS system!

## 🎯 **What the Installer Does**

The installer automatically downloads and installs:

✅ **Java 17 (OpenJDK)** - Required for Spring Boot backend  
✅ **Maven 3.9.5** - Build tool for Java applications  
✅ **Rust & Cargo** - Required for Tauri desktop wrapper  
✅ **Node.js Dependencies** - Frontend and build tools  
✅ **Environment Variables** - System configuration  
✅ **Desktop Shortcut** - Easy access to your app  

## 🚨 **Troubleshooting**

### **"Java is not installed" Error**
- Run the installer as administrator
- Restart your computer after installation
- Check if Java is in your PATH

### **"Maven is not installed" Error**
- Ensure you ran the installer completely
- Check if Maven folder exists in your user directory
- Restart your computer

### **"Rust/Cargo is not installed" Error**
- Run the installer again
- Check if `.cargo` folder exists in your user directory
- Restart your computer

### **White Screen or App Won't Start**
- Check if backend is running (port 8080)
- Look for error messages in the terminal
- Ensure all dependencies are installed

## 📁 **File Structure**

```
tauri-pos-app/
├── install-tauri-pos.bat      # Main installer (run as admin)
├── install-tauri-pos.ps1      # PowerShell installer (alternative)
├── launch-tauri-pos.bat       # Simple launcher for users
├── start-tauri-with-backend.bat # Advanced launcher
├── frontend/                  # React frontend code
├── backend/                   # Spring Boot backend code
├── tauri/                     # Tauri desktop configuration
└── README.md                  # This file
```

## 🔄 **Updating the Application**

To update to a newer version:

1. **Backup** your current installation
2. **Replace** the project folder with the new version
3. **Run** `install-tauri-pos.bat` again
4. **Restart** your computer

## 📞 **Support**

If you encounter issues:

1. **Check** the troubleshooting section above
2. **Ensure** you ran the installer as administrator
3. **Verify** all dependencies are installed
4. **Check** Windows Event Viewer for errors
5. **Contact** your system administrator

## 🎉 **After Installation**

Once everything is installed:

- **Desktop shortcut** will be created automatically
- **Environment variables** will be configured
- **All dependencies** will be ready
- **Application** will launch with one click

## 💡 **Pro Tips**

- **Run installer as administrator** for best results
- **Restart your computer** after installation
- **Keep the project folder** in a permanent location
- **Don't move** the project folder after installation
- **Use the desktop shortcut** for easiest access

---

**Happy POS-ing! 🎯**
