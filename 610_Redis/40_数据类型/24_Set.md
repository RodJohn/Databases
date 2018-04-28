
# 1 集合对象

特点

    集合成员是唯一的
    集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
    

作用

    去重
    提供集合运算功能
    

# 2 操作

## 2.1 普通操作

SADD 
    
    SADD key member [member ...] 
    
    添加一个或多个指定的member元素到集合的 key中.
    如果member已经在集合中存在则忽略.
    如果集合key不存在，则新建集合key,并添加member元素到集合key中.
    返回成功添加的成员数量

SISMEMBER 

    SISMEMBER key member
    member在set中返回1,不存在返回0

    
SREM
 
    SREM key member [member ...]
     
    在key集合中移除指定的元素. 
    如果指定的元素不是key集合中的元素则忽略
    如果key集合不存在则被视为一个空的集合，该命令返回0。
 

## 2.3 集合操作

SDIFF 

    SDIFF key [key ...]
    返回一个集合与给定集合的差集的元素.

SINTER 

    SINTER key [key ...] 
    返回指定所有的集合的成员的交集。
 
SUNION 
    
    SUNION key [key ...] 
    返回给定的多个集合的并集中的所有成员.
 
SMOVE

    SMOVE source destination memver
    将member 

# 结构

type
redis_set
encoding
intset hashtable
 
  
# 原理 

集合类型在redis内部使用值为空的散列表（hash table）实现，所以集合中的加入或删除元素等时间复杂度为O(1)。
集合具有元素唯一性。

Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。

集合（Set）
Redis集合是一个无序的，不允许相同成员存在的字符串合集（Uniq操作，获取某段时间所有数据排重值）
支持一些服务端的命令从现有的集合出发去进行集合运算，如合并（并集：union）,求交(交集：intersection)，差集, 找出不同元素的操作（共同好友、二度好友）
用集合跟踪一个独特的事。想要知道所有访问某个博客文章的独立IP？只要每次都用SADD来处理一个页面访问。那么你可以肯定重复的IP是不会插入的（ 利用唯一性，可以统计访问网站的所有独立IP）
Redis集合能很好的表示关系。你可以创建一个tagging系统，然后用集合来代表单个tag。接下来你可以用SADD命令把所有拥有tag的对象的所有ID添加进集合，这样来表示这个特定的tag。如果你想要同时有3个不同tag的所有对象的所有ID，那么你需要使用SINTER
使用SPOP或者SRANDMEMBER命令随机地获取元素

Set的内部是用HashMap实现的，Set只用了HashMap的key列来存储对象。
