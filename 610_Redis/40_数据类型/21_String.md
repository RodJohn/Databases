

# 1 字符串类型


    能存储任何形式的字符串，包括二进制数据。
    数据的最大容量是512MB。


# 2 操作

## 2.1 值操作

    SET key value 
        设置指定 key 的值
    GET key 
        获取指定 key 的值。
        
    GETSET key value
        将给定 key 的值设为 value ，并返回 key 的旧值(old value)。

    SETNX key value
        只有在 key 不存在时设置 key 的值。
    SET key value XX
        只有键在已经存在的时候才可以设置成功

## 2.2 字符串操作

    APPEND key value
        尾部追加   
    substr(key, start, end)：
        返回名称为key的string的value的子串


## 2.3 数值操作
   
    当作原子计数器
       
    原子递增
    有符号64位
        
    INCR key
        将 key 中储存的数字值增一。
    INCRBY key increment
        将 key 所储存的值加上给定的增量值（increment） 。
    INCRBYFLOAT key increment
        将 key 所储存的值加上给定的浮点增量值（increment） 。
    DECR key
        将 key 中储存的数字值减一。
    DECRBY key decrement
        key 所储存的值减去给定的减量值（decrement） 。
        
        

# 结构

    对应的encoding 
    int
    raw
    embstr
    
    字符串对象保存各类类型值  用long保存的整数 浮点数  
    
    类型变换  
    int -->raw  append
    emstr -->raw  只读
    
    incrby decrby  数值计算  版本
        