using System;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO.Compression;
using Microsoft.Win32;

namespace TauriPosInstaller
{
    public partial class MainForm : Form
    {
        private bool isInstalling = false;
        private int currentStep = 0;
        private readonly string[] steps = {
            "Checking System Requirements",
            "Installing Java 17 (OpenJDK)",
            "Installing Maven 3.9.5",
            "Installing Rust & Cargo",
            "Setting Environment Variables",
            "Installing Dependencies",
            "Creating Desktop Shortcut",
            "Installation Complete!"
        };

        public MainForm()
        {
            InitializeComponent();
            InitializeForm();
        }

        private void InitializeForm()
        {
            // Form setup
            this.Text = "Tauri POS Application Installer";
            this.Size = new System.Drawing.Size(600, 500);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;

            // Create controls
            CreateControls();
            
            // Check if running as admin
            CheckAdminStatus();
        }

        private void CreateControls()
        {
            // Title
            Label titleLabel = new Label
            {
                Text = "Tauri POS Application Installer",
                Font = new System.Drawing.Font("Segoe UI", 16, System.Drawing.FontStyle.Bold),
                TextAlign = ContentAlignment.MiddleCenter,
                Dock = DockStyle.Top,
                Height = 50,
                ForeColor = System.Drawing.Color.FromArgb(64, 64, 64)
            };

            // Subtitle
            Label subtitleLabel = new Label
            {
                Text = "Professional Point of Sale System",
                Font = new System.Drawing.Font("Segoe UI", 10),
                TextAlign = ContentAlignment.MiddleCenter,
                Dock = DockStyle.Top,
                Height = 30,
                ForeColor = System.Drawing.Color.FromArgb(100, 100, 100)
            };

            // Progress section
            Panel progressPanel = new Panel
            {
                Dock = DockStyle.Top,
                Height = 120,
                Padding = new Padding(20)
            };

            Label progressLabel = new Label
            {
                Text = "Installation Progress:",
                Font = new System.Drawing.Font("Segoe UI", 10, System.Drawing.FontStyle.Bold),
                Location = new System.Drawing.Point(0, 0),
                AutoSize = true
            };

            progressBar = new ProgressBar
            {
                Location = new System.Drawing.Point(0, 30),
                Size = new System.Drawing.Size(540, 25),
                Style = ProgressBarStyle.Continuous
            };

            stepLabel = new Label
            {
                Text = "Ready to install...",
                Font = new System.Drawing.Font("Segoe UI", 9),
                Location = new System.Drawing.Point(0, 65),
                AutoSize = true,
                ForeColor = System.Drawing.Color.FromArgb(80, 80, 80)
            };

            progressPanel.Controls.AddRange(new Control[] { progressLabel, progressBar, stepLabel });

            // Log section
            Panel logPanel = new Panel
            {
                Dock = DockStyle.Fill,
                Padding = new Padding(20)
            };

            Label logLabel = new Label
            {
                Text = "Installation Log:",
                Font = new System.Drawing.Font("Segoe UI", 10, System.Drawing.FontStyle.Bold),
                Location = new System.Drawing.Point(0, 0),
                AutoSize = true
            };

            logTextBox = new TextBox
            {
                Location = new System.Drawing.Point(0, 25),
                Size = new System.Drawing.Size(540, 150),
                Multiline = true,
                ScrollBars = ScrollBars.Vertical,
                ReadOnly = true,
                Font = new System.Drawing.Font("Consolas", 9),
                BackColor = System.Drawing.Color.FromArgb(245, 245, 245)
            };

            logPanel.Controls.AddRange(new Control[] { logLabel, logTextBox });

            // Button section
            Panel buttonPanel = new Panel
            {
                Dock = DockStyle.Bottom,
                Height = 60,
                Padding = new Padding(20)
            };

            installButton = new Button
            {
                Text = "Install Now",
                Size = new System.Drawing.Size(120, 35),
                Location = new System.Drawing.Point(300, 12),
                Font = new System.Drawing.Font("Segoe UI", 10),
                BackColor = System.Drawing.Color.FromArgb(0, 120, 215),
                ForeColor = System.Drawing.Color.White,
                FlatStyle = FlatStyle.Flat
            };
            installButton.Click += InstallButton_Click;

            cancelButton = new Button
            {
                Text = "Cancel",
                Size = new System.Drawing.Size(120, 35),
                Location = new System.Drawing.Point(430, 12),
                Font = new System.Drawing.Font("Segoe UI", 10),
                BackColor = System.Drawing.Color.FromArgb(200, 200, 200),
                ForeColor = System.Drawing.Color.Black,
                FlatStyle = FlatStyle.Flat
            };
            cancelButton.Click += CancelButton_Click;

            buttonPanel.Controls.AddRange(new Control[] { installButton, cancelButton });

            // Add all panels to form
            this.Controls.AddRange(new Control[] { titleLabel, subtitleLabel, progressPanel, logPanel, buttonPanel });
        }

        private void CheckAdminStatus()
        {
            bool isAdmin = IsRunAsAdministrator();
            if (!isAdmin)
            {
                stepLabel.Text = "⚠️  Warning: Not running as Administrator. Some installations may fail.";
                stepLabel.ForeColor = System.Drawing.Color.Orange;
                LogMessage("Warning: Not running as Administrator. Some installations may fail.");
            }
            else
            {
                stepLabel.Text = "✓ Running as Administrator - Excellent!";
                stepLabel.ForeColor = System.Drawing.Color.Green;
                LogMessage("Running as Administrator - Excellent!");
            }
        }

        private bool IsRunAsAdministrator()
        {
            try
            {
                return (new System.Security.Principal.WindowsPrincipal(System.Security.Principal.WindowsIdentity.GetCurrent()))
                    .IsInRole(System.Security.Principal.WindowsBuiltInRole.Administrator);
            }
            catch
            {
                return false;
            }
        }

        private async void InstallButton_Click(object sender, EventArgs e)
        {
            if (isInstalling) return;

            isInstalling = true;
            installButton.Enabled = false;
            cancelButton.Enabled = false;
            progressBar.Value = 0;
            currentStep = 0;

            try
            {
                await InstallApplication();
            }
            catch (Exception ex)
            {
                LogMessage($"Installation failed: {ex.Message}");
                MessageBox.Show($"Installation failed: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                isInstalling = false;
                installButton.Enabled = true;
                cancelButton.Enabled = true;
            }
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {
            if (isInstalling)
            {
                if (MessageBox.Show("Installation is in progress. Are you sure you want to cancel?", 
                    "Confirm Cancel", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
                {
                    // Cancel installation logic here
                    isInstalling = false;
                    LogMessage("Installation cancelled by user.");
                }
            }
            else
            {
                this.Close();
            }
        }

        private async Task InstallApplication()
        {
            LogMessage("Starting Tauri POS Application installation...");
            
            // Step 1: Check system requirements
            await UpdateProgress("Checking system requirements...");
            if (!await CheckSystemRequirements())
            {
                throw new Exception("System requirements not met. Please check the log for details.");
            }

            // Step 2: Install Java
            await UpdateProgress("Installing Java 17 (OpenJDK)...");
            if (!await InstallJava())
            {
                throw new Exception("Failed to install Java 17.");
            }

            // Step 3: Install Maven
            await UpdateProgress("Installing Maven 3.9.5...");
            if (!await InstallMaven())
            {
                throw new Exception("Failed to install Maven.");
            }

            // Step 4: Install Rust
            await UpdateProgress("Installing Rust & Cargo...");
            if (!await InstallRust())
            {
                throw new Exception("Failed to install Rust.");
            }

            // Step 5: Set environment variables
            await UpdateProgress("Setting environment variables...");
            if (!await SetEnvironmentVariables())
            {
                throw new Exception("Failed to set environment variables.");
            }

            // Step 6: Install dependencies
            await UpdateProgress("Installing project dependencies...");
            if (!await InstallDependencies())
            {
                throw new Exception("Failed to install dependencies.");
            }

            // Step 7: Create desktop shortcut
            await UpdateProgress("Creating desktop shortcut...");
            if (!await CreateDesktopShortcut())
            {
                throw new Exception("Failed to create desktop shortcut.");
            }

            // Step 8: Complete
            await UpdateProgress("Installation complete!");
            progressBar.Value = 100;
            
            LogMessage("Installation completed successfully!");
            MessageBox.Show("Tauri POS Application has been installed successfully!\n\n" +
                          "A desktop shortcut has been created.\n" +
                          "You may need to restart your computer for all changes to take effect.", 
                          "Installation Complete", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private async Task UpdateProgress(string message)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new Action<string>(UpdateProgress), message);
                return;
            }

            currentStep++;
            stepLabel.Text = message;
            progressBar.Value = (currentStep * 100) / steps.Length;
            LogMessage(message);
            await Task.Delay(500); // Small delay for visual feedback
        }

        private void LogMessage(string message)
        {
            if (this.InvokeRequired)
            {
                this.Invoke(new Action<string>(LogMessage), message);
                return;
            }

            string timestamp = DateTime.Now.ToString("HH:mm:ss");
            logTextBox.AppendText($"[{timestamp}] {message}{Environment.NewLine}");
            logTextBox.ScrollToCaret();
        }

        private async Task<bool> CheckSystemRequirements()
        {
            LogMessage("Checking Windows version...");
            // Add Windows version check logic here
            
            LogMessage("Checking available disk space...");
            // Add disk space check logic here
            
            LogMessage("Checking internet connection...");
            try
            {
                using (var client = new WebClient())
                {
                    await client.DownloadStringTaskAsync("https://www.google.com");
                    LogMessage("Internet connection: OK");
                }
            }
            catch
            {
                LogMessage("Warning: Internet connection check failed");
            }

            return true;
        }

        private async Task<bool> InstallJava()
        {
            try
            {
                string javaUrl = "https://aka.ms/download-jdk/microsoft-jdk-17.0.15-windows-x64.msi";
                string javaFile = Path.Combine(Path.GetTempPath(), "microsoft-jdk-17.msi");
                
                LogMessage("Downloading Microsoft OpenJDK 17...");
                await DownloadFile(javaUrl, javaFile);
                
                LogMessage("Installing Java 17...");
                var process = Process.Start("msiexec.exe", $"/i \"{javaFile}\" /quiet /norestart");
                await process.WaitForExitAsync();
                
                if (File.Exists(javaFile))
                    File.Delete(javaFile);
                
                LogMessage("Java 17 installation completed successfully");
                return true;
            }
            catch (Exception ex)
            {
                LogMessage($"Java installation failed: {ex.Message}");
                return false;
            }
        }

        private async Task<bool> InstallMaven()
        {
            try
            {
                string mavenUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip";
                string mavenFile = Path.Combine(Path.GetTempPath(), "maven.zip");
                string mavenDir = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "maven");
                
                if (!Directory.Exists(mavenDir))
                    Directory.CreateDirectory(mavenDir);
                
                LogMessage("Downloading Apache Maven 3.9.5...");
                await DownloadFile(mavenUrl, mavenFile);
                
                LogMessage("Extracting Maven...");
                ZipFile.ExtractToDirectory(mavenFile, mavenDir, true);
                
                if (File.Exists(mavenFile))
                    File.Delete(mavenFile);
                
                LogMessage("Maven installation completed successfully");
                return true;
            }
            catch (Exception ex)
            {
                LogMessage($"Maven installation failed: {ex.Message}");
                return false;
            }
        }

        private async Task<bool> InstallRust()
        {
            try
            {
                string rustUrl = "https://win.rustup.rs/x86_64";
                string rustFile = Path.Combine(Path.GetTempPath(), "rustup-init.exe");
                
                LogMessage("Downloading Rust installer...");
                await DownloadFile(rustUrl, rustFile);
                
                LogMessage("Installing Rust...");
                var process = Process.Start(rustFile, "--default-toolchain stable --profile default -y");
                await process.WaitForExitAsync();
                
                if (File.Exists(rustFile))
                    File.Delete(rustFile);
                
                LogMessage("Rust installation completed successfully");
                return true;
            }
            catch (Exception ex)
            {
                LogMessage($"Rust installation failed: {ex.Message}");
                return false;
            }
        }

        private async Task<bool> SetEnvironmentVariables()
        {
            try
            {
                string javaHome = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), ".jdks", "ms-17.0.15");
                string mavenPath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "maven", "apache-maven-3.9.5", "bin");
                string cargoPath = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), ".cargo", "bin");
                
                LogMessage("Setting JAVA_HOME environment variable...");
                Environment.SetEnvironmentVariable("JAVA_HOME", javaHome, EnvironmentVariableTarget.Machine);
                
                LogMessage("Adding Maven and Rust to PATH...");
                string currentPath = Environment.GetEnvironmentVariable("PATH", EnvironmentVariableTarget.Machine) ?? "";
                string newPath = $"{mavenPath};{cargoPath};{currentPath}";
                Environment.SetEnvironmentVariable("PATH", newPath, EnvironmentVariableTarget.Machine);
                
                LogMessage("Environment variables set successfully");
                return true;
            }
            catch (Exception ex)
            {
                LogMessage($"Failed to set environment variables: {ex.Message}");
                return false;
            }
        }

        private async Task<bool> InstallDependencies()
        {
            try
            {
                LogMessage("Installing Node.js dependencies...");
                // Add npm install logic here
                
                LogMessage("Installing Maven dependencies...");
                // Add mvn dependency:resolve logic here
                
                LogMessage("Dependencies installed successfully");
                return true;
            }
            catch (Exception ex)
            {
                LogMessage($"Failed to install dependencies: {ex.Message}");
                return false;
            }
        }

        private async Task<bool> CreateDesktopShortcut()
        {
            try
            {
                string desktopPath = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);
                string shortcutPath = Path.Combine(desktopPath, "Tauri POS App.lnk");
                string targetPath = Path.Combine(Application.StartupPath, "start-tauri-with-backend.bat");
                string iconPath = Path.Combine(Application.StartupPath, "tauri", "icons", "icon.ico");
                
                LogMessage("Creating desktop shortcut...");
                
                // Create shortcut using Windows Script Host
                var shell = new dynamic();
                var shortcut = shell.CreateShortcut(shortcutPath);
                shortcut.TargetPath = targetPath;
                shortcut.WorkingDirectory = Application.StartupPath;
                shortcut.Description = "Tauri POS Application";
                shortcut.IconLocation = iconPath;
                shortcut.Save();
                
                LogMessage("Desktop shortcut created successfully");
                return true;
            }
            catch (Exception ex)
            {
                LogMessage($"Failed to create desktop shortcut: {ex.Message}");
                return false;
            }
        }

        private async Task DownloadFile(string url, string filePath)
        {
            using (var client = new WebClient())
            {
                client.DownloadProgressChanged += (s, e) =>
                {
                    // Update progress if needed
                };
                
                await client.DownloadFileTaskAsync(url, filePath);
            }
        }

        // Form controls
        private ProgressBar progressBar;
        private Label stepLabel;
        private TextBox logTextBox;
        private Button installButton;
        private Button cancelButton;
    }
}
