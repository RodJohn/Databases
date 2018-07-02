
# 概述
作用

    使用explain命令查看SQL语句执行计划
    不一定完全真实
    
缺点

    

语法
{EXPLAIN | DESCRIBE | DESC}
    [explain_type]
    {explainable_stmt | FOR CONNECTION connection_id}

explain_type: {
    EXTENDED| PARTITIONS| FORMAT = format_name
}

format_name: {
    TRADITIONAL| JSON
}

explainable_stmt: {
    SELECT statement| DELETE statement| INSERT statement| REPLACE statement| UPDATE statement
}
EXTENDED:多了一列 filtered 
PARTITIONS:多了一列partitions 
FORMAT = JSON:默认就为Traditional

id
SELECT的唯一标识，这是查询中SELECT的序号，唯一。
select_type
表示查询中每个select子句的类型（简单 、复杂）
参考值

SIMPLE：查询中不包含子查询或者UNION
PRIMARY：查询中若包含任何复杂的子部分，最外层查询则被标记为PRIMARY
SUBQUERY：在SELECT或WHERE列表中包含了子查询，该子查询被标记为SUBQUERY
DERIVED：在FROM列表中包含的子查询被标记为DERIVED（衍生）。若UNION包含在  FROM子句的子查询中，外层SELECT将被标记为DERIVED
UNION：若第二个SELECT出现在UNION之后，则被标记为UNION
UNION RESULT：从UNION表获取结果的SELECT被标记为UNION RESULT
table
查询的表
DERIVED：在FROM列表中包含的子查询被标记为DERIVED（衍生）。
partitions
查询调用到的分区信息
type
其中type是重要的列，以下从最好到最差：
const：是一个常数查找，一般是主键和唯一索引查找
eq_reg：主键和唯一索引的范围查找
ref：连接的查找，一般一个表是基于某一个索引的查找
range：表示使用索引范围查询, 通过索引字段范围获取表中部分数据记录. 这个类型通常出现在 =, <>, >, >=, <, <=, IS NULL, <=>, BETWEEN, IN() 操作中. 
当 type 是 range 时, 那么 EXPLAIN 输出的 ref 字段为 NULL, 并且 key_len 字段是此次查询中使用到的索引的最长的那个.
index：基于索引的扫描
all：基于表扫描
possible_keys
指示了MySQL可以选择的索引，但是有可能不会使用。
key
MySQL真正使用的索引
key_len
MySQL决定用的索引的长度，和key对应
使用的索引的长度，在不损失精确性的情况下，长度越短越好
ref
将哪些列或常量与key列中命名的索引进行比较
rows
执行查询需要扫描的行数，预估值。
filtered
将被表条件过滤掉数据的百分比的预估值。
Extra
额外信息

 Using where
Distinct:MySQL发现第1个匹配行后,停止为当前的行组合搜索更多的行。 
Not exists:MySQL能够对查询进行LEFT JOIN优化,发现1个匹配LEFT JOIN标准的行后,不再为前面的的行组合在该表内检查更多的行。 
range checked for each record (index map: #):MySQL没有发现好的可以使用的索引,但发现如果来自前面的表的列值已知,可能部分索引可以使用。 
Using filesort:文件排序，需要利用额外的空间。 
Using index:从只使用索引树中的信息而不需要进一步搜索读取实际的行来检索表中的列信息。 
Using temporary:需要用临时表来容纳结果。 
Using where:WHERE 子句用于限制哪一个行匹配下一个表或发送到客户。 
Using sort_union(…), Using union(…), Using intersect(…):这些函数说明如何为index_merge联接类型合并索引扫描。 
Using index for group-by:类似于访问表的Using index方式,Using index for group-by表示MySQL发现了一个索引, 
可以用来查 询GROUP BY或DISTINCT查询的所有列,而不要额外搜索硬盘访问实际的表。 
using filesort：mysql需要进行额外的步骤来发现如果对返回的行排序。它根据连接类型以及存储排序键值和匹配条件的全部行的行指针来排序全部行 
using temporary：MySQL需要创建一个临时表来存储结果，这通常发生在对不同的列集进行order by上，而不是group by 上 
或者是union时候

当我们的执行计划所执行的结果的extra中如果出现Using temporary或者Using filesort时，这说明我们的sql语句就需要进行优化了。
对于Using temporary，大家并不陌生，当我们的查询涉及多张表时，需要将查询结果放入第三张临时表中来存放。这样势必会降低我们的查询效率，
所以当遇到extra中为Using temporary时，也许就是我们应该优化的时候了。
需要优化
extra 中出现了 Using temporary Using filesort

参考
http://blog.csdn.net/luzhenyu111/article/details/78725533



explain extended 

show warnings;
得到具体的优化器 执行方案

