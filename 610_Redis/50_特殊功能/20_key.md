
# 命名

使用 : 连接作为命名空间

key和数据库

# 简单操作

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


MOVE 

    将当前数据库的 key 移动到给定的数据库 db 当中。
    如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果。



# 3 有效期

## 过期

    EXPIRE一类命令能关联到一个有额外内存开销的key。
    当key执行过期操作时，Redis会确保按照规定时间删除他们。

    过期精度
    在 Redis 2.4 及以前版本，过期期时间可能不是十分准确，有0-1秒的误差。
    从 Redis 2.6 起，过期时间误差缩小到0-1毫秒。

## 3.3 命令

默认

    keys创建时默认的时间是-1(永久有效).

设置
    
    expire key seconds 
        设置key几秒后过期
        返回 1 成功设置,返回0 失败
    
    expire key timestamp 
        设置key的过期时间
        时间参数是 UNIX 时间戳 Unix timestamp 
        
    或者在set的时候设置    
    
    persist key
        设置key的过期时间为-1 (永久有效)

查询
    
    ttl key
        返回key剩余的时间(秒数)。
        在Redis 2.6和之前版本，如果key不存在或者已过期时返回-1。
        从Redis2.8开始，错误返回值的结果有如下改变：
        如果key不存在或者已过期，返回 -2
        如果key存在并且没有设置过期时间（永久有效），返回 -1 。
        
    pttl key  返回毫秒数
    
## 3.5 过期策略


    Redis keys过期有两种方式：被动和主动方式。
    
    当一些客户端尝试访问它时，key会被发现并主动的过期。
    
    当然，这样是不够的，因为有些过期的keys，永远不会访问他们。 无论如何，这些keys应该过期，所以定时随机测试设置keys的过期时间。所有这些过期的keys将会从密钥空间删除。
    具体就是Redis每秒10次做的事情：
    测试随机的20个keys进行相关过期检测。
    删除所有已经过期的keys。
    如果有多于25%的keys过期，重复步奏1.
    这是一个平凡的概率算法，基本上的假设是，我们的样本是这个密钥控件，并且我们不断重复过期检测，直到过期的keys的百分百低于25%,这意味着，在任何给定的时刻，最多会清除1/4的过期keys。
    

## 3.7 过期通知

http://blog.csdn.net/zpf0918/article/details/55511640
http://blog.csdn.net/liuchuanhong1/article/details/70147149
http://www.bubuko.com/infodetail-2243690.html



4、如果有大量的key需要设置同一时间过期，一般需要注意什么？

key过期时间太集中会导致系统在那个时间卡顿，因此一般在时间上加一个随机值，使其分散。



