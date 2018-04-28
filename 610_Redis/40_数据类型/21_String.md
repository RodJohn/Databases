

# 1 字符串类型

特点

    能存储任何形式的字符串，包括二进制数据。
    数据的最大容量是512MB。

用法        
        
    键存在判断
    数值计算,返回计算结果
    字符串操作 
    作为其他类型的基础类型   


# 2 命令

## 2.1 值操作

SET

    SET key value 
        key不存在时,插入键值对;
        key存在时,修改值;
    SETNX key value
        key不存在时,插入键值对;否则报错
    SET key value XX
        key存在时,修改值;否则报错
    
    MSET key value [key value]
        set的批量原子操作
    MSETNX key value [key value]
        setnx的批量原子操作
        (有任意一个键存在,都会失败)
        
GET        
        
    GET key 
        获取指定 key 的值。
        
    MGET key [key]
        批量获取指定 key 的值。

GETSET
        
    GETSET key value
        将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
        如果可以不存在则报错    

## 2.2 字符串操作

    APPEND key value
        尾部追加字符串
        返回字符串长度
    substr(key, start, end)：
        返回名称为key的string的value的子串


## 2.3 数值操作
  
要求
   
    值是数值类型
    值是有符号64位
    
特点
    
    原子操作
    操作完返回运算结果
    
操作
        
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


## 2.4 二进制操作

好处

    节省空间
    
操作

        
        
        
    

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
        
        
        
    字符串  整数 浮点数  二进制    