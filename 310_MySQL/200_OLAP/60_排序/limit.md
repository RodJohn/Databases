基础功能
作用

    LIMIT 子句可以被用于限制 SELECT 语句返回指定的记录数。
    只是限制条数 无序
语法

SELECT * FROM table  
LIMIT [offset,] rows | rows OFFSET offset
解释

LIMIT 接受一个或两个数字参数。参数必须是一个整数常量。
如果给定两个参数，第一个参数指定第一个返回记录行的偏移量，第二个参数指定返回记录行的最大数目。
初始记录行的偏移量是 0(而不是 1). 
如果给定一个参数,则表示偏移量为0.
举例

SELECT * FROM table LIMIT 5,10;  
// 检索记录行 6-15
SELECT * FROM table LIMIT 95,-1; 
// 检索记录行 96-last.
//为了检索从某一个偏移量到记录集的结束所有的记录行，可以指定第二个参数为 -1： 
SELECT * FROM table LIMIT 5;     
//检索前 5 个记录行.
优化
问题
在数据量比较大(几百万)的表中 
但是随着id越老越大，IO操作越来越多，需要进一步优化

例如

select ename,job from emp limit 1000000,20
#
limit常用于分页处理，时常会伴随order by从句使用，因此大多时候会使用Filesorts这样会造成大量的io问题 
1.使用有索引的列或主键进行order by操作

2.记录上次返回的主键，在下次查询时使用主键过滤 
即将：select film_id,description from sakila.film order by film_id limit 50,5; 
改为：select film_id,description from sakila.film where file_id >55 and film_id<=60 order by film_id limit 1,5; 
使用这种方式有一个限制，就是主键一定要顺序排序和连续的，如果主键出现空缺可能会导致最终页面上显示的列表不足5条，解决办法是附加一列，保证这一列是自增的并增加索引就可以了

limit常用于分页处理，时常伴随order by 从句使用，因此大多时候会使用 
Filesorts这样会造成大量的IO问题

select film_id,description from sakila.film order by title limit 50,5;

优化策略1:使用有索引的列或主键进行order by操作 
select film_id,description from sakila.film order by film_id limit 50,5;

但是随着id越老越大，IO操作越来越多，需要进一步优化

优化策略2:记录赏赐返回的主键，在下次查询时使用主键过滤 
select file_id,description from sakila.film where film_id>55 and 
film_id <=60 order by film_id limit 1,5

但是这种优化有个缺点就是要求ID连续的，如果ID不连续的话，会有问题，可以创建连续自增的列来实现

参考
参考和摘抄至http://blog.csdn.net/JavaMoo/article/details/78576363