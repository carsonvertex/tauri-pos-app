# Tauri POS - Single Executable Package

## ?? Quick Start
1. **For immediate use**: Double-click TauriPOS-Launcher.bat
2. **For silent launch**: Double-click TauriPOS-Silent.vbs
3. **For installation**: Run Install-TauriPOS.bat as administrator

## ? What's Included
- **TauriPOS.exe** (7.8 MB) - Main application
- **backend.jar** (69.5 MB) - Spring Boot backend
- **jre/** - Portable Java runtime (if available)
- **data/pos_local.db** - SQLite database with sample data
- **Launchers** - Multiple startup options
- **Installer** - Professional installation script

## ? Features
- ??**True Standalone** - No external dependencies required
- ??**Portable JRE** - Java runtime included
- ??**Offline-First** - Works without internet
- ??**SQLite Database** - Local data storage
- ??**Auto-Start Backend** - Seamless operation
- ??**Professional Installer** - System integration
- ??**Silent Launch** - No console windows

## ? Technical Details
- **Frontend**: Tauri (Rust + React)
- **Backend**: Spring Boot (Java 17)
- **Database**: SQLite
- **Size**: ~80 MB total
- **Platform**: Windows 10/11

## ?? Distribution Options

### Option 1: Direct Distribution
- Zip the entire dist-single-exe folder
- Users extract and run TauriPOS-Launcher.bat

### Option 2: Professional Installation
- Run Install-TauriPOS.bat on target machine
- Creates desktop shortcuts and start menu entries
- Installs to Program Files

### Option 3: Silent Deployment
- Use TauriPOS-Silent.vbs for enterprise deployment
- No console windows or user interaction required

## ??儭?Customization
- Modify TauriPOS-Launcher.bat for custom startup behavior
- Edit ackend.jar configuration for different database paths
- Customize installer script for specific deployment needs

## ?? Support
- Check console output for backend startup issues
- Ensure ports 8080 and 1420 are available
- Run as administrator if permission issues occur
