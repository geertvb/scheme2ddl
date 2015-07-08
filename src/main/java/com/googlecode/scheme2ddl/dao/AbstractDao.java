package com.googlecode.scheme2ddl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

public class AbstractDao extends JdbcDaoSupport {

    @Autowired
    public void init(DataSource dataSource) {
        setDataSource(dataSource);
    }

}
