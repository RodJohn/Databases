

# 1 列表对象

特点

    列表类型
    是一个双向队列
    最多能容纳2^32 -1 个元素
    
用法

    lpush+lpop=Stack(栈) 
    lpush+rpop=Queue（队列） 
    lpush+ltrim=Capped Collection（有限集合） 
    lpush+brpop=Message Queue（消息队列）    

# 2 命令

## 2.1 队列操作


LPUSH

    LPUSH key value [value ...]
    
    将所有指定的值插入到存于 key 的列表的头部。
    如果 key 不存在，那么在进行 push 操作前会创建一个空列表。 
    元素是从最左端的到最右端的、一个接一个被插入到 list 的头部
    返回list长度

LPOP 

    LPOP key 
    移除并且返回 key 对应的 list 的第一个元素。


LRANGE 

    LRANGE key start stop
    
    返回存储在 key 的列表里指定范围内的元素。 
    start 和 end 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推。
    偏移量也可以是负数，表示偏移量是从list尾部开始计数。 例如， -1 表示列表的最后一个元素，-2 是倒数第二个，以此类推。

LTRIM

    LTRIM key start stop 
    
    让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除  
      

## 阻塞队列

BLPOP 

    BLPOP key timeout
    BLPOP 是阻塞式列表的弹出原语。 它是命令 LPOP 的阻塞版本，
    这是因为当给定列表内没有任何元素可供弹出的时候， 连接将被 BLPOP 命令阻塞。 
    当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
    当 BLPOP 被调用时，如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，并和被弹出元素所属的列表的名字 key 一起，组成结果返回给调用者。
    timeout 参数表示的是一个指定阻塞的最大秒数的整型值。当 timeout 为 0 是表示阻塞时间无限制。
    

## 传递队列

RPOPLPUSH 

    RPOPLPUSH source destination 
    
    原子性地返回并移除存储在 source 的列表的最后一个元素（列表尾部元素）， 
    并把该元素放入存储在 destination 的列表的第一个元素位置（列表头部）。
    如果 source 不存在，那么会返回 nil 值，并且不会执行任何操作。
    如果 source 和 destination 是同样的，那么这个操作等同于移除列表最后一个元素并且把该元素放在列表头部，
    所以这个命令也可以当作是一个旋转列表的命令。





# 结构 原理


链表

encoding
ziplist quicklist linkedlist

redis对外的数据结构类型list的底层是quicklist


# 示例

    125:0>lpush mylist 1 2  3 4 
    "4"
    125:0>lrange mylist 0 -1
     1)  "4"
     2)  "3"
     3)  "2"
     4)  "1"
    125:0>lrange mylist 0 -2
     1)  "4"
     2)  "3"
     3)  "2"
    125:0>ltrim mylist 0 -2
    "OK"
    125:0>lrange mylist 0 -1
     1)  "4"
     2)  "3"
     3)  "2"
    125:0>lpush mylist 5
    "4"
    125:0>lrange mylist 0 -1
     1)  "5"
     2)  "4"
     3)  "3"
     4)  "2"




