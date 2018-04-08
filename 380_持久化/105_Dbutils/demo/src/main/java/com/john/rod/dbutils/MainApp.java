package com.john.rod.dbutils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainApp {

    Server server = null;
    Connection connection = null;


    @Before
    public void register() throws SQLException {

        server = Server
                .createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", "8043"})
                .start();

        Driver driver = new org.h2.Driver();
        DriverManager.registerDriver(driver);

        connection = DriverManager
                .getConnection("jdbc:h2:./h2db/sxaz42b4", "sa", "sa");

    }


    @Test
    public void init () {
        QueryRunner qr = new QueryRunner();
        String sql = "CREATE TABLE TEST(NAME VARCHAR)";
        int i;
        try {
            i = qr.update(connection, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() throws SQLException {

        QueryRunner qr = new QueryRunner();
        String sql = "INSERT INTO TEST VALUES (?)";
        Object param[] = {"lijun"};
        int i;
        try {
            i = qr.update(connection, sql,param);
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void query() throws SQLException {

        QueryRunner qr = new QueryRunner();
        String sql = "SELECT * FROM TEST ";
        int i;
        try {
            List<Person> list = qr.query(connection, sql,new BeanListHandler<Person>(Person.class));
            list.forEach(one-> System.out.println(one.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    @After
    public void closeAll() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection = null;
            }
        }
        server.stop();
    }


}
