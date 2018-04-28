
# 1 有序集合

特点

    元素具有唯一性
    每个元素都关联了一个分数(分数可以相同)，分数为元素排序的依据
    提供排序的功能

作用

    排序
    范围操作

# 2 操作

## 2.1 元素操作

ZADD

    ZADD key [NX|XX] [CH] [INCR] score member [score member ...]
    
    //XX: 仅仅更新存在的成员，不添加新成员。
    //NX: 不更新存在的成员。只添加新成员。
    不添加XX/NX时,默认是不存在插入,存在着更新score
    
    //CH: 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。更改的元素是新添加的成员，已经存在的成员更新分数。 所以在命令中指定的成员有相同的分数将不被计算在内。
    //INCR: 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。

ZREM

    ZREM key member 
    
## 2.2 score操作

ZSCORE

    ZSCORE key member
    
    获取成员的score


ZINCRBY 
    
    ZINCRBY key increment member 
    
    为有序集key的成员member的score值加上增量increment。
    如果key中不存在member，就在key中添加一个member，score是increment.
    如果key不存在，就创建一个只含有指定member成员的有序集合。
    也有可能给一个负数来减少score的值。


 
## 2.3 范围操作

ZRANGEBYSCORE

    ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
    
    返回有序集key中，指定区间(score)内的成员。
    其中成员的位置按score值递减(从小到大)来排列。
    具有相同score值的成员按字典序的反序排列。
    属性WITHSCORES表示返回的值带上score
    默认包括score值等于min或max,可以通过给参数前增加(符号来使用可选的开区间

ZREMRANGEYSCORE     

    ZREMRANGEYSCORE key min max 
    
    删除指定分数区间的memeber
    
ZCOUNT
 
    ZCOUNT key min max 
    
    返回有序集中，score值在min和max之间(默认包括score值等于min或max)的成员总数。    

 
# 结构

type
redis_zset
encoding
ziplist skiplist

随机读取
集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)



有序集合类型和列表类型的相似点：

二者都是有序的。
二者都可以获得某一范围的元素。
有序集合类型和列表类型的区别：

列表类型内部由双向链表实现，访问靠近两端数据极快，但访问中间元素较慢，有序集合内部有散列表和跳跃表（skip list）实现，读取中间的数据也很快。
有序集合可以通过分数调整某个元素的位置，而列表类型无法简单实现。
有序集合比列表更耗费内存。





Redis有序集合和Redis集合类似，是不包含相同字符串的合集
每个有序集合的成员都关联着一个评分，这个评分用于把有序集合中的成员按最低分到最高分排列（排行榜应用，取TOP N操作）
使用有序集合，你可以非常快地（O(log(N))）完成添加，删除和更新元素的操作
元素是在插入时就排好序的，所以很快地通过评分(score)或者位次(position)获得一个范围的元素（需要精准设定过期时间的应用）
轻易地访问任何你需要的东西: 有序的元素，快速的存在性测试，快速访问集合中间元素
在一个巨型在线游戏中建立一个排行榜，每当有新的记录产生时，使用ZADD 来更新它。你可以用ZRANGE轻松地获取排名靠前的用户， 你也可以提供一个用户名，然后用ZRANK获取他在排行榜中的名次。 同时使用ZRANK和ZRANGE你可以获得与指定用户有相同分数的用户名单。 所有这些操作都非常迅速
有序集合通常用来索引存储在Redis中的数据。 例如：如果你有很多的hash来表示用户，那么你可以使用一个有序集合，这个集合的年龄字段用来当作评分，用户ID当作值。用ZRANGEBYSCORE可以简单快速地检索到给定年龄段的所有用户


当redis中的sorted set 数据较多时，sorted set的底层从ziplist转为dict+ skiplist。
redis的sorted set 底层是由skiplist,dict,ziplist来实现的。
sorted set是一个有序集合，当数据较少时，sorted set是由一个ziplist来实现的。

 当数据多的时候，sorted set是由一个zset来实现的，它包括dict和一个skiplist。其中dict用来查询data到score的关系，而skiplist用来根据score做范围查找。