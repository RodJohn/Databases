

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



text blob
区别
BLOB：采用二进制存储、没有字符集或排序规则
TEXT：采用字符方式存储、有字符集和排序规则    
空洞
text和blob在大量删除/更新以后会留下碎片空间,影响后续数据的性能
建议定期使用 optimize table 碎片整理 
搜索
使用合成索引或者前缀索引
设计
最好尽量避免使用这两类型如果可以、可以把BLOB货TEXT 列分离成单独的表  

长度问题

BLOB和TEXT都是为了存储很大的数据而设计的字符串数据类型，BLOB采用二进制存储，TEXT采用字符串存储。 
TEXT类型下有：TINYTEXT，SAMLLTEXT，TEXT，MEDIUMTEXT，LONGTEXT； 
BLOB类型下有：TINYBLOB，SAMLLBLOB，BLOB，MEDIUMBLOB，LONGBLOB。 
MySQL把BLOB和TEXT值当作一个独立的对象处理。存储引擎在存储时会对它们做特殊处理。当值太大时，InnoDB存储引擎会使用专门的“外部”存储区域来存储实际值，此时每个值在行内会存储一个1～4字节的指针，通过该指针指向外部存储区域的实际值。

