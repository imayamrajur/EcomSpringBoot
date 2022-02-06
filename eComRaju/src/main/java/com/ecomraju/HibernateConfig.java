package com.ecomraju;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
// import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:datasource.properties")
public class HibernateConfig {
	
	@Autowired
	Environment evn;
	
	@Bean
	public DataSource getDataSource() {
		final DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(evn.getProperty("db.driver"));
		datasource.setUrl(evn.getProperty("db.url"));
		datasource.setUsername(evn.getProperty("db.username"));
		datasource.setPassword(evn.getProperty("db.password"));
		return datasource;
	}

	@Bean
	@Autowired
	public SessionFactory getSessionFactory(DataSource dataSource) throws IOException {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setPackagesToScan(new String[] { "com.ecomraju.entity" });
		factoryBean.setDataSource(dataSource);
		Properties props = factoryBean.getHibernateProperties();
		props.put("spring.jpa.properties.hibernate.dialect", evn.getProperty("hd.dialect"));
		props.put("spring.jpa.hibernate.ddl-auto", evn.getProperty("hd.ddl-auto"));
		props.put("hibernate.show_sql", evn.getProperty("hd.show-sql"));
		props.put("current_session_context_class", evn.getProperty("hd.session"));
		factoryBean.afterPropertiesSet();
		SessionFactory sessionFactory = factoryBean.getObject();
		
		return  sessionFactory;

	}

	@Bean
	@Autowired
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

}
