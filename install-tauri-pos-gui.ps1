# Tauri POS Application GUI Installer
# Run this script as Administrator for best results

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing
Add-Type -AssemblyName System.IO.Compression.FileSystem

# Create the main form
$form = New-Object System.Windows.Forms.Form
$form.Text = "Tauri POS Application Installer"
$form.Size = New-Object System.Drawing.Size(700, 600)
$form.StartPosition = "CenterScreen"
$form.FormBorderStyle = "FixedDialog"
$form.MaximizeBox = $false
$form.MinimizeBox = $false
$form.BackColor = [System.Drawing.Color]::FromArgb(240, 240, 240)

# Title
$titleLabel = New-Object System.Windows.Forms.Label
$titleLabel.Text = "Tauri POS Application Installer"
$titleLabel.Font = New-Object System.Drawing.Font("Segoe UI", 18, [System.Drawing.FontStyle]::Bold)
$titleLabel.ForeColor = [System.Drawing.Color]::FromArgb(64, 64, 64)
$titleLabel.TextAlign = [System.Drawing.ContentAlignment]::MiddleCenter
$titleLabel.Dock = [System.Windows.Forms.DockStyle]::Top
$titleLabel.Height = 60
$form.Controls.Add($titleLabel)

# Subtitle
$subtitleLabel = New-Object System.Windows.Forms.Label
$subtitleLabel.Text = "Professional Point of Sale System - Complete Installation Package"
$subtitleLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$subtitleLabel.ForeColor = [System.Drawing.Color]::FromArgb(100, 100, 100)
$subtitleLabel.TextAlign = [System.Drawing.ContentAlignment]::MiddleCenter
$subtitleLabel.Dock = [System.Windows.Forms.DockStyle]::Top
$subtitleLabel.Height = 30
$form.Controls.Add($subtitleLabel)

# Status panel
$statusPanel = New-Object System.Windows.Forms.Panel
$statusPanel.Dock = [System.Windows.Forms.DockStyle]::Top
$statusPanel.Height = 100
$statusPanel.Padding = New-Object System.Windows.Forms.Padding(20)

$statusLabel = New-Object System.Windows.Forms.Label
$statusLabel.Text = "Ready to install..."
$statusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$statusLabel.Location = New-Object System.Drawing.Point(0, 0)
$statusLabel.AutoSize = $true
$statusLabel.ForeColor = [System.Drawing.Color]::FromArgb(80, 80, 80)

$progressBar = New-Object System.Windows.Forms.ProgressBar
$progressBar.Location = New-Object System.Drawing.Point(0, 30)
$progressBar.Size = New-Object System.Drawing.Size(640, 25)
$progressBar.Style = "Continuous"

$stepLabel = New-Object System.Windows.Forms.Label
$stepLabel.Text = "Click 'Install Now' to begin the installation process"
$stepLabel.Font = New-Object System.Drawing.Font("Segoe UI", 9)
$stepLabel.Location = New-Object System.Drawing.Point(0, 65)
$stepLabel.AutoSize = $true
$stepLabel.ForeColor = [System.Drawing.Color]::FromArgb(80, 80, 80)

$statusPanel.Controls.AddRange(@($statusLabel, $progressBar, $stepLabel))
$form.Controls.Add($statusPanel)

# Log panel
$logPanel = New-Object System.Windows.Forms.Panel
$logPanel.Dock = [System.Windows.Forms.DockStyle]::Fill
$logPanel.Padding = New-Object System.Windows.Forms.Padding(20)

$logLabel = New-Object System.Windows.Forms.Label
$logLabel.Text = "Installation Log:"
$logLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$logLabel.Location = New-Object System.Drawing.Point(0, 0)
$logLabel.AutoSize = $true

$logTextBox = New-Object System.Windows.Forms.TextBox
$logTextBox.Location = New-Object System.Drawing.Point(0, 25)
$logTextBox.Size = New-Object System.Drawing.Size(640, 200)
$logTextBox.Multiline = $true
$logTextBox.ScrollBars = "Vertical"
$logTextBox.ReadOnly = $true
$logTextBox.Font = New-Object System.Drawing.Font("Consolas", 9)
$logTextBox.BackColor = [System.Drawing.Color]::FromArgb(245, 245, 245)

$logPanel.Controls.AddRange(@($logLabel, $logTextBox))
$form.Controls.Add($logPanel)

# Button panel
$buttonPanel = New-Object System.Windows.Forms.Panel
$buttonPanel.Dock = [System.Windows.Forms.DockStyle]::Bottom
$buttonPanel.Height = 70
$buttonPanel.Padding = New-Object System.Windows.Forms.Padding(20)

$installButton = New-Object System.Windows.Forms.Button
$installButton.Text = "Install Now"
$installButton.Size = New-Object System.Drawing.Size(140, 40)
$installButton.Location = New-Object System.Drawing.Point(300, 15)
$installButton.Font = New-Object System.Drawing.Font("Segoe UI", 11, [System.Drawing.FontStyle]::Bold)
$installButton.BackColor = [System.Drawing.Color]::FromArgb(0, 120, 215)
$installButton.ForeColor = [System.Drawing.Color]::White
$installButton.FlatStyle = "Flat"
$installButton.Cursor = [System.Windows.Forms.Cursors]::Hand

$cancelButton = New-Object System.Windows.Forms.Button
$cancelButton.Text = "Cancel"
$cancelButton.Size = New-Object System.Drawing.Size(140, 40)
$cancelButton.Location = New-Object System.Drawing.Point(460, 15)
$cancelButton.Font = New-Object System.Drawing.Font("Segoe UI", 11)
$cancelButton.BackColor = [System.Drawing.Color]::FromArgb(200, 200, 200)
$cancelButton.ForeColor = [System.Drawing.Color]::Black
$cancelButton.FlatStyle = "Flat"
$cancelButton.Cursor = [System.Windows.Forms.Cursors]::Hand

$buttonPanel.Controls.AddRange(@($installButton, $cancelButton))
$form.Controls.Add($buttonPanel)

# Global variables
$script:isInstalling = $false
$script:currentStep = 0
$script:totalSteps = 8

# Functions
function Write-Log {
    param([string]$Message)
    $timestamp = Get-Date -Format "HH:mm:ss"
    $logMessage = "[$timestamp] $Message"
    $logTextBox.AppendText("$logMessage`r`n")
    $logTextBox.ScrollToCaret()
    [System.Windows.Forms.Application]::DoEvents()
}

function Update-Progress {
    param([string]$Message, [int]$Step)
    $script:currentStep = $Step
    $progress = ($Step * 100) / $script:totalSteps
    $progressBar.Value = $progress
    $stepLabel.Text = $Message
    $statusLabel.Text = "Step $Step of $($script:totalSteps): $Message"
    Write-Log $Message
    [System.Windows.Forms.Application]::DoEvents()
}

function Check-AdminStatus {
    $isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")
    if ($isAdmin) {
        $stepLabel.Text = "✓ Running as Administrator - Excellent!"
        $stepLabel.ForeColor = [System.Drawing.Color]::Green
        Write-Log "Running as Administrator - Excellent!"
    } else {
        $stepLabel.Text = "⚠️  Warning: Not running as Administrator. Some installations may fail."
        $stepLabel.ForeColor = [System.Drawing.Color]::Orange
        Write-Log "Warning: Not running as Administrator. Some installations may fail."
    }
}

function Install-Java {
    try {
        Update-Progress "Installing Java 17 (OpenJDK)..." 2
        
        $javaUrl = "https://aka.ms/download-jdk/microsoft-jdk-17.0.15-windows-x64.msi"
        $javaFile = Join-Path $env:TEMP "microsoft-jdk-17.msi"
        
        Write-Log "Downloading Microsoft OpenJDK 17..."
        Invoke-WebRequest -Uri $javaUrl -OutFile $javaFile -UseBasicParsing
        
        Write-Log "Installing Java 17..."
        Start-Process -FilePath "msiexec.exe" -ArgumentList "/i", $javaFile, "/quiet", "/norestart" -Wait
        
        if (Test-Path $javaFile) {
            Remove-Item $javaFile -Force
        }
        
        Write-Log "Java 17 installation completed successfully"
        return $true
    } catch {
        Write-Log "Java installation failed: $($_.Exception.Message)"
        return $false
    }
}

function Install-Maven {
    try {
        Update-Progress "Installing Maven 3.9.5..." 3
        
        $mavenUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip"
        $mavenFile = Join-Path $env:TEMP "maven.zip"
        $mavenDir = Join-Path $env:USERPROFILE "maven"
        
        if (-not (Test-Path $mavenDir)) {
            New-Item -ItemType Directory -Path $mavenDir -Force | Out-Null
        }
        
        Write-Log "Downloading Apache Maven 3.9.5..."
        Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenFile -UseBasicParsing
        
        Write-Log "Extracting Maven..."
        Expand-Archive -Path $mavenFile -DestinationPath $mavenDir -Force
        
        if (Test-Path $mavenFile) {
            Remove-Item $mavenFile -Force
        }
        
        Write-Log "Maven installation completed successfully"
        return $true
    } catch {
        Write-Log "Maven installation failed: $($_.Exception.Message)"
        return $false
    }
}

function Install-Rust {
    try {
        Update-Progress "Installing Rust & Cargo..." 4
        
        $rustUrl = "https://win.rustup.rs/x86_64"
        $rustFile = Join-Path $env:TEMP "rustup-init.exe"
        
        Write-Log "Downloading Rust installer..."
        Invoke-WebRequest -Uri $rustUrl -OutFile $rustFile -UseBasicParsing
        
        Write-Log "Installing Rust..."
        Start-Process -FilePath $rustFile -ArgumentList "--default-toolchain", "stable", "--profile", "default", "-y" -Wait
        
        if (Test-Path $rustFile) {
            Remove-Item $rustFile -Force
        }
        
        Write-Log "Rust installation completed successfully"
        return $true
    } catch {
        Write-Log "Rust installation failed: $($_.Exception.Message)"
        return $false
    }
}

function Set-EnvironmentVariables {
    try {
        Update-Progress "Setting environment variables..." 5
        
        $javaHome = Join-Path $env:USERPROFILE ".jdks\ms-17.0.15"
        $mavenPath = Join-Path $env:USERPROFILE "maven\apache-maven-3.9.5\bin"
        $cargoPath = Join-Path $env:USERPROFILE ".cargo\bin"
        
        Write-Log "Setting JAVA_HOME environment variable..."
        [Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHome, "Machine")
        
        Write-Log "Adding Maven and Rust to PATH..."
        $currentPath = [Environment]::GetEnvironmentVariable("PATH", "Machine")
        if (-not $currentPath) { $currentPath = "" }
        $newPath = "$mavenPath;$cargoPath;$currentPath"
        [Environment]::SetEnvironmentVariable("PATH", $newPath, "Machine")
        
        Write-Log "Environment variables set successfully"
        return $true
    } catch {
        Write-Log "Failed to set environment variables: $($_.Exception.Message)"
        return $false
    }
}

function Install-Dependencies {
    try {
        Update-Progress "Installing project dependencies..." 6
        
        Write-Log "Installing Node.js dependencies..."
        Set-Location $PSScriptRoot
        npm install
        
        Write-Log "Installing frontend dependencies..."
        Set-Location "frontend"
        npm install
        Set-Location ..
        
        Write-Log "Installing backend dependencies..."
        Set-Location "backend"
        mvn dependency:resolve
        Set-Location ..
        
        Write-Log "Dependencies installed successfully"
        return $true
    } catch {
        Write-Log "Failed to install dependencies: $($_.Exception.Message)"
        return $false
    }
}

function Create-DesktopShortcut {
    try {
        Update-Progress "Creating desktop shortcut..." 7
        
        $desktopPath = [Environment]::GetFolderPath("Desktop")
        $shortcutPath = Join-Path $desktopPath "Tauri POS App.lnk"
        $targetPath = Join-Path $PSScriptRoot "start-tauri-with-backend.bat"
        $iconPath = Join-Path $PSScriptRoot "tauri\icons\icon.ico"
        
        Write-Log "Creating desktop shortcut..."
        
        $WshShell = New-Object -comObject WScript.Shell
        $Shortcut = $WshShell.CreateShortcut($shortcutPath)
        $Shortcut.TargetPath = $targetPath
        $Shortcut.WorkingDirectory = $PSScriptRoot
        $Shortcut.Description = "Tauri POS Application"
        $Shortcut.IconLocation = $iconPath
        $Shortcut.Save()
        
        Write-Log "Desktop shortcut created successfully"
        return $true
    } catch {
        Write-Log "Failed to create desktop shortcut: $($_.Exception.Message)"
        return $false
    }
}

# Event handlers
$installButton.Add_Click({
    if ($script:isInstalling) { return }
    
    $script:isInstalling = $true
    $installButton.Enabled = $false
    $cancelButton.Enabled = $false
    $progressBar.Value = 0
    $script:currentStep = 0
    
    try {
        Write-Log "Starting Tauri POS Application installation..."
        
        # Check system requirements
        Update-Progress "Checking system requirements..." 1
        
        # Install components
        if (-not (Install-Java)) { throw "Failed to install Java 17" }
        if (-not (Install-Maven)) { throw "Failed to install Maven" }
        if (-not (Install-Rust)) { throw "Failed to install Rust" }
        if (-not (Set-EnvironmentVariables)) { throw "Failed to set environment variables" }
        if (-not (Install-Dependencies)) { throw "Failed to install dependencies" }
        if (-not (Create-DesktopShortcut)) { throw "Failed to create desktop shortcut" }
        
        # Complete
        Update-Progress "Installation complete!" 8
        $progressBar.Value = 100
        
        Write-Log "Installation completed successfully!"
        [System.Windows.Forms.MessageBox]::Show(
            "Tauri POS Application has been installed successfully!`n`n" +
            "A desktop shortcut has been created.`n" +
            "You may need to restart your computer for all changes to take effect.",
            "Installation Complete",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Information
        )
        
    } catch {
        Write-Log "Installation failed: $($_.Exception.Message)"
        [System.Windows.Forms.MessageBox]::Show(
            "Installation failed: $($_.Exception.Message)",
            "Error",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Error
        )
    } finally {
        $script:isInstalling = $false
        $installButton.Enabled = $true
        $cancelButton.Enabled = $true
    }
})

$cancelButton.Add_Click({
    if ($script:isInstalling) {
        $result = [System.Windows.Forms.MessageBox]::Show(
            "Installation is in progress. Are you sure you want to cancel?",
            "Confirm Cancel",
            [System.Windows.Forms.MessageBoxButtons]::YesNo,
            [System.Windows.Forms.MessageBoxIcon]::Question
        )
        if ($result -eq [System.Windows.Forms.DialogResult]::Yes) {
            $script:isInstalling = $false
            Write-Log "Installation cancelled by user."
        }
    } else {
        $form.Close()
    }
})

# Initialize
Check-AdminStatus
Write-Log "Tauri POS Application Installer ready"
Write-Log "Click 'Install Now' to begin the installation process"

# Show the form
$form.ShowDialog()
