package com.sept.db.dba;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AnnotationDBConfig {
	private static DataSource ds = null;

	@Bean(name = "dsBean")
	public DataSource dsBean() {
		return ds;
	}

	public static final void setDataSource(DataSource ds) {
		AnnotationDBConfig.ds = ds;
	}
}
