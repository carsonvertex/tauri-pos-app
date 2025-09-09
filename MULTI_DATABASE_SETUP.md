# Multi-Database Configuration Setup

This guide explains how to set up and use both SQLite and MySQL databases simultaneously in your Tauri POS application.

## Overview

The application now supports two databases:
- **SQLite**: Local database for offline storage (existing functionality)
- **MySQL**: Remote database for adjustment_detail table and other remote data

## Configuration Files

### 1. Application Properties (`backend/src/main/resources/application.properties`)

The configuration has been updated to support both databases:

```properties
# SQLite Database Configuration (Primary - for local storage)
spring.datasource.sqlite.url=jdbc:sqlite:../data/pos_local.db
spring.datasource.sqlite.driverClassName=org.sqlite.JDBC
spring.datasource.sqlite.username=
spring.datasource.sqlite.password=

# MySQL Database Configuration (Secondary - for adjustment_detail)
spring.datasource.mysql.url=jdbc:mysql://localhost:3306/pos_mysql?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.mysql.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.mysql.username=root
spring.datasource.mysql.password=password
```

### 2. Database Configuration Classes

- `SqliteConfig.java`: Configures SQLite database connection
- `MysqlConfig.java`: Configures MySQL database connection

## Setup Instructions

### 1. MySQL Database Setup

1. **Install MySQL** (if not already installed)
2. **Create Database**:
   ```sql
   CREATE DATABASE pos_mysql;
   ```
3. **Update Connection Details** in `application.properties`:
   - Change `spring.datasource.mysql.url` to your MySQL server details
   - Update `spring.datasource.mysql.username` and `spring.datasource.mysql.password`

### 2. Dependencies

The MySQL driver has been added to `pom.xml`:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### 3. Build and Run

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

## Testing the Configuration

### Test Endpoint

Use the test endpoint to verify both databases are working:

```bash
GET http://localhost:8080/api/test/databases
```

This will return the status of both database connections.

### Create Test Data

```bash
POST http://localhost:8080/api/test/adjustment-detail
```

This creates a test adjustment detail record in MySQL.

## API Endpoints

### SQLite Endpoints (Existing)
- `/api/local-orderItem/*` - Local order item operations

### MySQL Endpoints (New)
- `/api/adjustment-detail/*` - Adjustment detail operations

## Database Structure

### SQLite Tables
- `local_order_items` - Local order items (existing)

### MySQL Tables
- `adjustment_detail` - Adjustment details with fields:
  - `adjustment_detail_id` (Primary Key)
  - `adjustment_id`
  - `product_id`
  - `warehouse_id`
  - `bin_id`
  - `before_adjustment_qty`
  - `adjustment_qty`
  - `after_adjustment_qty`
  - `created_at`
  - `updated_at`
  - `created_by`
  - `updated_by`

## Troubleshooting

### Common Issues

1. **MySQL Connection Failed**:
   - Check if MySQL server is running
   - Verify connection details in `application.properties`
   - Ensure database `pos_mysql` exists

2. **SQLite Connection Failed**:
   - Check if `../data/pos_local.db` file exists
   - Verify file permissions

3. **Port Conflicts**:
   - Ensure MySQL is not using the same port as your application (8080)

### Logs

Check application logs for detailed error messages:
```bash
tail -f logs/application.log
```

## Benefits

- **Offline Capability**: SQLite continues to work offline
- **Remote Data**: MySQL handles server-side data like adjustment details
- **Scalability**: Can easily add more remote tables to MySQL
- **Data Separation**: Clear separation between local and remote data
- **Transaction Management**: Each database has its own transaction manager
