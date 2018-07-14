

# 概述


作用 
    
    记录了页的更改
    用于保证事务的持久性
    
    
    
    redo log用于恢复提交事务的修改
    
    
   
# 结构

    重做日志写入过程

    在innodb的引擎实现中，为了实现事务的持久性，构建了重做日志系统。重做日志由两部分组成：内存日志缓冲区(redo log buffer)和重做日志文件。
    日志缓冲区是为了加快写日志的速度，而重做日志文件为日志数据提供持久化的作用。
    redo log 由 redo log buffer 和 redo log file 构成    

3.重做日志结构和关系图

     innodb在重做日志实现当中，设计了3个层模块，即redo log buffer、group files和archive files。这三个层模块的描述如下：
    
    redo log buffer        重做日志的日志内存缓冲区，新写入的日志都是先写入到这个地方.redo log buffer中数据同步到磁盘上，必须进行刷盘操作。
    
    group files       重做日志文件组，一般由3个同样大小的文件组成。3个文件的写入是依次循环的，每个日志文件写满后，即写下一个，日志文件如果都写满时，会覆盖第一次重新写。重做日志组在innodb的设计上支持多个。
    
    archive files         归档日志文件，是对重做日志文件做增量备份，它是不会覆盖以前的日志信息。
      
    重做日志组
    重做日志组可以支持多个，这样做的目的应该是为了防止一个日志组损坏后，可以从其他并行的日志组里面进行数据恢复。在MySQL-5.6的将日志组的个数设置为1，不允许多个group存在。网易姜承尧的解释是innodb的作者认为通过外层存储硬件来保证日志组的完整性比较好，例如raid磁盘。重做日志组的主要功能是实现对组内文件的写入管理、组内的checkpoint建立和checkpiont信息的保存、归档日志状态管理（只有第一个group才做archive操作）

   
   
# log block    

    物理逻辑日志

    innodb在日志系统里面定义了log block的概念，
    其实log block就是一个512字节的数据块，
    这个数据块包括块头、日志信息和块的checksum 
    
    Block no的最高位是描述block是否flush磁盘的标识位.通过lsn可以blockno,
    具体的计算过程是lsn是多少个512的整数倍，也就是no = lsn / 512 + 1;
    为什么要加1呢，因为所处no的块算成clac_lsn一定会小于传入的lsn.
    所以要+1。其实就是block的数组索引值。
    
    
    事务的日志写入是基于块的，如果事务的日志大小小于496字节，那么会合其他的事务日志合并在一个块中，如果事务日志的大小大于496字节，那么会以496为长度进行分离存储。例如：T1 = 700字节大小，T2 = 100字节大小存储结构如下：
    
    

# 日志格式


  
LSN
    
    LSN(log sequence number) 用于记录日志序号，
    它是一个不断递增的 unsigned long long 类型整数。
    
    LSN真正的含义是储存引擎向重做日志系统写入的日志量（字节数）
    
    在 InnoDB 的日志系统中，
    LSN 无处不在，它既用于表示修改脏页时的日志序号，
    也用于记录checkpoint，通过LSN，可以具体的定位到其在redo log文件中的位置。

    LSN是不会减小的，它是日志位置的唯一标记。在重做日志写入、checkpoint构建和PAGE头里面都有LSN。
    
    关于日志写入:
    
    例如当前重做日志的LSN = 2048,这时候innodb调用log_write_low写入一个长度为700的日志，2048刚好是4个block长度，那么需要存储700长度的日志，需要量个BLOCK(单个block只能存496个字节)。那么很容易得出新的LSN = 2048 + 700 + 2 * LOG_BLOCK_HDR_SIZE(12) + LOG_BLOCK_TRL_SIZE(4) = 2776。
    
# 

    在(2)中涉及知识：
    <1>.关于innodb_log_buffer_size的大小：(默认8M)
    mysql> show variables like 'innodb_log_buffer_size%';
    +------------------------+---------+
    | innodb_log_buffer_size | 8388608 |
    +------------------------+---------+
     
    8388608(Byte)/1024/1024=8M
     
    重做日志缓冲不需要设置的太大，只要保证每秒产生的事务量在缓冲大小范围之内。因为每秒都会刷新缓冲到日志文件。8M足够了。


# 写盘

    先行日志

写盘

    有几种场景可能会触发redo log写文件：
    
    Redo log buffer空间不足时
    事务提交
    后台线程
    做checkpoint
    实例shutdown时
    binlog切换时


commit
    
    我们所熟悉的参数innodb_flush_log_at_trx_commit 作用于事务提交时，这也是最常见的场景：
    
    当设置该值为1时，每次事务提交都要做一次fsync，这是最安全的配置，即使宕机也不会丢失事务；
    当设置为2时，则在事务提交时只做write操作，只保证写到系统的page cache，因此实例crash不会丢失事务，但宕机则可能丢失事务；
    当设置为0时，事务提交不会触发redo写操作，而是留给后台线程每秒一次的刷盘操作，因此实例crash将最多丢失1秒钟内的事务。
    
性能影响


# 原子写

    文件系统

    log write ahead
    上面已经介绍过，InnoDB以512字节一个block的方式对齐写入ib_logfile文件，但现代文件系统一般以4096字节为一个block单位。如果即将写入的日志文件块不在OS Cache时，就需要将对应的4096字节的block读入内存，修改其中的512字节，然后再把该block写回磁盘。
    
    为了解决这个问题，MySQL 5.7引入了一个新参数：innodb_log_write_ahead_size。当当前写入文件的偏移量不能整除该值时，则补0，多写一部分数据。这样当写入的数据是以磁盘block size对齐时，就可以直接write磁盘，而无需read-modify-write这三步了。
    
    注意innodb_log_write_ahead_size的默认值为8196，你可能需要根据你的系统配置来修改该值，以获得更好的效果。
    
    
    
# 恢复


关于checkpoint和日志恢复：

在page的fil_header中的LSN是表示最后刷新是的LSN, 假如数据库中存在PAGE1 LSN  = 1024，PAGE2 LSN = 2048， 系统重启时，检测到最后的checkpoint LSN = 1024，那么系统在检测到PAGE1不会对PAGE1进行恢复重做，当系统检测到PAGE2的时候，会将PAGE2进行重做。一次类推，小于checkpoint LSN的页不用重做，大于LSN checkpoint的PAGE就要进行重做。



# 总结
       Innodb的重做日志系统是相当完备的，它为数据的持久化做了很多细微的考虑,它效率直接影响MySQL的写效率，所以我们深入理解了它便以我们去优化它，尤其是在大量数据刷盘的时候。假设数据库的受理的事务速度大于磁盘IO的刷入速度，一定会出现同步建立checkpoint操作，这样数据库是堵塞的，整个数据库都在都在进行脏页刷盘。避免这样的问题发生就是增加IO能力，用多磁盘分散IO压力。也可以考虑SSD这读写速度很高的存储介质来做优化。

         
# 參考

    https://yq.aliyun.com/articles/219?spm=5176.100240.searchblog.93#13         