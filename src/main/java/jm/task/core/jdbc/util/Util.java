package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kata_jdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "qwer1234Q!";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
        return conn;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties setting = new Properties();
                setting.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                setting.put(Environment.URL, "jdbc:mysql://localhost:3306/kata_jdbc?useSSL=false");
                setting.put(Environment.USER, USERNAME);
                setting.put(Environment.PASS, PASSWORD);
                setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                setting.put(Environment.SHOW_SQL, "true");

                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                setting.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(setting);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}