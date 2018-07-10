

# 1 概述

    整数类型用于保存整数

  
# 2 类型
      
    类型         存储位数             最值(有符号)        十进制位数     
    TINYINT       8                             127     3     
    SMALLINT      16                         32 767     5
    MEDIUMINT     24                      8 388 607     7 
    INT           32                  2 147 483 647     10  
    BIGINT        64      9 223 372 036 854 775 807     19      
    



# 3 属性

## 3.1 UNSINGED

作用

    UNSINGED属性，表示不允许负值，
    使正数的上限提高一倍，但是存储空间一样，性能一样。
    
    默认是有符号的,
    无符号数字不能表示负数,容易引起奇怪的问题

注意

    无符号相减产生负数越界问题
    
    mysql> create table iu (i1 int unsigned ,i2 int unsigned);
    mysql> insert into iu select 1 ,2 ;
    mysql> select * from iu;
    +------+------+
    | i1   | i2   |
    +------+------+
    |    1 |    2 |
    +------+------+
    
    mysql> select i1 - i2 from iu;
    ERROR 1690 (22003): 
    BIGINT UNSIGNED value is out of range in '(`test1`.`iu`.`i1` - `test1`.`iu`.`i2`)'
    
    
    mysql> set sql_mode='no_unsigned_subtraction';
    mysql> select i1 - i2 from iu;
    +---------+
    | i1 - i2 |
    +---------+
    |      -1 |
    +---------+


 
## 3.4 ZEROFILL
 
作用
   
    定义整数类型时,可以设置显示宽度
    例如int(N) zerofill 其中的N是显示宽度下限
    
    当存储的数字长度 < N时，用数字0填充左边，直至补满长度N
    当存储数字的长度超过N时，按照实际存储的数字显示
    
    设置了ZEROFILL之后,会默认被设置为UNSIGNED
    
示例

    create table intt (i1 int(4),i2 int(4) zerofill );
    mysql> desc intt;
    +-------+--------------------------+------+-----+---------+-------+
    | Field | Type                     | Null | Key | Default | Extra |
    +-------+--------------------------+------+-----+---------+-------+
    | i1    | int(4)                   | YES  |     | NULL    |       |
    | i2    | int(4) unsigned zerofill | YES  |     | NULL    |       |
    +-------+--------------------------+------+-----+---------+-------+

    mysql> insert into intt select 1 ,1;
    
    mysql> select * from intt;
    +------+------+
    | i1   | i2   |
    +------+------+
    |    1 | 0001 |
    +------+------+

    
# 6 运用

    
    主键的自增值最好使用BIGINT
   
    整数计算一般使用64位BIGINT整数，
    所以在代码中接收类似count(id)的结果时，要定义成BigInteger,不然会报错,？？
    
    
 
   