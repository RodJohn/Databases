
# 6 redo log file

    重做日志缓冲是易失的 2.重做日志文件(redo log file)，是持久的

## 6.3 触发写入

写入时机

    Redo log buffer空间不足时
    事务提交
    后台线程做checkpoint
    实例shutdown时
    binlog切换时

特点

    为了确保每次重做日志都写入重做日志文件，在将重做日志缓冲写入重做日志文件后，InnoDB存储引擎都需要调用一次fsync操作。

## 6.5 commit时的写入

概述
    
    可以通过参数innodb_flush_log_at_trx_commit
    设置事务提交时redo log buffer的刷新动作

设置
    
    当设置为1时，(默认)
        每次事务提交都要做一次fsync，这是最安全的配置，即使宕机也不会丢失事务；
    当设置为2时，
        则在事务提交时只做write操作，只保证写到系统的page cache，因此实例crash不会丢失事务，但宕机则可能丢失事务；
    当设置为0时，
        事务提交不会触发redo写操作，而是留给后台线程每秒一次的刷盘操作，因此实例crash将最多丢失1秒钟内的事务。

考虑

    为了保证事务的持久性,需要将参数设置为1;
    0和2能提高事务提交性能，但是这种情况丧失了事务的ACID特性，因此在大量执行insert操作时，在最后执行一次commit操作。这样回滚时可以回滚到事务最开始的状态
        

## 6.8 原子写

    文件系统

    log write ahead
    上面已经介绍过，InnoDB以512字节一个block的方式对齐写入ib_logfile文件，但现代文件系统一般以4096字节为一个block单位。
    如果即将写入的日志文件块不在OS Cache时，就需要将对应的4096字节的block读入内存，修改其中的512字节，然后再把该block写回磁盘。
    
    为了解决这个问题，MySQL 5.7引入了一个新参数：innodb_log_write_ahead_size。当当前写入文件的偏移量不能整除该值时，则补0，多写一部分数据。这样当写入的数据是以磁盘block size对齐时，就可以直接write磁盘，而无需read-modify-write这三步了。
    
    注意innodb_log_write_ahead_size的默认值为8196，你可能需要根据你的系统配置来修改该值，以获得更好的效果。
    
## log group
 
    log group为重做日志组，里面有多个重做日志文件。源码中支持log group的镜像功能，但已禁用了，因此InnoDB存储引擎实际只有1个log group。
     
    log group 是逻辑上的概念！！！
     
    重做日志存储的就是之前在log buffer中保存的块，因此也是根据块的方式进行物理存储的管理。block=512bytes。
     
    redo log file的写入顺序：
         log block 写入追加到redo log file的最后部分，当一个redo log file写满时，会写入下一个redo log file。 这种方式:round-robin.
         看起来是顺序的，其实不然，除了保存log buffer刷新到磁盘的log block，还保存了一些其他信息，这些信息占：2KB，即redo log file 的前2KB不保存log block的信息。    

    2KB的信息：保存 4 * 512字节的 块。
     
    名称	
    大小（字节）
    log file header
    512
    checkpoint1
    512
    空
    512
    checkpoint2
    512
     
 
 
 
 
    
    上述信息只在log group的第一个redo log file里存储，其余file留空，这也就是说 写入不是顺序的

        
