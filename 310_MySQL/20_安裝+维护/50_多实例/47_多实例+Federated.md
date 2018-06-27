    
    
    -- mysql1 准备数据
    --
    mysql> show variables like "port"; 
    +---------------+-------+
    | Variable_name | Value |
    +---------------+-------+
    | port          | 3306  |   -- mysql1 实例端口
    +---------------+-------+
    1 row in set (0.00 sec)
    
    mysql> create database burn;
    Query OK, 1 row affected (0.00 sec)
    
    mysql> use burn;
    Database changed
    
    mysql> create table book (
        -> id int not null auto_increment,
        -> name varchar(128) not null,
        -> primary key(id)
        -> );
    Query OK, 0 rows affected (0.20 sec)
    
    mysql> insert into book values(1, "book1");
    Query OK, 1 row affected (0.02 sec)
    
    mysql> select * from book;
    +----+-------+
    | id | name  |
    +----+-------+
    |  1 | book1 |
    +----+-------+
    1 row in set (0.00 sec)
    
    mysql> create user 'burn'@'127.0.0.1' identified by '123';
    Query OK, 0 rows affected (0.00 sec)
    
    mysql> grant select on burn.* to 'burn'@'127.0.0.1';
    Query OK, 0 rows affected (0.00 sec)
    
    mysql> show grants for 'burn'@'127.0.0.1';
    +------------------------------------------------+
    | Grants for burn@127.0.0.1                      |
    +------------------------------------------------+
    | GRANT USAGE ON *.* TO 'burn'@'127.0.0.1'       |
    | GRANT SELECT ON `burn`.* TO 'burn'@'127.0.0.1' |
    +------------------------------------------------+
    2 rows in set (0.00 sec)
    --
    -- mysql2 测试Federated
    --
    mysql> show variables like "port";
    +---------------+-------+
    | Variable_name | Value |
    +---------------+-------+
    | port          | 3307  |  -- msyql2 实例端口
    +---------------+-------+
    1 row in set (0.01 sec)
    
    mysql> show engines;
    +--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
    | Engine             | Support | Comment                                                        | Transactions | XA   | Savepoints |
    +--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
    | MyISAM             | YES     | MyISAM storage engine                                          | NO           | NO   | NO         |
    | CSV                | YES     | CSV storage engine                                             | NO           | NO   | NO         |
    | PERFORMANCE_SCHEMA | YES     | Performance Schema                                             | NO           | NO   | NO         |
    | BLACKHOLE          | YES     | /dev/null storage engine (anything you write to it disappears) | NO           | NO   | NO         |
    | MRG_MYISAM         | YES     | Collection of identical MyISAM tables                          | NO           | NO   | NO         |
    | InnoDB             | DEFAULT | Supports transactions, row-level locking, and foreign keys     | YES          | YES  | YES        |
    | ARCHIVE            | YES     | Archive storage engine                                         | NO           | NO   | NO         |
    | MEMORY             | YES     | Hash based, stored in memory, useful for temporary tables      | NO           | NO   | NO         |
    | FEDERATED          | NO      | Federated MySQL storage engine                                 | NULL         | NULL | NULL       |
    +--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
    --
    -- federated 引擎没有打开
    --
    9 rows in set (0.00 sec)
    
    
    #
    # 在[mysqld2]标签下面增加federated
    #
    
    [root@MyServer ~]> cat /etc/my.cnf
    # ... 省略 ...
    [mysqld2]
    federated # 新增的配置项，表示打开federated引擎
    # ... 省略 ...
    [root@MyServer ~]> mysqld_multi stop 2
    [root@MyServer ~]> mysqld_multi start 2   # 重启配置
    mysql> show variables like "port";
    +---------------+-------+
    | Variable_name | Value |
    +---------------+-------+
    | port          | 3307  |  -- msyql2 实例端口
    +---------------+-------+
    1 row in set (0.01 sec)
    
    mysql> show engines;
    +--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
    | Engine             | Support | Comment                                                        | Transactions | XA   | Savepoints |
    +--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
    | MyISAM             | YES     | MyISAM storage engine                                          | NO           | NO   | NO         |
    | CSV                | YES     | CSV storage engine                                             | NO           | NO   | NO         |
    | PERFORMANCE_SCHEMA | YES     | Performance Schema                                             | NO           | NO   | NO         |
    | BLACKHOLE          | YES     | /dev/null storage engine (anything you write to it disappears) | NO           | NO   | NO         |
    | MRG_MYISAM         | YES     | Collection of identical MyISAM tables                          | NO           | NO   | NO         |
    | InnoDB             | DEFAULT | Supports transactions, row-level locking, and foreign keys     | YES          | YES  | YES        |
    | ARCHIVE            | YES     | Archive storage engine                                         | NO           | NO   | NO         |
    | MEMORY             | YES     | Hash based, stored in memory, useful for temporary tables      | NO           | NO   | NO         |
    | FEDERATED          | YES     | Federated MySQL storage engine                                 | NO           | NO   | NO         |
    +--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
    9 rows in set (0.00 sec)
    --
    -- 显示 federated 已经启用
    --
    mysql> create database federated_test;
    Query OK, 1 row affected (0.00 sec)
    
    mysql> use federated_test;
    Database changed
    
    mysql> create table federated_table_1 (
        -> id int not null auto_increment,
        -> name varchar(128) not null,
        -> primary key(id)
        -> ) engine=federated
        -> connection='mysql://burn:123@127.0.0.1:3306/burn/book';
    Query OK, 0 rows affected (0.04 sec)
    
    mysql> select * from federated_table_1;
    +----+-------+
    | id | name  |
    +----+-------+
    |  1 | book1 |  -- 和 mysqld1 上的内容一致。
    +----+-------+
    1 row in set (0.00 sec)
    --
    -- 由于只有select权限，无法对该表进行insert操作
    --
    mysql> insert into  federated_table_1 values(2, "book2");
    ERROR 1296 (HY000): Got error 10000 'Error on remote system: 1142: INSERT command denied to user 'burn'@'127.0.0.1' for table 'book'' from FEDERATED
    
