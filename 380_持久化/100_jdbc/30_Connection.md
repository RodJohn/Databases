

   
# 注册JDBC驱动程序

    注册驱动程序是将驱动程序的类文件加载到内存中的过程


正常流程

```
Driver myDriver = new oracle.jdbc.driver.OracleDriver();
DriverManager.registerDriver( myDriver );
```

快捷

```
Class.forName("oracle.jdbc.driver.OracleDriver");
实际是在静态代码块调用
```


# 创建连接对象


DriverManager.getConnection()用于创建连接对象

    getConnection(String url)
    getConnection(String url, Properties prop)
    getConnection(String url, String user, String password)




# 关闭连接对象


节约资源
conn.close();