package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

public class ConnectionDb {
    private static final Logger LOGGER = Logger.getLogger(ConnectionDb.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static ConnectionDb singleInstance = new ConnectionDb();

    private ConnectionDb() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement statement) {
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
