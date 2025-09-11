# Backend Structure Documentation

## Overview

The backend has been reorganized to provide clear separation between SQLite (local/offline) and MySQL (remote/online) database operations. This improves code readability, maintainability, and makes it easier to understand the data flow.

## New Package Structure

```
backend/src/main/java/com/tauri/pos/
├── config/                    # Global configuration classes
│   ├── DataInitializer.java
│   └── SecurityConfig.java
├── shared/                    # Shared components across all modules
│   ├── dto/                   # Data Transfer Objects
│   ├── enums/                 # Shared enumerations
│   │   ├── OrderStatus.java
│   │   └── SyncStatus.java
│   └── utils/                 # Utility classes
├── sqlite/                    # SQLite (Local/Offline) components
│   ├── controller/            # REST controllers for offline operations
│   ├── service/               # Business logic for offline operations
│   ├── repository/            # Data access layer for SQLite
│   │   ├── LocalProductRepository.java
│   │   └── LocalOrderRepository.java
│   ├── model/                 # JPA entities for SQLite
│   │   ├── LocalProduct.java
│   │   ├── LocalOrder.java
│   │   └── LocalOrderItem.java
│   └── config/                # SQLite-specific configuration
├── mysql/                     # MySQL (Remote/Online) components
│   ├── controller/            # REST controllers for online operations
│   ├── service/               # Business logic for online operations
│   ├── repository/            # Data access layer for MySQL
│   │   ├── ProductRepository.java
│   │   └── OrderRepository.java
│   ├── model/                 # JPA entities for MySQL
│   │   ├── Product.java
│   │   ├── Order.java
│   │   └── OrderItem.java
│   └── config/                # MySQL-specific configuration
├── sync/                      # Synchronization between SQLite and MySQL
│   ├── service/               # Sync service implementations
│   ├── strategy/              # Different sync strategies
│   └── config/                # Sync-specific configuration
└── TauriPosApplication.java   # Main application class
```

## Package Responsibilities

### 1. `shared/` Package
- **Purpose**: Contains components used across multiple modules
- **Contents**:
  - `enums/`: Shared enumerations like `OrderStatus`, `SyncStatus`
  - `dto/`: Data Transfer Objects for API communication
  - `utils/`: Utility classes and helper functions

### 2. `sqlite/` Package
- **Purpose**: Handles all local/offline operations using SQLite database
- **Use Cases**:
  - Offline POS operations
  - Local data storage
  - Sync queue management
- **Key Features**:
  - Local entities with sync status tracking
  - Offline-first data operations
  - Conflict resolution preparation

### 3. `mysql/` Package
- **Purpose**: Handles all remote/online operations using MySQL database
- **Use Cases**:
  - Online POS operations
  - Central data management
  - Multi-store synchronization
- **Key Features**:
  - Standard JPA entities
  - Online data operations
  - Central data source

### 4. `sync/` Package
- **Purpose**: Manages synchronization between SQLite and MySQL
- **Use Cases**:
  - Bidirectional data sync
  - Conflict resolution
  - Sync status management
- **Key Features**:
  - Data transformation between local and remote models
  - Sync strategies (push, pull, bidirectional)
  - Error handling and retry logic

## Data Flow

### Offline Operations (SQLite)
1. User performs operations (create order, update product)
2. Data is stored in SQLite with `SYNC_STATUS = PENDING`
3. Operations continue normally offline
4. When online, sync service processes pending changes

### Online Operations (MySQL)
1. User performs operations through online endpoints
2. Data is stored directly in MySQL
3. Changes are immediately available to other clients
4. Sync service can pull changes to local SQLite

### Synchronization Flow
1. **Push to Remote**: Local changes → MySQL
2. **Pull from Remote**: MySQL changes → Local
3. **Conflict Resolution**: Handle data conflicts
4. **Status Updates**: Update sync statuses

## Benefits of New Structure

### 1. **Clear Separation of Concerns**
- SQLite operations are isolated in `sqlite/` package
- MySQL operations are isolated in `mysql/` package
- Sync logic is centralized in `sync/` package

### 2. **Improved Readability**
- Easy to understand which database is being used
- Clear package naming conventions
- Logical grouping of related components

### 3. **Better Maintainability**
- Changes to one database don't affect the other
- Easier to add new features to specific databases
- Clear boundaries for testing

### 4. **Scalability**
- Easy to add new sync strategies
- Can add more database types in the future
- Modular design supports microservices migration

## Migration Guide

### For Developers
1. **Import Updates**: Update import statements to use new package names
2. **Entity References**: Use `sqlite.model.*` for local entities, `mysql.model.*` for remote entities
3. **Repository Access**: Use appropriate repository based on operation type
4. **Service Layer**: Use `sqlite.service.*` for offline operations, `mysql.service.*` for online operations

### For Configuration
1. **Database Configs**: Separate configuration for SQLite and MySQL
2. **Sync Settings**: Centralized sync configuration in `sync.config.*`
3. **Security**: Maintained in global `config/` package

## Example Usage

### Creating a Local Product (SQLite)
```java
@Autowired
private LocalProductRepository localProductRepository;

public LocalProduct createLocalProduct(String name, BigDecimal price) {
    LocalProduct product = new LocalProduct(name, price, 0);
    product.setSyncStatus(SyncStatus.PENDING);
    return localProductRepository.save(product);
}
```

### Creating a Remote Product (MySQL)
```java
@Autowired
private ProductRepository productRepository;

public Product createRemoteProduct(String name, BigDecimal price) {
    Product product = new Product(name, "Description", price, 0, "Category");
    return productRepository.save(product);
}
```

### Syncing Data
```java
@Autowired
private SyncService syncService;

public void syncData() {
    syncService.syncProductsToRemote();  // SQLite → MySQL
    syncService.syncProductsFromRemote(); // MySQL → SQLite
}
```

This structure provides a solid foundation for a robust offline-first POS system with clear separation between local and remote data operations.
