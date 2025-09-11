package com.tauri.pos.sqlite.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/database")
@CrossOrigin(origins = "*")
@ConditionalOnProperty(name = "spring.datasource.sqlite.jdbc-url")
public class DatabaseInitController {

    private final String dbPath = "data/pos_local.db";

    /**
     * Initialize the database with new product tables
     */
    @PostMapping("/init")
    public ResponseEntity<Map<String, Object>> initializeDatabase() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Check if database file exists
            File dbFile = new File(dbPath);
            boolean dbExists = dbFile.exists();
            
            // Create backup if database exists
            if (dbExists) {
                String backupPath = dbPath + ".backup." + 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
                Files.copy(dbFile.toPath(), Paths.get(backupPath));
                response.put("backupCreated", backupPath);
            }
            
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Initialize database
            initializeDatabaseWithSQL();
            
            response.put("success", true);
            response.put("message", "Database initialized successfully!");
            response.put("databasePath", dbPath);
            response.put("backupCreated", dbExists);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to initialize database: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    private void initializeDatabaseWithSQL() throws SQLException {
        String sqlScript = getSQLScript();
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement()) {
            
            // Split SQL script by semicolon and execute each statement
            String[] statements = sqlScript.split(";");
            for (String statement : statements) {
                statement = statement.trim();
                if (!statement.isEmpty() && !statement.startsWith("--")) {
                    stmt.execute(statement);
                }
            }
        }
    }
    
    private String getSQLScript() {
        return """
            -- Drop existing tables (except users) to ensure clean state
            DROP TABLE IF EXISTS local_order_items;
            DROP TABLE IF EXISTS orders;
            DROP TABLE IF EXISTS order_items;
            DROP TABLE IF EXISTS products;
            DROP TABLE IF EXISTS product_barcode;
            DROP TABLE IF EXISTS product_description;
            DROP TABLE IF EXISTS local_product;
            DROP TABLE IF EXISTS local_product_barcode;
            DROP TABLE IF EXISTS local_product_description;

            -- Create the users table (preserve existing structure)
            CREATE TABLE users (
                userid INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                hashed_password TEXT NOT NULL,
                permission TEXT NOT NULL DEFAULT 'user',
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
            );

            -- Insert default user data
            INSERT INTO users (userid, username, hashed_password, permission, created_at, updated_at) VALUES
            (2, 'testuser', '\\\\\\.IjdQj8Kz8Kz8Kz8Kz8Kz8Kz8Kz8Kz8K', 'user', '2025-09-10 07:30:41', '2025-09-10 07:30:41'),
            (3, 'admin', '$2a$10$WMoSdoKBdONd0Nq39g8AJ.XQ28MjrdS09SbIZVdm0qarEM2QT3DjW', 'admin', NULL, NULL),
            (4, 'user1', '$2a$10$WMoSdoKBdONd0Nq39g8AJ.XQ28MjrdS09SbIZVdm0qarEM2QT3DjW', 'user', '2025-09-10 07:51:43', '2025-09-10 07:51:43');

            -- Create local SQLite tables (mirroring MySQL entities with local_ prefix)
            CREATE TABLE local_product (
                product_id INTEGER PRIMARY KEY AUTOINCREMENT,
                status INTEGER NOT NULL DEFAULT 1,
                brand_id INTEGER,
                ebay_id INTEGER,
                model_number VARCHAR(255),
                sku VARCHAR(255),
                date_available DATE,
                date_backorder DATE,
                date_preorder DATE,
                qty_preorder INTEGER DEFAULT 0,
                barcode VARCHAR(45),
                weight DECIMAL(10,3),
                weight_class_id INTEGER,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                created_by INTEGER NOT NULL,
                updated_by INTEGER NOT NULL
            );

            CREATE TABLE local_product_barcode (
                product_id INTEGER NOT NULL,
                barcode VARCHAR(45) NOT NULL,
                status INTEGER NOT NULL DEFAULT 1,
                created_at TIMESTAMP,
                updated_at TIMESTAMP,
                created_by INTEGER NOT NULL,
                updated_by INTEGER NOT NULL,
                PRIMARY KEY (product_id, barcode),
                FOREIGN KEY (product_id) REFERENCES local_product(product_id) ON DELETE CASCADE
            );

            CREATE TABLE local_product_description (
                product_id INTEGER NOT NULL,
                site_id INTEGER NOT NULL,
                language_id INTEGER NOT NULL,
                name VARCHAR(255),
                description TEXT,
                feature TEXT,
                specification TEXT,
                include TEXT,
                required TEXT,
                created_at TIMESTAMP,
                updated_at TIMESTAMP,
                created_by INTEGER NOT NULL,
                updated_by INTEGER NOT NULL,
                PRIMARY KEY (product_id, site_id, language_id),
                FOREIGN KEY (product_id) REFERENCES local_product(product_id) ON DELETE CASCADE
            );

            -- Create indexes for local tables
            CREATE INDEX idx_local_product_status ON local_product(status);
            CREATE INDEX idx_local_product_brand_id ON local_product(brand_id);
            CREATE INDEX idx_local_product_sku ON local_product(sku);
            CREATE INDEX idx_local_product_barcode ON local_product(barcode);
            CREATE INDEX idx_local_product_barcode_status ON local_product_barcode(status);
            CREATE INDEX idx_local_product_description_product_id ON local_product_description(product_id);
            CREATE INDEX idx_local_product_description_site_id ON local_product_description(site_id);
            CREATE INDEX idx_local_product_description_language_id ON local_product_description(language_id);
            """;
    }
}
