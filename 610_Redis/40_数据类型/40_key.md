

Key
key的设置:

exists key
	返回1存在,0不存在

del key 
	删除key,可以多个
	返回删除的个数

key是否存在

 EXISTS key [key ...]


查找所有符合给定模式pattern（正则表达式）的 key 。
KEYS pattern

DEL 
删除指定的key，如果删除的key不存在，则直接忽略。
DEL key [key ...]

TYPE 
返回key所存储的value的数据结构类型，它可以返回string, list, set, zset 和 hash等不同的类型。

MOVE 
将当前数据库的 key 移动到给定的数据库 db 当中。
如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果。
 MOVE key db 

``



3、假如Redis里面有1亿个key，其中有10w个key是以某个固定的已知的前缀开头的，如果将它们全部找出来？

keys + 正则表达式。会阻塞服务器

scan  模糊查询。不会阻塞服务器



