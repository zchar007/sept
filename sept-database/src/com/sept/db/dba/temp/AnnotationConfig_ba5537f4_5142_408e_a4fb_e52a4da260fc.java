package com.sept.db.dba.temp;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfig_e63abea0_34eb_4525_a455_701bbfa7eaac {
	@Bean(name="dataSource")
  private DataSource dataSource() { 
    class com.alibaba.druid.pool.DruidDataSource ds = new class com.alibaba.druid.pool.DruidDataSource(); 
    ds.setUsername("aecc"); 
    ds.setPassword("password"); 
    ds.setUrl("jdbc:oracle:thin:@10.24.19.152:1521:oradb"); 
    ds.setDriver(new class oracle.jdbc.driver.OracleDriver()); 
    return ds; 
  }
}