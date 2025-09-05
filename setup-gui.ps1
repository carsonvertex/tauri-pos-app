#!/usr/bin/env pwsh

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

# Create main form
$form = New-Object System.Windows.Forms.Form
$form.Text = "Tauri POS App Setup & Launcher"
$form.Size = New-Object System.Drawing.Size(700, 630)
$form.StartPosition = "CenterScreen"
$form.FormBorderStyle = "FixedSingle"
$form.MaximizeBox = $false
$form.BackColor = [System.Drawing.Color]::FromArgb(45, 45, 48)
$form.ForeColor = [System.Drawing.Color]::White

# Title
$titleLabel = New-Object System.Windows.Forms.Label
$titleLabel.Text = "Tauri POS App Setup & Launcher"
$titleLabel.Font = New-Object System.Drawing.Font("Segoe UI", 18, [System.Drawing.FontStyle]::Bold)
$titleLabel.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
$titleLabel.TextAlign = [System.Drawing.ContentAlignment]::MiddleCenter
$titleLabel.Size = New-Object System.Drawing.Size(680, 40)
$titleLabel.Location = New-Object System.Drawing.Point(10, 20)
$form.Controls.Add($titleLabel)

# Status group
$statusGroupBox = New-Object System.Windows.Forms.GroupBox
$statusGroupBox.Text = "System Status"
$statusGroupBox.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$statusGroupBox.Size = New-Object System.Drawing.Size(680, 230)
$statusGroupBox.Location = New-Object System.Drawing.Point(10, 100)
$statusGroupBox.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
$form.Controls.Add($statusGroupBox)

# Status labels and indicators
$javaStatusLabel = New-Object System.Windows.Forms.Label
$javaStatusLabel.Text = "Java 17+"
$javaStatusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$javaStatusLabel.Size = New-Object System.Drawing.Size(150, 25)
$javaStatusLabel.Location = New-Object System.Drawing.Point(20, 30)
$statusGroupBox.Controls.Add($javaStatusLabel)

$javaIndicator = New-Object System.Windows.Forms.Label
$javaIndicator.Size = New-Object System.Drawing.Size(20, 20)
$javaIndicator.Location = New-Object System.Drawing.Point(180, 30)
$statusGroupBox.Controls.Add($javaIndicator)

$mavenStatusLabel = New-Object System.Windows.Forms.Label
$mavenStatusLabel.Text = "Apache Maven"
$mavenStatusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$mavenStatusLabel.Size = New-Object System.Drawing.Size(150, 25)
$mavenStatusLabel.Location = New-Object System.Drawing.Point(20, 60)
$statusGroupBox.Controls.Add($mavenStatusLabel)

$mavenIndicator = New-Object System.Windows.Forms.Label
$mavenIndicator.Size = New-Object System.Drawing.Size(20, 20)
$mavenIndicator.Location = New-Object System.Drawing.Point(180, 60)
$statusGroupBox.Controls.Add($mavenIndicator)

$rustStatusLabel = New-Object System.Windows.Forms.Label
$rustStatusLabel.Text = "Rust"
$rustStatusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$rustStatusLabel.Size = New-Object System.Drawing.Size(150, 25)
$rustStatusLabel.Location = New-Object System.Drawing.Point(20, 90)
$statusGroupBox.Controls.Add($rustStatusLabel)

$rustIndicator = New-Object System.Windows.Forms.Label
$rustIndicator.Size = New-Object System.Drawing.Size(20, 20)
$rustIndicator.Location = New-Object System.Drawing.Point(180, 90)
$statusGroupBox.Controls.Add($rustIndicator)

$nodeStatusLabel = New-Object System.Windows.Forms.Label
$nodeStatusLabel.Text = "Node.js"
$nodeStatusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$nodeStatusLabel.Size = New-Object System.Drawing.Size(150, 25)
$nodeStatusLabel.Location = New-Object System.Drawing.Point(20, 120)
$statusGroupBox.Controls.Add($nodeStatusLabel)

$nodeIndicator = New-Object System.Windows.Forms.Label
$nodeIndicator.Size = New-Object System.Drawing.Size(20, 20)
$nodeIndicator.Location = New-Object System.Drawing.Point(180, 120)
$statusGroupBox.Controls.Add($nodeIndicator)

$depsStatusLabel = New-Object System.Windows.Forms.Label
$depsStatusLabel.Text = "Dependencies"
$depsStatusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$depsStatusLabel.Size = New-Object System.Drawing.Size(150, 25)
$depsStatusLabel.Location = New-Object System.Drawing.Point(20, 150)
$statusGroupBox.Controls.Add($depsStatusLabel)

$depsIndicator = New-Object System.Windows.Forms.Label
$depsIndicator.Size = New-Object System.Drawing.Size(20, 20)
$depsIndicator.Location = New-Object System.Drawing.Point(180, 150)
$statusGroupBox.Controls.Add($depsIndicator)

$sqliteStatusLabel = New-Object System.Windows.Forms.Label
$sqliteStatusLabel.Text = "SQLite CLI"
$sqliteStatusLabel.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$sqliteStatusLabel.Size = New-Object System.Drawing.Size(150, 25)
$sqliteStatusLabel.Location = New-Object System.Drawing.Point(20, 180)
$statusGroupBox.Controls.Add($sqliteStatusLabel)

$sqliteIndicator = New-Object System.Windows.Forms.Label
$sqliteIndicator.Size = New-Object System.Drawing.Size(20, 20)
$sqliteIndicator.Location = New-Object System.Drawing.Point(180, 180)
$statusGroupBox.Controls.Add($sqliteIndicator)

# Progress group
$progressGroupBox = New-Object System.Windows.Forms.GroupBox
$progressGroupBox.Text = "Setup Progress"
$progressGroupBox.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$progressGroupBox.Size = New-Object System.Drawing.Size(680, 120)
$progressGroupBox.Location = New-Object System.Drawing.Point(10, 350)
$progressGroupBox.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
$form.Controls.Add($progressGroupBox)

# Progress bar
$progressBar = New-Object System.Windows.Forms.ProgressBar
$progressBar.Size = New-Object System.Drawing.Size(640, 30)
$progressBar.Location = New-Object System.Drawing.Point(20, 30)
$progressBar.Style = "Continuous"
$progressGroupBox.Controls.Add($progressBar)

# Progress label
$progressLabel = New-Object System.Windows.Forms.Label
$progressLabel.Text = "Ready to start setup..."
$progressLabel.Font = New-Object System.Drawing.Font("Segoe UI", 9)
$progressLabel.Size = New-Object System.Drawing.Size(640, 25)
$progressLabel.Location = New-Object System.Drawing.Point(20, 70)
$progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(200, 200, 200)
$progressGroupBox.Controls.Add($progressLabel)

# Buttons group
$buttonsGroupBox = New-Object System.Windows.Forms.GroupBox
$buttonsGroupBox.Text = "Actions"
$buttonsGroupBox.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$buttonsGroupBox.Size = New-Object System.Drawing.Size(680, 80)
$buttonsGroupBox.Location = New-Object System.Drawing.Point(10, 490)
$buttonsGroupBox.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
$form.Controls.Add($buttonsGroupBox)

# Setup button
$setupButton = New-Object System.Windows.Forms.Button
$setupButton.Text = "Setup Environment"
$setupButton.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$setupButton.Size = New-Object System.Drawing.Size(200, 40)
$setupButton.Location = New-Object System.Drawing.Point(20, 25)
$setupButton.BackColor = [System.Drawing.Color]::FromArgb(0, 120, 215)
$setupButton.ForeColor = [System.Drawing.Color]::White
$setupButton.FlatStyle = "Flat"
$buttonsGroupBox.Controls.Add($setupButton)

# Launch button
$launchButton = New-Object System.Windows.Forms.Button
$launchButton.Text = "Launch App"
$launchButton.Font = New-Object System.Drawing.Font("Segoe UI", 10, [System.Drawing.FontStyle]::Bold)
$launchButton.Size = New-Object System.Drawing.Size(200, 40)
$launchButton.Location = New-Object System.Drawing.Point(240, 25)
$launchButton.BackColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
$launchButton.ForeColor = [System.Drawing.Color]::White
$launchButton.FlatStyle = "Flat"
$launchButton.Enabled = $false
$buttonsGroupBox.Controls.Add($launchButton)

# Exit button
$exitButton = New-Object System.Windows.Forms.Button
$exitButton.Text = "Exit"
$exitButton.Font = New-Object System.Drawing.Font("Segoe UI", 10)
$exitButton.Size = New-Object System.Drawing.Size(100, 40)
$exitButton.Location = New-Object System.Drawing.Point(560, 25)
$exitButton.BackColor = [System.Drawing.Color]::FromArgb(255, 69, 0)
$exitButton.ForeColor = [System.Drawing.Color]::White
$exitButton.FlatStyle = "Flat"
$buttonsGroupBox.Controls.Add($exitButton)

# Functions
function Test-Command($cmdname) {
    return [bool](Get-Command -Name $cmdname -ErrorAction SilentlyContinue)
}

function Update-StatusIndicator($indicator, $status) {
    if ($status) {
        $indicator.Text = "OK"
        $indicator.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
    } else {
        $indicator.Text = "X"
        $indicator.ForeColor = [System.Drawing.Color]::FromArgb(255, 69, 0)
    }
}

function Check-SystemStatus {
    $progressLabel.Text = "Checking system status..."
    $progressBar.Value = 10
    
    # Check Java using auto-detection
    $javaHome = Find-Java
    $javaOk = $null -ne $javaHome
    if ($javaOk) {
        $env:JAVA_HOME = $javaHome
        $env:PATH = "$javaHome\bin;$env:PATH"
    }
    Update-StatusIndicator $javaIndicator $javaOk
    $progressBar.Value = 20
    
    # Check Maven using auto-detection
    $mavenBin = Find-Maven
    $mavenOk = $null -ne $mavenBin
    if ($mavenOk) {
        $env:PATH = "$mavenBin;$env:PATH"
    }
    Update-StatusIndicator $mavenIndicator $mavenOk
    $progressBar.Value = 30
    
    # Check Rust - also add to PATH if found
    $rustOk = Test-Command "cargo"
    if (-not $rustOk) {
        # Try to find Rust in common locations
        $rustPaths = @(
            "$env:USERPROFILE\.cargo\bin",
            "$env:PROGRAMFILES\Rust stable MSVC 64-bit\bin",
            "$env:PROGRAMFILES(X86)\Rust stable MSVC 64-bit\bin"
        )
        
        foreach ($rustPath in $rustPaths) {
            if (Test-Path "$rustPath\cargo.exe") {
                $env:PATH = "$rustPath;$env:PATH"
                $rustOk = $true
                break
            }
        }
    }
    Update-StatusIndicator $rustIndicator $rustOk
    $progressBar.Value = 40
    
    # Check Node.js using auto-detection
    $nodeDir = Find-NodeJS
    $nodeOk = $null -ne $nodeDir
    if ($nodeOk) {
        $env:PATH = "$nodeDir;$env:PATH"
    }
    Update-StatusIndicator $nodeIndicator $nodeOk
    $progressBar.Value = 50
    
    # Check dependencies - try to install if missing
    $depsOk = (Test-Path "node_modules") -and (Test-Path "frontend/node_modules")
    if (-not $depsOk) {
        # Try to install dependencies if Node.js is available
        if ($nodeOk) {
            try {
                if (-not (Test-Path "node_modules")) {
                    Write-Host "Installing root dependencies..." -ForegroundColor Yellow
                    npm install --silent | Out-Null
                }
                if (-not (Test-Path "frontend/node_modules")) {
                    Write-Host "Installing frontend dependencies..." -ForegroundColor Yellow
                    Set-Location frontend
                    npm install --silent | Out-Null
                    Set-Location ..
                }
                $depsOk = (Test-Path "node_modules") -and (Test-Path "frontend/node_modules")
            } catch {
                # Installation failed, but that's okay - user can run setup
                $depsOk = $false
            }
        }
    }
    Update-StatusIndicator $depsIndicator $depsOk
    $progressBar.Value = 60
    
    # Check SQLite CLI
    $sqliteOk = Test-Command "sqlite3"
    if (-not $sqliteOk) {
        # Try to find SQLite in common locations
        $sqlitePaths = @(
            "$env:PROGRAMFILES\SQLite\bin",
            "$env:PROGRAMFILES(X86)\SQLite\bin",
            "$env:USERPROFILE\AppData\Local\Microsoft\WinGet\Packages\SQLite.SQLite_*\bin"
        )
        
        foreach ($sqlitePath in $sqlitePaths) {
            if (Test-Path "$sqlitePath\sqlite3.exe") {
                $env:PATH = "$sqlitePath;$env:PATH"
                $sqliteOk = $true
                break
            }
        }
    }
    Update-StatusIndicator $sqliteIndicator $sqliteOk
    $progressBar.Value = 70
    
    $progressBar.Value = 100
    $progressLabel.Text = "System status check completed"
    
    # Enable/disable buttons
    $allReady = $javaOk -and $mavenOk -and $rustOk -and $nodeOk -and $depsOk -and $sqliteOk
    $launchButton.Enabled = $allReady
    $setupButton.Enabled = -not $allReady
    
    if ($allReady) {
        $progressLabel.Text = "Everything is ready! You can launch the app now."
        $progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
    } else {
        $progressLabel.Text = "Some components need setup. Click 'Setup Environment' to continue."
        $progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(255, 165, 0)
    }
    
    return $allReady
}

# Auto-detection functions (integrated from detect-env.ps1)
function Find-NodeJS {
    # Check if node and npm are in PATH
    try {
        $nodeVersion = node --version 2>&1
        $npmVersion = npm --version 2>&1
        if ($nodeVersion -match "v\d+" -and $npmVersion -match "\d+") {
            $nodePath = (Get-Command node -ErrorAction SilentlyContinue).Source
            $nodeDir = Split-Path $nodePath -Parent
            return $nodeDir
        }
    } catch {
        # Node.js not in PATH, continue searching
    }
    
    # Search common locations
    $searchPaths = @(
        "$env:PROGRAMFILES\nodejs",
        "$env:PROGRAMFILES(X86)\nodejs",
        "$env:LOCALAPPDATA\Programs\nodejs",
        "$env:APPDATA\npm"
    )
    
    foreach ($basePath in $searchPaths) {
        if (Test-Path $basePath) {
            if ((Test-Path "$basePath\node.exe") -and (Test-Path "$basePath\npm.cmd")) {
                try {
                    $nodeVersion = & "$basePath\node.exe" --version 2>&1
                    $npmVersion = & "$basePath\npm.cmd" --version 2>&1
                    if ($nodeVersion -match "v\d+" -and $npmVersion -match "\d+") {
                        return $basePath
                    }
                } catch {
                    continue
                }
            }
        }
    }
    
    return $null
}

function Find-Java {
    # Check if JAVA_HOME is already set and working
    if ($env:JAVA_HOME -and (Test-Path "$env:JAVA_HOME\bin\java.exe")) {
        try {
            $version = & "$env:JAVA_HOME\bin\java.exe" -version 2>&1 | Select-String "version"
            if ($version -match "17|18|19|20|21|22|23|24") {
                return $env:JAVA_HOME
            }
        } catch {
            # JAVA_HOME is set but not working, continue searching
        }
    }
    
    # Check if java is in PATH
    try {
        $version = java -version 2>&1 | Select-String "version"
        if ($version -match "17|18|19|20|21|22|23|24") {
            $javaPath = (Get-Command java -ErrorAction SilentlyContinue).Source
            $javaHome = Split-Path (Split-Path $javaPath -Parent) -Parent
            return $javaHome
        }
    } catch {
        # Java not in PATH, continue searching
    }
    
    # Search common locations
    $searchPaths = @(
        "$env:USERPROFILE\.jdks",
        "$env:PROGRAMFILES\Java",
        "$env:PROGRAMFILES(X86)\Java",
        "$env:LOCALAPPDATA\Programs\Java"
    )
    
    foreach ($basePath in $searchPaths) {
        if (Test-Path $basePath) {
            $javaDirs = Get-ChildItem -Path $basePath -Directory -ErrorAction SilentlyContinue | Where-Object { 
                $_.Name -match "(jdk|jre|corretto|openjdk|ms-)" -and 
                (Test-Path "$($_.FullName)\bin\java.exe")
            }
            
            foreach ($javaDir in $javaDirs) {
                try {
                    $version = & "$($javaDir.FullName)\bin\java.exe" -version 2>&1 | Select-String "version"
                    if ($version -match "17|18|19|20|21|22|23|24") {
                        return $javaDir.FullName
                    }
                } catch {
                    continue
                }
            }
        }
    }
    
    return $null
}

function Find-Maven {
    # Check if mvn is in PATH
    try {
        $version = mvn -version 2>&1 | Select-String "Apache Maven"
        if ($version) {
            $mvnPath = (Get-Command mvn -ErrorAction SilentlyContinue).Source
            $mavenHome = Split-Path (Split-Path $mvnPath -Parent) -Parent
            return "$mavenHome\bin"
        }
    } catch {
        # Maven not in PATH, continue searching
    }
    
    # Search common locations
    $searchPaths = @(
        "$env:USERPROFILE\maven",
        "$env:PROGRAMFILES\Apache\maven",
        "$env:PROGRAMFILES(X86)\Apache\maven"
    )
    
    foreach ($basePath in $searchPaths) {
        if (Test-Path $basePath) {
            $mavenDirs = Get-ChildItem -Path $basePath -Directory -ErrorAction SilentlyContinue | Where-Object { 
                $_.Name -match "apache-maven" -and 
                (Test-Path "$($_.FullName)\bin\mvn.cmd")
            }
            
            foreach ($mavenDir in $mavenDirs) {
                try {
                    $version = & "$($mavenDir.FullName)\bin\mvn.cmd" -version 2>&1 | Select-String "Apache Maven"
                    if ($version) {
                        return "$($mavenDir.FullName)\bin"
                    }
                } catch {
                    continue
                }
            }
        }
    }
    
    return $null
}

function Setup-Environment {
    $setupButton.Enabled = $false
    $progressBar.Value = 0
    $progressLabel.Text = "Starting environment setup..."
    
    try {
        # Step 1: Find Java installation
        $progressBar.Value = 10
        $progressLabel.Text = "Detecting Java installation..."
        
        $javaHome = Find-Java
        if ($javaHome) {
            $env:JAVA_HOME = $javaHome
            $env:PATH = "$javaHome\bin;$env:PATH"
            Update-StatusIndicator $javaIndicator $true
        } else {
            throw "Java installation not found. Please install Java 17 or later."
        }
        
        # Step 2: Find Maven installation
        $progressBar.Value = 30
        $progressLabel.Text = "Detecting Maven installation..."
        
        $mavenBin = Find-Maven
        if ($mavenBin) {
            $env:PATH = "$mavenBin;$env:PATH"
            Update-StatusIndicator $mavenIndicator $true
        } else {
            throw "Maven installation not found. Please install Apache Maven."
        }
        
        # Step 3: Check Rust
        $progressBar.Value = 50
        $progressLabel.Text = "Checking Rust installation..."
        
        if (-not (Test-Command "cargo")) {
            throw "Rust not found. Please install Rust from https://rustup.rs/"
        }
        Update-StatusIndicator $rustIndicator $true
        
        # Step 4: Install Node.js dependencies
        $progressBar.Value = 70
        $progressLabel.Text = "Installing Node.js dependencies..."
        
        if (-not (Test-Command "node") -or -not (Test-Command "npm")) {
            throw "Node.js not found. Please install Node.js from https://nodejs.org/"
        }
        Update-StatusIndicator $nodeIndicator $true
        
        # Install dependencies
        npm install | Out-Null
        Set-Location frontend
        npm install | Out-Null
        Set-Location ..
        
        Update-StatusIndicator $depsIndicator $true
        
        # Step 5: Install SQLite CLI if not found
        $progressBar.Value = 80
        $progressLabel.Text = "Checking SQLite CLI installation..."
        
        if (-not (Test-Command "sqlite3")) {
            $progressLabel.Text = "Installing SQLite CLI..."
            
            # Try to install SQLite using winget
            try {
                winget install SQLite.SQLite --accept-package-agreements --accept-source-agreements --silent
                
                # Update PATH to include SQLite
                $env:PATH = [System.Environment]::GetEnvironmentVariable("PATH","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("PATH","User")
                
                # Test if SQLite is now available
                if (Test-Command "sqlite3") {
                    Update-StatusIndicator $sqliteIndicator $true
                } else {
                    throw "SQLite installation completed but command not found in PATH"
                }
            } catch {
                # If winget fails, show manual installation instructions
                $result = [System.Windows.Forms.MessageBox]::Show(
                    "SQLite CLI not found and automatic installation failed.`n`nWould you like to install it manually?`n`nClick Yes to open download page, or No to continue without SQLite CLI.",
                    "SQLite CLI Required",
                    [System.Windows.Forms.MessageBoxButtons]::YesNo,
                    [System.Windows.Forms.MessageBoxIcon]::Question
                )
                
                if ($result -eq [System.Windows.Forms.DialogResult]::Yes) {
                    Start-Process "https://www.sqlite.org/download.html"
                }
                
                Update-StatusIndicator $sqliteIndicator $false
            }
        } else {
            Update-StatusIndicator $sqliteIndicator $true
        }
        
        # Step 6: Test backend compilation
        $progressBar.Value = 90
        $progressLabel.Text = "Testing backend compilation..."
        
        Set-Location backend
        mvn compile -q
        if ($LASTEXITCODE -ne 0) {
            throw "Backend compilation failed"
        }
        Set-Location ..
        
        $progressBar.Value = 100
        $progressLabel.Text = "Environment setup completed successfully!"
        $progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
        
        # Enable launch button
        $launchButton.Enabled = $true
        $setupButton.Enabled = $false
        
        [System.Windows.Forms.MessageBox]::Show(
            "Environment setup completed successfully! You can now launch the app.",
            "Setup Complete",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Information
        )
        
    } catch {
        $progressLabel.Text = "Setup failed: $($_.Exception.Message)"
        $progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(255, 69, 0)
        
        [System.Windows.Forms.MessageBox]::Show(
            "Setup failed: $($_.Exception.Message)`n`nPlease ensure all prerequisites are installed and try again.",
            "Setup Failed",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Error
        )
        
        $setupButton.Enabled = $true
    }
}

function Launch-App {
    $launchButton.Enabled = $false
    $progressLabel.Text = "Launching Tauri POS App..."
    
    try {
        # Find npm executable path
        $npmPath = Get-Command "npm" -ErrorAction SilentlyContinue
        if ($npmPath) {
            $npmExe = $npmPath.Source
        } else {
            # Try to find npm in common locations
            $possiblePaths = @(
                "$env:APPDATA\npm",
                "$env:PROGRAMFILES\nodejs",
                "$env:PROGRAMFILES(X86)\nodejs"
            )
            
            foreach ($path in $possiblePaths) {
                if (Test-Path "$path\npm.cmd") {
                    $npmExe = "$path\npm.cmd"
                    break
                }
            }
            
            if (-not $npmExe) {
                throw "npm executable not found"
            }
        }
        
        # Start the application using cmd to run npm
        Start-Process -FilePath "cmd.exe" -ArgumentList "/c", "npm", "run", "tauri:with-backend" -WorkingDirectory $PWD -WindowStyle Normal
        
        $progressLabel.Text = "App launched successfully! Check your desktop for the Tauri window."
        $progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(0, 255, 127)
        
        # Close the setup GUI after a delay
        Start-Sleep -Seconds 3
        $form.Close()
        
    } catch {
        $progressLabel.Text = "Failed to launch app: $($_.Exception.Message)"
        $progressLabel.ForeColor = [System.Drawing.Color]::FromArgb(255, 69, 0)
        $launchButton.Enabled = $true
        
        [System.Windows.Forms.MessageBox]::Show(
            "Failed to launch the app: $($_.Exception.Message)`n`nYou can try launching manually with: npm run tauri:with-backend",
            "Launch Failed",
            [System.Windows.Forms.MessageBoxButtons]::OK,
            [System.Windows.Forms.MessageBoxIcon]::Warning
        )
    }
}

# Event handlers
$setupButton.Add_Click({ Setup-Environment })
$launchButton.Add_Click({ Launch-App })
$exitButton.Add_Click({ $form.Close() })

# Initial status check
Check-SystemStatus

# Show the form
$form.ShowDialog()
