

# 1 概述

undo log

    Undo Log 是为了实现事务的原子性
    
    ROLLBACK
    MVCC
    
    
原理

    为了满足事务的原子性，
    在操作任何数据之前，首先将数据备份到一个地方（这个存储数据备份的地方称为Undo Log）。
    然后进行数据的修改。
    如果出现了错误或者用户执行了ROLLBACK语句，系统可以利用Undo Log中的备份将数据恢复到事务开始之前的状态。   


    undo log主要为事务的回滚服务。
    在事务执行的过程中，除了记录redo log，还会记录一定量的undo log。
    undo log记录了数据在每个操作前的状态，如果事务执行过程中需要回滚，就可以根据undo log进行回滚操作。
    单个事务的回滚，只会回滚当前事务做的操作，并不会影响到其他的事务做的操作。

    Undo Log的原理很简单，为了满足事务的原子性，在操作任何数据之前，首先将数据备份到一个地方（这个存储数据备份的地方称为Undo Log）。然后进行数据的修改。如果出现了错误或者用户执行了ROLLBACK语句，系统可以利用Undo Log中的备份将数据恢复到事务开始之前的状态。

rollback segment

回滚段

    仅保存undo log 的位置

    数量
        使用undo的并发线程

        5.6
        5.7


    undo log放在ibdata
    
    
undo log records 
    
    每个事务
    
    逻辑记录
     记录数据的前后项
        
    
版本

    当产生数据变更时，我们需要使用Undo log记录下变更前的数据以维护多版本信息
    
    
# 流程


    完成Undo log写入后，构建新的回滚段指针并返回（trx_undo_build_roll_ptr），回滚段指针包括undo log所在的回滚段id、日志所在的page no、以及page内的偏移量，需要记录到聚集索引记录中。    
    

# undo页申请
    
    当开启一个读写事务时，我们需要预先为事务申请对应的undo页



    innodb存储引擎对undo的管理同样采用段的方式。
    每个rollback segment 包含1024个undo log segment，
    事务在每个undo log segment中进行undo页的申请。
    一个回滚段可以支持1024个事务并发，

# 存储管理

    
    Undo记录默认被记录到系统表空间(ibdata)中，但从5.6开始，也可以使用独立的Undo 表空间。


## 参数设置

查看

    mysql> show variables like '%undo%';
    +-------------------------+-------+
    | Variable_name           | Value |
    +-------------------------+-------+
    | innodb_undo_directory   | .     |
    | innodb_undo_logs        | 128   |
    | innodb_undo_tablespaces | 0     |
    +-------------------------+-------+
    3 rows in set (0.00 sec)

innodb_undo_directory 
     
    相对的或者绝对路径 InnoDB 创建单独的表空间用于undo logs.
    通常用于防止那些日志在不同的存储设备,和innodb_undo_logs and innodb_undo_tablespaces 结合起来使用,
    确定了undo logs的磁盘布局 默认值是"." 表示同样的目录和InnoDB创建它的其他日志文件一样
     
innodb_undo_logs:
     
     
    定义 undo logs 的数量(也称为回滚段).
    每个undo log 可以接待多大1024个事务
     
    这个设置是合适的用于性能调优 如果你观察到undo logs的相关争用。
     
    innodb_undo_logs  选项替换innodb_rollback_segments
     
     
    对于可用undo logs的总量,而不是活动的数量
     
    mysql> SHOW GLOBAL STATUS;
     
    Innodb_available_undo_logs：
     
    可用InnoDB undo logs 的总的数量,补充innodb_undo_logs 系统变量, 报告活动undo logs的数量
     
     
     
    虽然你可以增加或者减少 undo logs 使用的数量, undo logs的数量物理表现在系统山该不会减少。
     
    因此你可能 开始一个低的值用于这个参数,逐步增大它,以避免分配不需要的undo logs.
     
    如果 innodb_undo_logs 没有被设置,它默认最小值是128
     
    jrhdpt11:/root# mysql -uroot -p'R00t,uHagt.0511' -e"SHOW GLOBAL STATUS;" | grep undo
    Warning: Using a password on the command line interface can be insecure.
    Innodb_available_undo_logs	128
     
     
     
innodb_undo_tablespaces:
     
     
    表空间文件的数量,undo logs 是被分成当你使用一个非0的 innodb_undo_logs setting
    默认的,所有的undo logs 是system tablespace的一部分, system tablespace 总是包含一个undo tablespace
    除了那些配置了innodb_undo_tablespaces之外
    因为undo logs 可以变大 在运行长时间运行的事务,让undo logs 在多个tablespaces 降低了 任何单个表空间的最大值
     
    重要:
     
    innodb_undo_tablespaces 只能在初始化之前配置MySQL 实例,以后不能更改。
    如果没有值指定, 实例是使用默认设置初始化。


