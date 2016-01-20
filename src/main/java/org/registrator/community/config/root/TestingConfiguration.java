package org.registrator.community.config.root;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.registrator.community.components.TestDataInitializer;
import org.registrator.community.config.SecurityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@Configuration
@EnableTransactionManagement
@Profile("testing")
//@ComponentScan("org.registrator") 
@EnableJpaRepositories(basePackages="org.registrator.community.dao")
//@org.springframework.context.annotation.Import({ SecurityConfiguration.class })
public class TestingConfiguration {
	
	@Bean(initMethod="init")
	public TestDataInitializer initTestData() {
        return new TestDataInitializer();
    }
	
	@Bean(name = "datasource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://192.168.195.250/registratortest_db?useUnicode=yes&amp;"
//				+ "characterEncoding=UTF-8&amp;characterSetResults=UTF-8");
		dataSource.setUrl("jdbc:mysql://localhost/registratortest_db?useUnicode=yes&amp;"
				+ "characterEncoding=UTF-8&amp;characterSetResults=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DriverManagerDataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean
				.setPackagesToScan(new String[] {"org.registrator.community.entity"});
		entityManagerFactoryBean
				.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		entityManagerFactoryBean
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Map<String, Object> jpaProperties = new HashMap<String, Object>();
		jpaProperties.put("hibernate.hbm2ddl.auto", "create");
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.format_sql", "true");
		jpaProperties.put("hibernate.use_sql_comments", "true");
		jpaProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
		return entityManagerFactoryBean;
	}
	
	@Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
                                                         DriverManagerDataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
