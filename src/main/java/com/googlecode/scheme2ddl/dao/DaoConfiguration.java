package com.googlecode.scheme2ddl.dao;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DaoConfiguration {

    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setUser("activiti");
        dataSource.setPassword("welcome1");
        dataSource.setConnectionCachingEnabled(true);
        return dataSource;
    }

}
