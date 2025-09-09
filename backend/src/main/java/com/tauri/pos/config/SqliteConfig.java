package com.tauri.pos.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "sqliteEntityManagerFactory",
    transactionManagerRef = "sqliteTransactionManager",
    basePackages = {"com.tauri.pos.sqlite.persistance.dao"}
)
public class SqliteConfig {

    @Primary
    @Bean(name = "sqliteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlite")
    public DataSource sqliteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqliteEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqliteEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sqliteDataSource") DataSource dataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        
        return builder
                .dataSource(dataSource)
                .packages("com.tauri.pos.sqlite.persistance.eo")
                .persistenceUnit("sqlite")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "sqliteTransactionManager")
    public PlatformTransactionManager sqliteTransactionManager(
            @Qualifier("sqliteEntityManagerFactory") LocalContainerEntityManagerFactoryBean sqliteEntityManagerFactory) {
        return new JpaTransactionManager(sqliteEntityManagerFactory.getObject());
    }
}
