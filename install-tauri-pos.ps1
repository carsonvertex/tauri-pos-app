# Tauri POS Application Installer
# Run this script as Administrator for best results

param(
    [switch]$SkipAdminCheck
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Tauri POS Application Installer" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "This installer will:" -ForegroundColor Yellow
Write-Host "1. Install Java 17 (OpenJDK)" -ForegroundColor White
Write-Host "2. Install Maven 3.9.5" -ForegroundColor White
Write-Host "3. Install Rust (for Tauri)" -ForegroundColor White
Write-Host "4. Set up environment variables" -ForegroundColor White
Write-Host "5. Create desktop shortcut" -ForegroundColor White
Write-Host "6. Configure the application" -ForegroundColor White
Write-Host ""

if (-not $SkipAdminCheck) {
    # Check if running as administrator
    $isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")
    
    if (-not $isAdmin) {
        Write-Host "WARNING: This script is not running as Administrator!" -ForegroundColor Red
        Write-Host "Some installations may fail. Consider running as Administrator." -ForegroundColor Yellow
        Write-Host ""
        $response = Read-Host "Do you want to continue anyway? (y/N)"
        if ($response -ne "y" -and $response -ne "Y") {
            Write-Host "Installation cancelled." -ForegroundColor Red
            exit 1
        }
    } else {
        Write-Host "Running as Administrator - excellent!" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "Press any key to continue..." -ForegroundColor Cyan
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

Write-Host ""
Write-Host "Starting installation process..." -ForegroundColor Green
Write-Host ""

# Function to download file with progress
function Download-File {
    param(
        [string]$Url,
        [string]$OutFile,
        [string]$Description
    )
    
    Write-Host "Downloading $Description..." -ForegroundColor Yellow
    try {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        Invoke-WebRequest -Uri $Url -OutFile $OutFile -UseBasicParsing
        Write-Host "✓ $Description downloaded successfully" -ForegroundColor Green
        return $true
    } catch {
        Write-Host "✗ Failed to download $Description" -ForegroundColor Red
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

# Function to install Java
function Install-Java {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Installing Java 17 (OpenJDK)..." -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    $javaUrl = "https://aka.ms/download-jdk/microsoft-jdk-17.0.15-windows-x64.msi"
    $javaFile = "$env:TEMP\microsoft-jdk-17.msi"
    
    if (Download-File -Url $javaUrl -OutFile $javaFile -Description "Microsoft OpenJDK 17") {
        Write-Host "Installing Java 17..." -ForegroundColor Yellow
        try {
            Start-Process -FilePath "msiexec.exe" -ArgumentList "/i", $javaFile, "/quiet", "/norestart" -Wait
            Write-Host "✓ Java 17 installation completed" -ForegroundColor Green
        } catch {
            Write-Host "✗ Java 17 installation failed" -ForegroundColor Red
        }
        Remove-Item $javaFile -Force -ErrorAction SilentlyContinue
    }
}

# Function to install Maven
function Install-Maven {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Installing Maven 3.9.5..." -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    $mavenUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip"
    $mavenFile = "$env:TEMP\maven.zip"
    $mavenDir = "$env:USERPROFILE\maven"
    
    if (-not (Test-Path $mavenDir)) {
        New-Item -ItemType Directory -Path $mavenDir -Force | Out-Null
    }
    
    if (Download-File -Url $mavenUrl -OutFile $mavenFile -Description "Apache Maven 3.9.5") {
        Write-Host "Extracting Maven..." -ForegroundColor Yellow
        try {
            Expand-Archive -Path $mavenFile -DestinationPath $mavenDir -Force
            Write-Host "✓ Maven installation completed" -ForegroundColor Green
        } catch {
            Write-Host "✗ Maven extraction failed" -ForegroundColor Red
        }
        Remove-Item $mavenFile -Force -ErrorAction SilentlyContinue
    }
}

# Function to install Rust
function Install-Rust {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Installing Rust..." -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    $rustUrl = "https://win.rustup.rs/x86_64"
    $rustFile = "$env:TEMP\rustup-init.exe"
    
    if (Download-File -Url $rustUrl -OutFile $rustFile -Description "Rust installer") {
        Write-Host "Installing Rust..." -ForegroundColor Yellow
        try {
            Start-Process -FilePath $rustFile -ArgumentList "--default-toolchain", "stable", "--profile", "default", "-y" -Wait
            Write-Host "✓ Rust installation completed" -ForegroundColor Green
        } catch {
            Write-Host "✗ Rust installation failed" -ForegroundColor Red
        }
        Remove-Item $rustFile -Force -ErrorAction SilentlyContinue
    }
}

# Function to set environment variables
function Set-EnvironmentVariables {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Setting up environment variables..." -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    try {
        # Set for current session
        $env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
        $env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
        
        # Add to system environment variables
        Write-Host "Adding environment variables to system..." -ForegroundColor Yellow
        [Environment]::SetEnvironmentVariable("JAVA_HOME", "$env:USERPROFILE\.jdks\ms-17.0.15", "Machine")
        
        Write-Host "✓ Environment variables set successfully" -ForegroundColor Green
    } catch {
        Write-Host "✗ Failed to set environment variables" -ForegroundColor Red
        Write-Host "You may need to set them manually" -ForegroundColor Yellow
    }
}

# Function to install dependencies
function Install-Dependencies {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Installing Node.js dependencies..." -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    try {
        Write-Host "Installing project dependencies..." -ForegroundColor Yellow
        npm install
        
        Write-Host "Installing frontend dependencies..." -ForegroundColor Yellow
        Set-Location frontend
        npm install
        Set-Location ..
        
        Write-Host "Installing backend dependencies..." -ForegroundColor Yellow
        Set-Location backend
        mvn dependency:resolve
        Set-Location ..
        
        Write-Host "✓ All dependencies installed successfully" -ForegroundColor Green
    } catch {
        Write-Host "✗ Failed to install some dependencies" -ForegroundColor Red
        Write-Host "You may need to install them manually" -ForegroundColor Yellow
    }
}

# Function to create desktop shortcut
function Create-DesktopShortcut {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Creating desktop shortcut..." -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    try {
        $WshShell = New-Object -comObject WScript.Shell
        $Shortcut = $WshShell.CreateShortcut("$env:USERPROFILE\Desktop\Tauri POS App.lnk")
        $Shortcut.TargetPath = "$PWD\start-tauri-with-backend.bat"
        $Shortcut.WorkingDirectory = $PWD
        $Shortcut.Description = "Tauri POS Application"
        $Shortcut.IconLocation = "$PWD\tauri\icons\icon.ico"
        $Shortcut.Save()
        
        Write-Host "✓ Desktop shortcut created successfully" -ForegroundColor Green
    } catch {
        Write-Host "✗ Failed to create desktop shortcut" -ForegroundColor Red
    }
}

# Main installation process
try {
    Install-Java
    Install-Maven
    Install-Rust
    Set-EnvironmentVariables
    Install-Dependencies
    Create-DesktopShortcut
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "Installation Complete!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Your Tauri POS application is now ready!" -ForegroundColor Green
    Write-Host ""
    Write-Host "What was installed:" -ForegroundColor Yellow
    Write-Host "- Java 17 (OpenJDK)" -ForegroundColor White
    Write-Host "- Maven 3.9.5" -ForegroundColor White
    Write-Host "- Rust (with Cargo)" -ForegroundColor White
    Write-Host "- All project dependencies" -ForegroundColor White
    Write-Host ""
    Write-Host "Desktop shortcut created: 'Tauri POS App'" -ForegroundColor Green
    Write-Host ""
    Write-Host "To start the application:" -ForegroundColor Yellow
    Write-Host "1. Double-click 'Tauri POS App' on your desktop" -ForegroundColor White
    Write-Host "2. Or run: npm run tauri:with-backend" -ForegroundColor White
    Write-Host ""
    Write-Host "Note: You may need to restart your computer for all" -ForegroundColor Cyan
    Write-Host "environment variables to take effect." -ForegroundColor Cyan
    Write-Host ""
    
} catch {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "Installation failed!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please check the errors above and try again." -ForegroundColor Yellow
}

Write-Host "Press any key to exit..." -ForegroundColor Cyan
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
