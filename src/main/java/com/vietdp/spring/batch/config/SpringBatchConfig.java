package com.vietdp.spring.batch.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    @Autowired
    private Environment e;

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(e.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(e.getProperty("spring.datasource.url"));
        dataSource.setUsername(e.getProperty("spring.datasource.username"));
        dataSource.setPassword(e.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Autowired
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory(final DataSource dataSource) {
        try {
            final Properties properties = new Properties();
            properties.put("hibernate.dialect", e.getProperty("hibernate.dialect"));
            properties.put("hibernate.hbm2ddl.auto", e.getProperty("hibernate.hbm2ddl.auto"));
            final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            entityManagerFactoryBean.setDataSource(dataSource);
            entityManagerFactoryBean.setPackagesToScan("com.vietdp*");
            entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
            entityManagerFactoryBean.setJpaProperties(properties);
            entityManagerFactoryBean.afterPropertiesSet();
            return entityManagerFactoryBean.getObject();
        } catch (final Exception exception) {
            throw exception;
        }

    }
    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

}
