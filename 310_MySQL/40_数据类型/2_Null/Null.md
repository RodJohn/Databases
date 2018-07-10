

null


    尽量避免Null--- 以前我们表中包含很多为NULL的列，即使不需要保存Null,通常情况下最好指定为Not NULL,除非真的需要。
    原因：1：优化需要，对于查询包含为NULL的列，会使索引，索引统计和值比较复杂，NULL会使用更多的存储空间，
    当NULL列被索引时需要一个额外的字节。
    2：程序问题，如果你domain中的变量没有定义成包装类，查询时不能将NULL保存到数据。所以这里同时建议程序中使用Integer 代替 int 在domain中。
 
 MySQL 官网文档： 
 NULL columns require additional space in the rowto record whether their values are NULL. For MyISAM tables, each NULL columntakes one bit extra, rounded up to the nearest byte. 
 Mysql难以优化引用可空列查询，它会使索引、索引统计和值更加复杂。可空列需要更多的存储空间，还需要mysql内部进行特殊处理。可空列被索引后，每条记录都需要一个额外的字节，还能导致MYisam 中固定大小的索引变成可变大小的索引。
 
 所有使用NULL值的情况，都可以通过一个有意义的值的表示
 
 null
 存储
 可为Null 列需要更多的存储空间：需要一个额外字节作为判断是否为 NULL 的标志位
 
 null查询
 “null” 表示什么也不是， 用“=、>、< …” 所有的判断，结果都是false，所有只能用 is null进行判断。默认情况下，推荐使用 IS NOT NULL去判断，因为SQL默认情况下对。”！= Null”的判断会永远返回0行，但没有语法错误。如果你一定想要使用”！= Null”来判断，需要加上这个语句：“set ANSI_NULLS off”这时你会发现“IS NOT NULL” 和 “!= null” 是等效的。
 
 缺点
 效率
 NULL 其实并不是空值，而是要占用空间，所以mysql在进行比较的时候，NULL 会参与字段比较，所以对效率有一部分影响，而且B树索引时不会存储NULL值的，所以如果索引的字段可以为NULL，索引的效率会下降很多。
 
 影响查询
 NOT IN、!= 等负向条件查询在有NULL值的情况下返回永远为空结果，查询容易出错
 
 select user_name from table_2 where user_name not in (select user_name from table_3 where id!=1)
 
 select * from table_3 where name != ‘zhaoliu_2_1’
 
 影响字符拼接
 如果在两个字段进行拼接：比如题号+分数，首先要各字段进行非null判断，否则只要任意一个字段为空都会造成拼接的结果为null。 select CONCAT(“1”,null) from dual; – 执行结果为null。
 
 影响Count
 如果有 Null column 存在的情况下，count(Null column)需要格外注意，null 值不会参与统计。
 
 影响索引
 NULL值到非NULL的更新无法做到原地更新，更容易发生索引分裂，从而影响性能
 
 B树索引时不会存储NULL值的，所以如果索引的字段可以为NULL，索引的效率会下降很多。
 
 2、单列索引不存null值，复合索引不存全为null的值，如果列允许为null，可能会得到“不符合预期”的结果集 – 如果name允许为null，索引不存储null值，结果集中不会包含这些记录。所以，请使用not null约束以及默认值。 
 select * from table_3 where name != ‘zhaoliu_2_1’ –
 
 参考
 https://www.imooc.com/article/21588?block_id=tuijian_wz
 
 