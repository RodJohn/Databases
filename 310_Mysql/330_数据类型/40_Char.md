

VARCHAR 

VARHCAR类型用来存储可变长字符串
它仅使用必要的空间，越短的字符串使用越少的空间，VARCHAR节省了存储空间




存储

    VARCHAR需要额外使用1或2个字节记录字符串的长度，
    列的最大长度小于等于255字节时，额外使用1一个字节表示长度，否则使用2个字节。
    比如VARCHAR(10)需要11个字节的存储空间，VARCHAR(1000)需要1002个字节的存储空间

    行格式

保存
    
    剔除末尾空格
    
    过长的存为blob

    
更新

    但是由于是变长，在UPDATE时需要做额外的工作，因为有可能更新使行变得比原来更长。 
    对于不同的引擎处理方式不一样
    innodb分裂页
    
适合情况
    
    下面这些情况适合使用VARCHAR：更新频率较少；
    存储的字符串值长度不确定且最大长度比平均长度大很多；
    使用了类似UTF-8这样的复杂字符集（一个英文字符等于一个字节，一个中文（含繁体）等于三个字节）。



# CHAR 

CHAR类型是定长的，
最多 255 
MySQL总是根据定义的字符串长度去分配空间。


保存

    CHAR值会根据需要采用空格进行填充来补长，
    值得注意的是MySQL取出CHAR会删除CHAR类型值的末尾空格。 
    
更新

    CHAR适合存储很短的字符串，或者所有值都接近同一个长度。
    对于经常UPDATE的数据，CAHR也比VARCHAR更合适，因为定长不容易产生碎片。
    
       
    



字符


char varchar
定义
char(n)     n的范围 0--255
varchar(n)  n的范围,0-255(5.0.3以前) ,0-65535之后()
n的表示      5.X以后表示的是字符.之前表示的是字节
存储
varchar需要额外的空间存储字段的实际长度
char/varchar对于超过长度的处理--截断
char/varchar对于未超过长度的处理


字节和字符
length:   是计算字段的长度一个汉字是算三个字符,一个数字或字母算一个字符
CHAR_LENGTH(str) 返回值为字符串str 的长度，长度的单位为字符

select id,name,length(name),char_length(name)  from test_varchar_utf8;

+----+--------------------------------------+--------------+-------------------+
| id | name                                 | length(name) | char_length(name) |
+----+--------------------------------------+--------------+-------------------+
|  1 | 12345678901                          |           11 |                11  |
|  3 | 你                                            |            3 |                 1   |
|  4 | 你好                                         |            6 |                 2   |
+----+--------------------------------------+--------------+-------------------+
查询
查询时CHAR列删除了尾部的空格,VARCHAR保持原样

mysql> CREATE TABLE vc (v VARCHAR(4), c CHAR(4));
mysql> INSERT INTO vc VALUES ('ab  ', 'ab  ');

mysql> SELECT CONCAT(v, '+'), CONCAT(c, '+') FROM vc;
+----------------+----------------+
| CONCAT(v, '+') | CONCAT(c, '+') |
+----------------+----------------+
| ab  +          | ab+            |
+----------------+----------------+


引擎
MyISAM 数据存储引擎和数据列：MyISAM数据表，最好使用固定长度(CHAR)的数据列代替可变长度(VARCHAR)的数据列。
MEMORY存储引擎和数据列：MEMORY数据表目前都使用固定长度的数据行存储，因此无论使用CHAR或VARCHAR列都没有关系。两者都是作为CHAR类型处理的。

InnoDB 存储引擎和数据列：建议使用 VARCHAR类型。
对于InnoDB数据表，**内部的行存储格式没有区分固定长度和可变长度列（所有数据行都使用指向数据列值的头指针）**，
因此在本质上，使用固定长度的CHAR列不一定比使用可变长度VARCHAR列简单。因而，主要的性能因素是数据行使用的存储总量。
由于CHAR平均占用的空间多于VARCHAR，因 此使用VARCHAR来最小化需要处理的数据行的存储总量和磁盘I/O是比较好的。
varchar text


区别
对于text字段，MySQL不允许有默认值。 varchar允许有默认值
存储
都需要额外地保存该字段的实际长度,叫overhead.

- varchar 小于255byte  1byte overhead
- varchar 大于255byte  2byte overhead
- tinytext 0-255 1 byte overhead
- text 0-65535 byte 2 byte overhead
- mediumtext 0-16M  3 byte overhead
overflow存储

- varchar(255+)存储上和text很相似
- text 是要要进overflow存储。 前768字节和原始的行存储在一块，多于768的行会存在和行相同的Page或是其它Page上。
- varchar 在MySQL内部属于从blob发展出来的一个结构，在早期版本中innobase中，也是768字节以后进行overfolw存储。
- 对于Innodb-plugin后： 对于变长字段处理都是20Byte后进行overflow存储



