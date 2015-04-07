package com.github.johhy.simpleshopaxon.test.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.github.johhy.simpleshopaxon.query.productcell",
	"com.github.johhy.simpleshopaxon.query.customer"})
@EnableTransactionManagement
public class JpaTestConfig {

	final static String PACKAGE_TO_SCAN = "com.github.johhy.simpleshopaxon.query";
	 
	@Bean
	  public DataSource dataSource() {
		 EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		 return builder.setType(EmbeddedDatabaseType.HSQL).build();
	  }
	 
	  @Bean
	  public EntityManagerFactory entityManagerFactory() {
		 HibernateJpaVendorAdapter vendorAdapter = 
		    		new HibernateJpaVendorAdapter();
		 vendorAdapter.setGenerateDdl(true);
		 vendorAdapter.setShowSql(false);
		 vendorAdapter.setDatabase(Database.HSQL);
		    
		 LocalContainerEntityManagerFactoryBean factory = 
		    		new LocalContainerEntityManagerFactoryBean();
		 factory.setJpaVendorAdapter(vendorAdapter);
		 factory
		 	.setPackagesToScan(PACKAGE_TO_SCAN, "org.axonframework.eventstore.jpa");
		 factory.setDataSource(dataSource());
		 factory.afterPropertiesSet();
		 return factory.getObject();
	  }

	  @Bean
	  public PlatformTransactionManager transactionManager() {
		 JpaTransactionManager txManager = new JpaTransactionManager();
		 txManager.setEntityManagerFactory(entityManagerFactory());
		 return txManager;
	  }
}
