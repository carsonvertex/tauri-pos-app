# Tauri POS Application

A professional Point of Sale (POS) system built with Tauri, React, and Spring Boot.

## 🚀 Quick Start

### For New Users (First Time Setup)

1. **Install Dependencies**
   - **Double-click:** `install-dependencies.bat`
   - **Right-click** → "Run as Administrator" (recommended)
   - This will install Java, Maven, Rust, and Node.js automatically

2. **Launch the App**
   - **Double-click:** `pos-launcher.bat`
   - Enjoy the beautiful GUI launcher with progress bars!

### For Existing Users

- **Double-click:** `pos-launcher.bat` to launch directly

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
├── install-dependencies.bat      ← **FIRST: Install all dependencies**
├── pos-launcher.bat              ← **THEN: Launch the app**
├── pos-launcher-gui.ps1          ← **Beautiful GUI launcher**
├── backend/                      ← Spring Boot backend
├── frontend/                     ← React + Tauri frontend
└── tauri/                        ← Tauri configuration
```

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
