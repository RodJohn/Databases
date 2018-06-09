
SQL中的关键字,函数名均使用大写字母，

字段名、表名.字段名均使用小写字母。

SQL语句必须用分号结尾.

写成小写也没事,只是为了规范

值是区分大小的

Linux下的MYSQL默认是要区分表名大小写的 ，而在windows下表名不区分大小写 
show Variables like ‘%table_names’

　　让MYSQL不区分表名大小写的方法其实很简单：

　　1.用ROOT登录，修改/etc/my.cnf

　　2.在[mysqld]下加入一行：lower_case_table_names=1

　　3.重新启动数据库即可

sql_mode
设置model 
常见mode 
可移植

sql_mode

mysql有sql_mode的区别,用于设置不同的特性,也便于迁移不同的数据库

设置model

默认的sql_mode为空,对于设置的错误数据,只是提示不报错 
sql_mode有全局和会话级别 
mode可以是多个模式的组合

查看 
select @@SESSION.sql_mode 
select @@GLOBAL.sql_mode 
设置 
set session sql_mode=’STRICT_TRANS_TABLES’;

常见mode

PIPES_AS_CONCAT 
将”||”视为字符串的连接操作符而非或运算符，这和Oracle数据库是一样 
NO_ZERO_IN_DATE 
在严格模式下，不允许日期和月份为零 
NO_ZERO_DATE 
设置该值，mysql数据库不允许插入零日期，插入零日期会抛出错误而不是警告。 
ERROR_FOR_DIVISION_BY_ZERO 
在INSERT或UPDATE过程中，如果数据被零除，则产生错误而非警告。否则值为null 
NO_AUTO_CREATE_USER 
禁止GRANT创建密码为空的用户

STRICT_TRANS_TABLES 
TRADITIONAL 
这两个是严格模式， 
当在列中插入或更新不正确的值时，mysql将会给出错误，并且放弃insert/update操作。 
在我们的一般应用中建议使用这两种模式。 
否则,日期字段可以插入’0000-00-00 00:00:00’这样的值，或者要插入的字段长度超过列定义的长度，会自动截断

可移植

POSTGRESQL 相当于 
PIPES_AS_CONCAT、ANSI_QUOTES、IGNORE_SPACE、NO_KEY_OPTIONS、NO_TABLE_OPTIONS、NO_FIELD_OPTIONS

设计
多对多中间表

多对一附表

列名的方法 
iid 
不会和关键字冲突 
下划线代替驼峰

数据冗余的 
没有冗余–展示的时候必须再次查询–而且不直观 
有冗余–要维护冗余

中间表 唯一索引 和 state 
主表要尽量缩减 将不是必须的数据拆分出去 特别是长的varchar text

主键在分表的情况下 自增

关系表的主键 还是联合主键 对联合主键的支持不好 而且效率低