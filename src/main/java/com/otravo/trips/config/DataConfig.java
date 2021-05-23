package com.otravo.trips.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(basePackages = "com.otravo.trips.repositories")
@EnableTransactionManagement()
public class DataConfig {

    @Value("${db.config.dataSourceClassName}")
    private String dataSourceClassName;
    @Value("${db.config.poolName}")
    private String poolName;
    @Value("${db.config.jdbcUrl}")
    private String jdbcUrl;
    @Value("${db.config.username}")
    private String user;
    @Value("${db.config.password}")
    private String password;
    @Value("${db.config.databaseName}")
    private String databaseName;
    @Value("${db.config.transactionIsolation}")
    private String transactionIsolation;
    @Value("${db.config.connectionTestQuery}")
    private String connectionTestQuery;
    @Value("${db.config.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${db.config.maxLifetime}")
    private int maxLifetime;
    @Value("${db.config.connectionTimeout}")
    private int connectionTimeout;
    @Value("${db.config.createStrategy}")
    private String createStrategy;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.otravo.trips.domain");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("hibernate.show_sql", createStrategy);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource(hikariConfig());
        return dataSource;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceClassName);
        config.setPoolName(poolName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("databaseName", databaseName);
        config.setConnectionTestQuery(connectionTestQuery);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMaxLifetime(maxLifetime);
        config.setConnectionTimeout(connectionTimeout);
        return config;
    }
}