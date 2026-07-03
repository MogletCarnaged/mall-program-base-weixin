package com.mall.common;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;

public class Global {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/mall?characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "212622314Xk";

    private static DataSource dataSource = null;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            try{
                Driver driver = new com.mysql.jdbc.Driver();
                dataSource = new SimpleDriverDataSource(driver,MYSQL_URL,MYSQL_USER,MYSQL_PASSWORD);
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return dataSource;
    }
}
