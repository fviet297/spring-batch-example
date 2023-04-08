package com.vietdp.spring.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.MapJobExecutionDao;
import org.springframework.batch.core.repository.dao.MapJobInstanceDao;
import org.springframework.batch.core.repository.dao.MapStepExecutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.Duration;
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

    @Bean
    public SseEmitter sseEmitter() {
        return new SseEmitter();
    }
}