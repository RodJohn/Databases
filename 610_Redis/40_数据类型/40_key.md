

exists key

    key是否存在
	返回1存在,0不存在
	

del key 

	删除key,可以多个
	如果删除的key不存在，则直接忽略。
	返回删除的个数
	
	
KEYS pattern

    查找所有符合给定模式pattern（正则表达式）的 key 
    会阻塞服务器
    KEYS命令一次性返回所有匹配的key，所以，当redis中的key非常多时，对于内存的消耗和redis服务器都是一个隐患
    

scan pattern  

    SCAN cursor [MATCH pattern] [COUNT count]  
    模糊查询。不会阻塞服务器
  
    原理利用Linux的xargs
    redis-cli KEYS “edu:*” | xargs redis-cli DEL
    SCAN 每次执行都只会返回少量元素，所以可以用于生产环境
    
    SCAN命令是一个基于游标的迭代器。这意味着命令每次被调用都需要使用上一次这个调用返回的游标作为该次调用的游标参数，以此来延续之前的迭代过程
    
    当SCAN命令的游标参数（即cursor）被设置为 0 时， 服务器将开始一次新的迭代， 而当服务器向用户返回值为 0 的游标时， 表示迭代已结束

TYPE 
返回key所存储的value的数据结构类型，它可以返回string, list, set, zset 和 hash等不同的类型。

MOVE 
将当前数据库的 key 移动到给定的数据库 db 当中。
如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果。
 MOVE key db 


使用 : 连接作为命名空间


3、假如Redis里面有1亿个key，其中有10w个key是以某个固定的已知的前缀开头的，如果将它们全部找出来？




