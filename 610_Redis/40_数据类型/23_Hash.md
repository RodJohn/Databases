
# 1 哈希对象

特点

    hash
    是一种字典结构
    适合用于存储对象
    一个散列键值可以包含最多2^32 -1 个字段

用处

    存储对象,更加原子


# 2 命令

## 2.1 值操作

SET

    HSET
    
        HSET key field value 
        将哈希表 key 中的字段 field 的值设为 value
        filed存在,则覆盖;不存在则添加
    
    HSETNX
        
        HSETNX key field value 
        只有在字段 field 不存在时，设置哈希表字段的值。

DEL
    
    HDEL 
    
        HDEL key filed
        删除
        
GET    
            
    HGET
        
        HGET key field 
        用于获取与字段中存储的键哈希相关联的值。   
    
    HVALS 
    
        HVALS key 

## 2.3 特殊操作



HINCRBY
    
    HINCRBY key field increment 
    为哈希表 key 中的指定字段的整数值加上增量 increment 。


# 结构

type
redis_hash
encoding
ziplist hashtable


Redis Hashes是字符串字段和字符串值之间的映射
尽管Hashes主要用来表示对象，但它们也能够存储许多元素
当前HashMap的实现有两种方式：
当HashMap的成员比较少时Redis为了节省内存会采用类似一维数组的方式来紧凑存储，而不会采用真正的HashMap结构，
这时对应的value的redisObject的encoding为zipmap，当成员数量增大时会自动转成真正的HashMap,此时encoding为ht。



hash过大


hash比string的好处,缺点

# 原理

hash的底层是ziplist或者dict。
当redis中的hash结构数据较多时，hash的底层会从ziplist转为dict。


hash的底层是ziplist，当数据变的越来越多，会转为dict，每次插入filed和value，ziplist数据项+2。
hash为什么要转为dict?
Ⅰ，ziplist的数据发生改动，会引发内存realloc,可能导致内存拷贝。
Ⅱ，ziplist里查找需要进行遍历，数据项过多会变慢。



Q:用redis的hash和string哪个结构更省内存？

A:不一定，因为可以先将对象用protocol buffer等方式序列化为字节数组，然后存入redis的string中。
而hash随着数据的增大或者value比较大的时候转为dict,存储效率会下降，
不过hash在支持的操作命令上比序列化后再存入string的方式还是
有优势的：它既支持多个field同时存取（hmset,hmget）,也支持按照某个特定的field单独存取（hset,hget）。