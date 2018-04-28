


# RedisObject

redis每个database维护了K到V的映射关系，
其中key为固定的string，而value类型可能是string,list,hash等。


redis的这个映射关系是用一个dict来维护的，
为了再同一个dict能够存储不同类型的value，redis使用了一个通用结构robj。

如果value是一个list，则其内部是quicklist，如果value是一个string，则其内部结构可能是个sds或者long。

Redis内部使用一个redisObject对象来表示所有的key和value


# RedisObject

定义

    typedef struct RedisObject{
        type;
        encoding;
        ptr;
    }

type

    对象类型(string/list/hash/set/sortedSet)

encoding

    底层数据结构(ziplist)

TYPE
 
返回key所存储的value的数据结构类型，它可以返回string, list, set, zset 和 hash等不同的类型。













