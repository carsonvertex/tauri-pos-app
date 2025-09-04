# 🚀 Tauri POS App Setup Guide

This guide will help you set up the Tauri POS App on any Windows computer, automatically detecting and configuring all required dependencies.

## 📋 Prerequisites

Before running the setup script, you need to install these core tools:

### 1. **Java 17+ (Required)**
- **Download from**: [Eclipse Temurin](https://adoptium.net/) (Recommended) or [Oracle JDK](https://www.oracle.com/java/technologies/)
- **Version**: Java 17 or later
- **Installation**: Download and run the installer, or use:
  ```bash
  winget install EclipseAdoptium.Temurin.17.JDK
  ```

### 2. **Apache Maven (Required)**
- **Download from**: [Maven Official Site](https://maven.apache.org/download.cgi)
- **Version**: 3.6+ (3.9.5 recommended)
- **Installation**: Extract to a folder (e.g., `C:\Users\YourUser\maven\`)

### 3. **Rust (Required)**
- **Installation**: Visit [rustup.rs](https://rustup.rs/) or run:
  ```bash
  winget install Rustlang.Rust.MSVC
  ```

### 4. **Node.js (Required)**
- **Download from**: [Node.js Official Site](https://nodejs.org/)
- **Version**: 18+ (LTS recommended)
- **Installation**: Download and run the installer

## 🎯 Quick Setup (Recommended)

### **Option 1: PowerShell Setup (Recommended)**
```powershell
# Run as Administrator or with ExecutionPolicy Bypass
.\setup.ps1
```

### **Option 2: Batch File Setup**
```cmd
# Double-click or run from command prompt
setup.bat
```

### **Option 3: Manual Setup**
```powershell
# Set execution policy (if needed)
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope CurrentUser

# Run setup
.\setup.ps1
```

## 🔍 What the Smart Setup Script Does

The smart setup script automatically:

1. **🔍 Detects Current Setup Status**
   - Checks if all tools are available (Java, Maven, Rust, Node.js)
   - Verifies if dependencies are installed
   - Tests if backend compiles successfully

2. **🚀 Smart Launch Decision**
   - **If everything is ready** → Launches the app directly
   - **If setup is needed** → Configures environment, then launches

3. **🔍 Detects Java Installation** (if needed)
   - Searches common Java installation paths
   - Sets `JAVA_HOME` environment variable
   - Adds Java to PATH

4. **🔍 Detects Maven Installation** (if needed)
   - Checks if Maven is in PATH
   - Searches common Maven installation paths
   - Adds Maven to PATH if needed

5. **🔍 Verifies Rust Installation** (if needed)
   - Checks if Cargo is available
   - Verifies Rust toolchain

6. **🔍 Checks Node.js** (if needed)
   - Verifies Node.js and npm versions
   - Installs all project dependencies

7. **🔍 Tests Backend Compilation** (if needed)
   - Compiles the Spring Boot backend
   - Verifies Java/Maven configuration

8. **🔧 Creates Environment Script** (if needed)
   - Generates `setup-env.ps1` for future use
   - Sets up proper environment variables

9. **🎯 Launches Application**
   - Asks user if they want to start the app now
   - Automatically starts both backend and Tauri app
   - Provides fallback instructions if launch fails

## 🚀 After Setup

The smart setup script will automatically launch the application for you! However, you can also start it manually:

### **Automatic Launch (Recommended)**
The setup script will:
1. **Ask if you want to start the app now**
2. **Launch both backend and Tauri app automatically**
3. **Handle all the complexity for you**

### **Manual Start (if needed)**
```bash
# Start both services
npm run tauri:with-backend

# Or start services individually
npm run backend:dev    # Terminal 1: Backend
npm run tauri:dev      # Terminal 2: Frontend + Tauri
```

### **Environment Setup (for new terminals)**
```bash
# Run this in new terminal sessions
.\setup-env.ps1
```

## 🌐 Access Points

After starting the services:

- **Frontend Dev Server**: http://localhost:5173
- **Backend API**: http://localhost:8080/api
- **H2 Database Console**: http://localhost:8080/api/h2-console
- **Desktop App**: Tauri window will open automatically

## 🔧 Troubleshooting

### **Common Issues**

#### **1. PowerShell Execution Policy**
```powershell
# Fix execution policy
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope CurrentUser
```

#### **2. Java Not Found**
- Ensure Java 17+ is installed
- Check if `JAVA_HOME` is set correctly
- Verify Java is in PATH

#### **3. Maven Not Found**
- Download Apache Maven
- Extract to a folder
- Add `bin` folder to PATH

#### **4. Rust Not Found**
```bash
# Install Rust
winget install Rustlang.Rust.MSVC
# or visit https://rustup.rs/
```

#### **5. Node.js Dependencies Failed**
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and reinstall
Remove-Item -Recurse -Force node_modules
Remove-Item -Recurse -Force frontend/node_modules
npm run install:all
```

### **Manual Environment Setup**
If automatic setup fails, manually set environment variables:

```powershell
# Set Java
$env:JAVA_HOME = "C:\Users\YourUser\.jdks\corretto-17.0.12"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Set Maven
$env:PATH = "C:\Users\YourUser\maven\apache-maven-3.9.5\bin;$env:PATH"

# Set Rust
$env:PATH = "$env:USERPROFILE\.cargo\bin;$env:PATH"
```

## 📁 Project Structure

```
tauri-pos-app/
├── setup.ps1              # Main setup script
├── setup.bat              # Batch file wrapper
├── setup-env.ps1          # Generated environment script
├── tauri/                 # Tauri configuration
├── frontend/              # React frontend
├── backend/               # Spring Boot backend
└── package.json           # Project configuration
```

## 🆘 Getting Help

If you encounter issues:

1. **Check the error messages** from the setup script
2. **Verify prerequisites** are installed correctly
3. **Check environment variables** are set properly
4. **Review the troubleshooting section** above
5. **Check project documentation** in README.md

## 🎉 Success!

Once setup is complete, you'll have:
- ✅ Java 17+ with Maven configured
- ✅ Rust toolchain ready
- ✅ Node.js dependencies installed
- ✅ Backend compilation verified
- ✅ Environment scripts generated
- ✅ Ready to run the Tauri POS App!

Happy coding! 🚀
