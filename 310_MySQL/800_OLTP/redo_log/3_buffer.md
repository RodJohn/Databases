# 5 redo log buffer     
    
## 5.3 日志信息

概述

    innodb的redo log是基于页的
    记录了页的修改信息
    
数据格式    
    
    格式
        redo_log_type   space   page_no   redo_log_body
    解析
        reod_log_type: 占用1字节，表示重做日志类型。各种不同操作有不同的重做日志格式，但有基本的格式。        
        space：表空间的ID，采用压缩的方式，占用空间可能小于4字节。
        page_no:页的偏移量，同样采用压缩方式
        redo_log_body:每个重做日志的数据部分，恢复时需要调用相应的函数解析。        
  
   
## 5.6 log block    

概述

    在InnoDB存储引擎中，重做日志都是以512字节进行存储的.
    也就是说重做日志缓冲，重做日志文件都是以块(block)的方式进行保存.称为：重做日志块(redo log block)

分块

    事务的日志写入是基于块的，如果事务的日志大小小于496字节，那么会合其他的事务日志合并在一个块中，
    如果事务日志的大小大于496字节，那么会以496为长度进行分离存储。
    例如：T1 = 700字节大小，T2 = 100字节大小存储结构如下：
    如果一个页中产生的重做日志大于512字节，就分割成多个重做日志块就行存储.


数据格式
   
    日志块的组成：
        日志块头(log block header),日志本身，日志块尾(log block tailer) 

    Log Block Header 解析：
        LOG_BLOCK_HDR_NO:4字节
        log buffer由log block组成，在内部就像一个数组，而LOG_BLOCK_HDR_NO,用来标记这个数组中的位置。
        LOG_BLOCK_DATA_LEN:2字节
        表示LOG_BLOCK所占用的大小，被写满时，该值为：0x200,表示全部block空间，即占用512字节。
        LOG_BLOCK_FIRST_REC_GROUP:占用2字节
        表示LOG_BLOCK中第一个日志所在的偏移量。如果LOG_BLOCK_FIRST_REC_GROUP=LOG_BLOCK_DATA_LEN 表示log block不包含新的日志。
        LOG_BLOCK_CHECKPOINT_NO：4字节
        表示：LOG_BLOCK最后被写入时的检查点。如果此时log block还没写满，只能等下次log flush 时，才会更新。
    
    
    
## 5.8 buffer size

    
    可以通过innodb_log_buffer_size参数设置
     
    重做日志缓冲不需要设置的太大，只要保证每秒产生的事务量在缓冲大小范围之内。
    因为每秒都会刷新缓冲到日志文件。8M足够了。

