package com.john.rod.jdbc;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

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
    public void initConnection() throws SQLException {


        Statement statement = connection.createStatement();

        //
        statement.execute("CREATE TABLE TEST(NAME VARCHAR)");
        statement.executeUpdate("INSERT INTO TEST VALUES('测试数据一')");
        statement.executeUpdate("INSERT INTO TEST VALUES('测试数据二')");


        // retrive data
        ResultSet result = statement.executeQuery("select name from test ");

        while (result.next()) {
            System.out.println(result.getString("name"));
        }


        result.close();
        statement.execute("DROP TABLE TEST");
        statement.close();
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
