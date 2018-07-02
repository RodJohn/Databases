基础
作用

设定结果集按照那个字段,那种方式进行排序
语句

ORDER BY field1, [field2...] [ASC [DESC]]
说明

1. ASC==升序   DESC==降序   默认是升序
2. 可以指定多个排序条件
3. 默认是使用主键进行排序
举例

原理
MySQL中order by的原理

索引排序
当查询语句的 order BY 条件和查询的执行计划中所利用的 Index 的索引键（或前面几个索引键）完全一致,且索引访问方式为 rang,ref 或者 index 的时候,MySQL 可以利用索引顺序而直接取得已经排好序的数据。这种方式的 order BY 基本上可以说是最优的排序方式了,因为 MySQL 不需要进行实际的排序操作。需要注意的是使用索引排序也有很多限制。这个在后文中中解释。
内存/文件排序
在MySQL中filesort 的实现算法有两种:

1) 双路排序:
是首先根据相应的条件取出相应的排序字段和可以直接定位行数据的行指针信息，然后在sort buffer 中进行排序。完成排序之后再次通过行指针信息取出所需的Columns。
2) 单路排序:
是一次性取出满足条件行的所有字段，然后在sort buffer中进行排序。
算法选择

在 MySQL4.1 版本之前只有第一种排序算法,第二种算法是从MySQL4.1开始的改进算法,主要目的是为了减少第一次算法中需要两次访问表数据的IO操作,将两次变成了一次,但相应也会耗用更多的 sort buffer 空间。典型的以空间换时间的优化方式。
当然,MySQL4.1开始的以后所有版本同时也支持第一种算法,MySQL主要通过比较系统参数 max_length_for_sort_data的大小和Query语句所取出的字段类型大小总和来判定需要使用哪一种排序算法。如果max_length_for_sort_data更大,则使用第二种优化后的算法,反之使用第一种算法。所以如果希望 order BY 操作的效率尽可能的高,需要注意max_length_for_sort_data参数的设置。
由于没有可以利用的有序索引取得有序的数据,MySQL需要通过相应的排序算法,将取得的数据在sort_buffer_size系统变量所设置大小的排序区进行排序,这个排序区是每个Thread 独享的,所以说可能在同一时刻在 MySQL 中可能存在多个 sort buffer 内存区域。

优化
当无法避免排序操作时,又该如何来优化呢？很显然,优先选择第一种using index 的排序方式，在第一种方式无法满足的情况下，尽可能让 MySQL 选择使用第二种单路算法来进行排序。这样可以减少大量的随机IO操作,很大幅度地提高排序工作的效率。 
1 加大 max_length_for_sort_data 参数的设置 
在 MySQL 中,决定使用老式排序算法还是改进版排序算法是通过参数 max_length_for_ sort_data 来决定的。当所有返回字段的最大长度小于这个参数值时,MySQL 就会选择改进后的排序算法,反之,则选择老式的算法。所以,如果有充足的内存让MySQL 存放须要返回的非排序字段,就可以加大这个参数的值来让 MySQL 选择使用改进版的排序算法。

2 去掉不必要的返回字段

当内存不是很充裕时,不能简单地通过强行加大上面的参数来强迫 MySQL 去使用改进版的排序算法,否则可能会造成 MySQL 不得不将数据分成很多段,然后进行排序,这样可能会得不偿失。此时就须要去掉不必要的返回字段,让返回结果长度适应 max_length_for_sort_data 参数的限制。
3 增大 sort_buffer_size 参数设置 
这个值如果过小的话,再加上你一次返回的条数过多,那么很可能就会分很多次进行排序,然后最后将每次的排序结果再串联起来,这样就会更慢,增大 sort_buffer_size 并不是为了让 MySQL选择改进版的排序算法,而是为了让MySQL尽量减少在排序过程中对须要排序的数据进行分段,因为分段会造成 MySQL 不得不使用临时表来进行交换排序。 
但是这个值不是越大越好： 
1 Sort_Buffer_Size 是一个connection级参数,在每个connection第一次需要使用这个buffer的时候,一次性分配设置的内存。 
2 Sort_Buffer_Size 并不是越大越好,由于是connection级的参数,过大的设置+高并发可能会耗尽系统内存资源。 
3 据说Sort_Buffer_Size 超过2M的时候,就会使用mmap() 而不是 malloc() 来进行内存分配,导致效率降低。

索引和排序
t1表存在索引key1(key_part1,key_part2),key2(key2)

可以利用索引避免排序的SQL
SELECT * FROM t1 ORDER BY key_part1,key_part2; 
SELECT * FROM t1 WHERE key_part1 = constant ORDER BY key_part2; 
SELECT * FROM t1 WHERE key_part1 > constant ORDER BY key_part1 ASC; 
SELECT * FROM t1 WHERE key_part1 = constant1 AND key_part2 > constant2 ORDER BY key_part2;

不能利用索引避免排序的SQL
//排序字段在多个索引中，无法使用索引排序 
SELECT * FROM t1 ORDER BY key_part1,key_part2, key2;

//排序键顺序与索引中列顺序不一致，无法使用索引排序 
SELECT * FROM t1 ORDER BY key_part2, key_part1;

//升降序不一致，无法使用索引排序 
SELECT * FROM t1 ORDER BY key_part1 DESC, key_part2 ASC;

//key_part1是范围查询，key_part2无法使用索引排序 
SELECT * FROM t1 WHERE key_part1> constant ORDER BY key_part2;

加权排序
也就是简单搜索

原理 
按需构建虚拟列,并排序

加权排序 
select * from aname 
order by ((name1 = ‘李’) + (name2 = ‘李’)*3 +(name3 = ‘李’)*4) desc

把null 放在最后 
SELECT vcenter_ip, status, sla_id FROM vm_list ORDER BY sla_id is NULL, sla_id = 0, sla_id ASC;

参考 
http://blog.csdn.net/xingxinglaile/article/details/39496755

其他
多字段排序 
SELECT * FROM table_name order by filed1 desc ,filed2 
先按照filed1降序,filed1相同的情况下,按照filed2升序

分条件排序 
SELECT * FROM log ORDER BY 
CASE WHEN status=1 THEN time_1 END DESC, 
CASE WHEN status=2 THEN time_2 END ASC;

指定排序 
select id from t_learn_category ORDER BY FIELD( id,1 ,6 ) DESC ,id 
先id按照 1,6 倒序 ,然后按照id升序

排序号 
set @rownum=0; 
SELECT @rownum:=@rownum+1 AS rownum, table_name.* FROM table_name

未处理(0)、正在处理(2)、处理成功(1)、处理失败(4) 
select * from tab_task b order by (CASE b.deal_status WHEN 0 THEN 10 WHEN 2 THEN 30 WHEN 1 THEN 40 WHEN 4 THEN 20 ELSE 40 END) ASC,b.add_time desc

锁定排序

如果第一列中有些值为 nulls 时，情况也是这样的。

参考
排序原理 
http://blog.itpub.net/22664653/viewspace-2143086/ 
http://blog.itpub.net/22664653/viewspace-1259133/ 
http://www.open-open.com/lib/view/open1475910384210.html

加权排序 
http://blog.csdn.net/xingxinglaile/article/details/39496755