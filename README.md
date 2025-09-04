# 🛒 Tauri POS Application

A modern Point of Sale (POS) desktop application built with **Tauri**, **React TypeScript**, and **Spring Boot Java backend**. This application packages everything into a single native executable that can start both the frontend and backend services.

## 🚀 Features

- **Native Desktop Application**: Built with Tauri for cross-platform compatibility
- **Modern React Frontend**: TypeScript-based UI with modern design
- **Spring Boot Backend**: Java backend with JPA, H2 database, and RESTful APIs
- **Integrated Experience**: Single click to start the entire application
- **POS Functionality**: Product management, order processing, and reporting
- **Responsive Design**: Works on desktop and tablet devices

## 🏗️ Architecture

```
tauri-pos-app/
├── frontend/          # React TypeScript frontend
├── backend/           # Spring Boot Java backend
├── tauri/            # Tauri Rust configuration
└── package.json      # Root project configuration
```

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

### Required Software
- **Node.js** (v18 or higher)
- **Java JDK** (v17 or higher)
- **Maven** (v3.6 or higher)
- **Rust** (latest stable version)
- **Cargo** (comes with Rust)

### Installation Commands

#### Windows
```bash
# Install Node.js from https://nodejs.org/
# Install Java from https://adoptium.net/
# Install Maven from https://maven.apache.org/
# Install Rust from https://rustup.rs/
```

#### macOS
```bash
# Install Homebrew first
brew install node
brew install openjdk@17
brew install maven
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install nodejs npm
sudo apt install openjdk-17-jdk
sudo apt install maven
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
```

## 🚀 Quick Start

### 1. Clone and Setup
```bash
git clone <your-repo-url>
cd tauri-pos-app
npm run install:all
```

### 2. Development Mode
```bash
# Start all services (frontend, backend, and Tauri)
npm run dev

# Or start individually:
npm run frontend:dev    # Start React dev server
npm run backend:dev     # Start Spring Boot backend
npm run tauri:dev       # Start Tauri development
```

### 3. Build for Production
```bash
# Build all components
npm run build

# Build individually:
npm run frontend:build  # Build React app
npm run backend:build   # Build Spring Boot JAR
npm run tauri:build     # Build Tauri executable
```

## 🔧 Project Structure

### Frontend (React + TypeScript)
- **Location**: `frontend/`
- **Framework**: React 18 with TypeScript
- **Build Tool**: Vite
- **Features**: Modern UI components, responsive design, Tauri integration

### Backend (Spring Boot)
- **Location**: `backend/`
- **Framework**: Spring Boot 3.2
- **Database**: H2 (in-memory for development)
- **Features**: RESTful APIs, JPA, Security, CORS enabled

### Tauri (Rust)
- **Location**: `tauri/`
- **Language**: Rust
- **Features**: Native desktop app, process management, backend integration

## 🌐 API Endpoints

The Spring Boot backend provides the following REST endpoints:

- `GET /api/pos/health` - Health check
- `GET /api/pos/products` - Get all products
- `POST /api/pos/products` - Create new product
- `GET /api/pos/orders` - Get all orders
- `POST /api/pos/orders` - Create new order

### Database Console
- **H2 Console**: `http://localhost:8080/api/h2-console`
- **Credentials**: `sa` / `password`

## 🎯 Usage

### Starting the Application
1. **Double-click** the Tauri executable (`.exe` on Windows, `.app` on macOS)
2. The application will automatically:
   - Start the Spring Boot backend on port 8080
   - Launch the React frontend
   - Present a unified desktop interface

### Using the POS System
1. **Dashboard**: View sales statistics and system overview
2. **POS**: Process sales transactions with product selection
3. **Products**: Manage inventory and product catalog
4. **Orders**: View and manage customer orders
5. **Reports**: Generate business analytics and reports

## 🛠️ Development

### Adding New Features
1. **Frontend**: Add React components in `frontend/src/`
2. **Backend**: Add Java classes in `backend/src/main/java/`
3. **Tauri**: Modify Rust code in `tauri/src/`

### Database Changes
- Modify JPA entities in `backend/src/main/java/com/tauri/pos/model/`
- Update repositories in `backend/src/main/java/com/tauri/pos/repository/`
- Database schema auto-generates on startup

### Styling
- CSS styles are in `frontend/src/App.css`
- Uses modern CSS Grid and Flexbox
- Responsive design with mobile-first approach

## 📦 Building and Distribution

### Development Build
```bash
npm run tauri:dev
```

### Production Build
```bash
npm run build
```

### Distribution
The built application will be in:
- **Windows**: `tauri/target/release/bundle/msi/`
- **macOS**: `tauri/target/release/bundle/dmg/`
- **Linux**: `tauri/target/release/bundle/appimage/`

## 🔒 Security Features

- **CORS Configuration**: Configured for local development
- **Input Validation**: JPA validation annotations
- **SQL Injection Protection**: JPA parameterized queries
- **Cross-Site Scripting Protection**: Spring Security defaults

## 🐛 Troubleshooting

### Common Issues

#### Backend Won't Start
- Check Java version: `java -version`
- Ensure port 8080 is available
- Check Maven installation: `mvn -version`

#### Frontend Build Fails
- Clear node_modules: `rm -rf node_modules && npm install`
- Check Node.js version: `node -v`

#### Tauri Build Fails
- Update Rust: `rustup update`
- Check Cargo: `cargo --version`
- Install system dependencies (see Tauri docs)

#### Database Connection Issues
- H2 database is in-memory by default
- Check application.properties for configuration
- Verify H2 console access at `/h2-console`

### Logs
- **Backend**: Check console output for Spring Boot logs
- **Frontend**: Check browser console for React errors
- **Tauri**: Check system logs or console output

## 📚 Additional Resources

- [Tauri Documentation](https://tauri.app/docs/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev/)
- [TypeScript Documentation](https://www.typescriptlang.org/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

If you encounter any issues:
1. Check the troubleshooting section above
2. Search existing GitHub issues
3. Create a new issue with detailed information
4. Include your operating system and versions

---

**Happy coding! 🎉**
