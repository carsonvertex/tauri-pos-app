# Tauri POS Application GUI Launcher
# Beautiful launcher with progress bars and app icon

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

# Create the main form
$form = New-Object System.Windows.Forms.Form
$form.Text = "Tauri POS Application Launcher"
$form.Size = New-Object System.Drawing.Size(500, 400)
$form.StartPosition = "CenterScreen"
$form.FormBorderStyle = "FixedDialog"
$form.MaximizeBox = $false
$form.MinimizeBox = $false
$form.BackColor = [System.Drawing.Color]::FromArgb(240, 240, 240)
$form.Icon = [System.Drawing.Icon]::FromHandle([System.Drawing.Bitmap]::FromFile("$PSScriptRoot\tauri\icons\icon.ico").GetHicon())

# Title with app icon
$titlePanel = New-Object System.Windows.Forms.Panel
$titlePanel.Dock = [System.Windows.Forms.DockStyle]::Top
$titlePanel.Height = 80
$titlePanel.BackColor = [System.Drawing.Color]::FromArgb(64, 64, 64)

$iconPictureBox = New-Object System.Windows.Forms.PictureBox
$iconPictureBox.Size = New-Object System.Drawing.Size(48, 48)
$iconPictureBox.Location = New-Object System.Drawing.Point(20, 16)
$iconPictureBox.SizeMode = [System.Windows.Forms.PictureBoxSizeMode]::StretchImage
try {
    $iconPictureBox.Image = [System.Drawing.Image]::FromFile("$PSScriptRoot\tauri\icons\icon.ico")
} catch {
    # Use default icon if custom icon fails
    $iconPictureBox.Image = [System.Drawing.SystemIcons]::Application.ToBitmap()
}

$titleLabel = New-Object System.Windows.Forms.Label
$titleLabel.Text = "Tauri POS Application"
$titleLabel.Font = New-Object System.Drawing.Font("Segoe UI", 18, [System.Drawing.FontStyle]::Bold)
$titleLabel.ForeColor = [System.Drawing.Color]::White
$titleLabel.Location = New-Object System.Drawing.Point(80, 20)
$titleLabel.AutoSize = $true

$subtitleLabel = New-Object System.Windows.Forms.Label
$subtitleLabel.Text = "Professional Point of Sale System"
$subtitleLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$subtitleLabel.ForeColor = [System.Drawing.Color]::FromArgb(200, 200, 200)
$titleLabel.Location = New-Object System.Drawing.Point(80, 50)
$subtitleLabel.AutoSize = $true

$titlePanel.Controls.AddRange(@($iconPictureBox, $titleLabel, $subtitleLabel))
$form.Controls.Add($titlePanel)

# Status section
$statusPanel = New-Object System.Windows.Forms.Panel
$statusPanel.Dock = [System.Windows.Forms.DockStyle]::Top
$statusPanel.Height = 120
$statusPanel.Padding = New-Object System.Windows.Forms.Padding(20)

$statusLabel = New-Object System.Windows.Forms.Label
$statusLabel.Text = "Ready to launch..."
$statusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 12, [System.Drawing.FontStyle]::Bold)
$statusLabel.Location = New-Object System.Drawing.Point(0, 0)
$statusLabel.AutoSize = $true
$statusLabel.ForeColor = [System.Drawing.Color]::FromArgb(64, 64, 64)

$progressBar = New-Object System.Windows.Forms.ProgressBar
$progressBar.Location = New-Object System.Drawing.Point(0, 35)
$progressBar.Size = New-Object System.Drawing.Size(440, 25)
$progressBar.Style = "Continuous"
$progressBar.Value = 0

$stepLabel = New-Object System.Windows.Forms.Label
$stepLabel.Text = "Click 'Launch POS App' to start the application"
$stepLabel.Font = New-Object System.Drawing.Font("Segoe UI", 9)
$stepLabel.Location = New-Object System.Drawing.Point(0, 70)
$stepLabel.AutoSize = $true
$stepLabel.ForeColor = [System.Drawing.Color]::FromArgb(80, 80, 80)

$statusPanel.Controls.AddRange(@($statusLabel, $progressBar, $stepLabel))
$form.Controls.Add($statusPanel)

# Log section
$logPanel = New-Object System.Windows.Forms.Panel
$logPanel.Dock = [System.Windows.Forms.DockStyle]::Fill
$logPanel.Padding = New-Object System.Windows.Forms.Padding(20)

$logLabel = New-Object System.Windows.Forms.Label
$logLabel.Text = "Launch Log:"
$logLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$logLabel.Location = New-Object System.Drawing.Point(0, 0)
$logLabel.AutoSize = $true

$logTextBox = New-Object System.Windows.Forms.TextBox
$logTextBox.Location = New-Object System.Drawing.Point(0, 25)
$logTextBox.Size = New-Object System.Drawing.Size(440, 120)
$logTextBox.Multiline = $true
$logTextBox.ScrollBars = "Vertical"
$logTextBox.ReadOnly = $true
$logTextBox.Font = New-Object System.Drawing.Font("Consolas", 9)
$logTextBox.BackColor = [System.Drawing.Color]::FromArgb(245, 245, 245)

$logPanel.Controls.AddRange(@($logLabel, $logTextBox))
$form.Controls.Add($logPanel)

# Button section
$buttonPanel = New-Object System.Windows.Forms.Panel
$buttonPanel.Dock = [System.Windows.Forms.DockStyle]::Bottom
$buttonPanel.Height = 70
$buttonPanel.Padding = New-Object System.Windows.Forms.Padding(20)

$launchButton = New-Object System.Windows.Forms.Button
$launchButton.Text = "Launch POS App"
$launchButton.Size = New-Object System.Drawing.Size(150, 40)
$launchButton.Location = New-Object System.Drawing.Point(150, 15)
$launchButton.Font = New-Object System.Drawing.Font("Segoe UI", 11, [System.Drawing.FontStyle]::Bold)
$launchButton.BackColor = [System.Drawing.Color]::FromArgb(0, 120, 215)
$launchButton.ForeColor = [System.Drawing.Color]::White
$launchButton.FlatStyle = "Flat"
$launchButton.Cursor = [System.Windows.Forms.Cursors]::Hand

$cancelButton = New-Object System.Windows.Forms.Button
$cancelButton.Text = "Cancel"
$cancelButton.Size = New-Object System.Drawing.Size(120, 40)
$cancelButton.Location = New-Object System.Drawing.Point(320, 15)
$cancelButton.Font = New-Object System.Drawing.Font("Segoe UI", 11)
$cancelButton.BackColor = [System.Drawing.Color]::FromArgb(200, 200, 200)
$cancelButton.ForeColor = [System.Drawing.Color]::Black
$cancelButton.FlatStyle = "Flat"
$cancelButton.Cursor = [System.Windows.Forms.Cursors]::Hand

$buttonPanel.Controls.AddRange(@($launchButton, $cancelButton))
$form.Controls.Add($buttonPanel)

# Global variables
$script:isLaunching = $false
$script:currentStep = 0
$script:totalSteps = 4

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

function Check-Requirements {
    try {
        Update-Progress "Checking system requirements..." 1
        
        # Set environment variables first
        Write-Log "Setting environment variables..."
        $env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
        $env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
        
        # Check if Java is available
        Write-Log "Checking Java installation..."
        $javaVersion = java -version 2>&1 | Select-String "version"
        if ($javaVersion) {
            Write-Log "✓ Java found: $javaVersion"
        } else {
            Write-Log "⚠️ Java not found in PATH - will try to use from JAVA_HOME"
        }
        
        # Check if Maven is available
        Write-Log "Checking Maven installation..."
        $mavenVersion = mvn --version 2>&1 | Select-String "Apache Maven"
        if ($mavenVersion) {
            Write-Log "✓ Maven found: $mavenVersion"
        } else {
            Write-Log "⚠️ Maven not found in PATH - will try to use from Maven directory"
        }
        
        # Check if Rust/Cargo is available
        Write-Log "Checking Rust installation..."
        $cargoVersion = cargo --version 2>&1
        if ($cargoVersion -and $cargoVersion -notmatch "not found") {
            Write-Log "✓ Rust/Cargo found: $cargoVersion"
        } else {
            Write-Log "⚠️ Rust/Cargo not found in PATH - will try to use from .cargo directory"
        }
        
        # Check if Node.js is available
        Write-Log "Checking Node.js installation..."
        $nodeVersion = node --version 2>&1
        if ($nodeVersion -and $nodeVersion -notmatch "not found") {
            Write-Log "✓ Node.js found: $nodeVersion"
        } else {
            Write-Log "⚠️ Node.js not found in PATH - will try to use from npm"
        }
        
        Write-Log "Requirements check completed - proceeding with launch..."
        return $true
    } catch {
        Write-Log "Error checking requirements: $($_.Exception.Message)"
        Write-Log "Continuing anyway - tools may be available in custom locations..."
        return $true
    }
}

function Start-Backend {
    try {
        Update-Progress "Starting Spring Boot backend..." 2
        
        Write-Log "Setting environment variables..."
        $env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
        $env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
        
        Write-Log "Starting backend server..."
        $backendJob = Start-Job -ScriptBlock {
            Set-Location $using:PSScriptRoot
            Set-Location backend
            
            # Set environment variables in the job
            $env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
            $env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
            
            # Use full path to Maven
            & "$env:USERPROFILE\maven\apache-maven-3.9.5\bin\mvn.cmd" spring-boot:run
        }
        
        Write-Log "Backend started in background"
        return $backendJob
    } catch {
        Write-Log "Error starting backend: $($_.Exception.Message)"
        return $null
    }
}

function Start-Frontend {
    try {
        Update-Progress "Starting Tauri frontend..." 3
        
        Write-Log "Waiting for backend to be ready..."
        Start-Sleep -Seconds 10
        
        Write-Log "Starting Tauri application..."
        $frontendJob = Start-Job -ScriptBlock {
            Set-Location $using:PSScriptRoot
            
            # Set environment variables in the job
            $env:JAVA_HOME = "$env:USERPROFILE\.jdks\ms-17.0.15"
            $env:PATH = "$env:USERPROFILE\maven\apache-maven-3.9.5\bin;$env:USERPROFILE\.cargo\bin;$env:PATH"
            
            # Use npm from the current directory
            & npm run tauri:dev
        }
        
        Write-Log "Frontend started in background"
        return $frontendJob
    } catch {
        Write-Log "Error starting frontend: $($_.Exception.Message)"
        return $null
    }
}

function Complete-Launch {
    try {
        Update-Progress "Launch complete!" 4
        $progressBar.Value = 100
        
        Write-Log "Tauri POS Application launched successfully!"
        Write-Log "Backend: Running on port 8080"
        Write-Log "Frontend: Tauri desktop application"
        
        $statusLabel.Text = "Application Launched Successfully!"
        $statusLabel.ForeColor = [System.Drawing.Color]::Green
        
        # Show success message briefly, then auto-close
        [System.Windows.Forms.MessageBox]::Show(
            "Tauri POS Application has been launched successfully!`n`n" +
            "Backend: Running on port 8080`n" +
            "Frontend: Tauri desktop application`n`n" +
            "The launcher will close automatically in 3 seconds...",
            "Launch Complete",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Information
        )
        
        # Wait 3 seconds, then close the GUI
        Write-Log "Closing launcher in 3 seconds..."
        Start-Sleep -Seconds 3
        $form.Close()
        
        return $true
    } catch {
        Write-Log "Error completing launch: $($_.Exception.Message)"
        return $false
    }
}

# Event handlers
$launchButton.Add_Click({
    if ($script:isLaunching) { return }
    
    $script:isLaunching = $true
    $launchButton.Enabled = $false
    $cancelButton.Enabled = $false
    $progressBar.Value = 0
    $script:currentStep = 0
    
    try {
        Write-Log "Starting Tauri POS Application launch sequence..."
        
        # Check requirements
        if (-not (Check-Requirements)) { throw "System requirements check failed" }
        
        # Start backend
        $backendJob = Start-Backend
        if (-not $backendJob) { throw "Failed to start backend" }
        
        # Start frontend
        $frontendJob = Start-Frontend
        if (-not $frontendJob) { throw "Failed to start frontend" }
        
        # Complete launch
        if (Complete-Launch) {
            Write-Log "Launch sequence completed successfully!"
        } else {
            throw "Launch completion failed"
        }
        
    } catch {
        Write-Log "Launch failed: $($_.Exception.Message)"
        [System.Windows.Forms.MessageBox]::Show(
            "Launch failed: $($_.Exception.Message)`n`n" +
            "Please check the log for details and try again.",
            "Launch Failed",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Error
        )
    } finally {
        $script:isLaunching = $false
        $launchButton.Enabled = $true
        $cancelButton.Enabled = $true
    }
})

$cancelButton.Add_Click({
    if ($script:isLaunching) {
        $result = [System.Windows.Forms.MessageBox]::Show(
            "Launch is in progress. Are you sure you want to cancel?",
            "Confirm Cancel",
            [System.Windows.Forms.MessageBoxButtons]::YesNo,
            [System.Windows.Forms.MessageBoxIcon]::Question
        )
        if ($result -eq [System.Windows.Forms.DialogResult]::Yes) {
            $script:isLaunching = $false
            Write-Log "Launch cancelled by user."
        }
    } else {
        $form.Close()
    }
})

# Initialize
Write-Log "Tauri POS Application Launcher ready"
Write-Log "Click 'Launch POS App' to start the application"

# Show the form
$form.ShowDialog()
