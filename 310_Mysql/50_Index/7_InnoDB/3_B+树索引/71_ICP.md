

# 1 概述

定义

    Index Condition Pushdown (ICP) 是mysql使用索引从表中检索行数据的一种优化方式。

原理

    禁用ICP，存储引擎会通过遍历索引定位基表中的行，然后返回给MySQL Server层，再去为这些数据行进行WHERE后的条件的过滤。
    
    开启ICP，如果部分WHERE条件能使用索引中的字段，MySQL Server 会把这部分下推到存储引擎层，存储引擎通过索引过滤，把满足的行从表中读取出。
    ICP能减少引擎层访问基表的次数和MySQL Server 访问存储引擎的次数。

 

ICP的目标是减少从基表中全纪录读取操作的数量，从而降低IO操作

对于InnoDB表，ICP只适用于辅助索引。


## 设置

参数

    用optimizer_switch的index_condition_pushdown来控制ICP优化。

查询    
    
    Select @@optimizer_switch;
    默认开启    

设置

    SET  @@optimizer_switch='index_condition_pushdown=on';    



#辅助索引INDEX (zipcode, lastname, firstname).

SELECT * FROM peopleWHERE zipcode='95054'AND lastname LIKE '%etrunia%'AND address LIKE '%Main Street%';

People表有个二级索引INDEX (zipcode, lastname, firstname),用户只知道某用户的zipcode,和大概的lastname、address,此时想查询相关信息。

若不使用ICP：则是通过二级索引中zipcode的值去基表取出所有zipcode='95054'的数据，然后server层再对lastname LIKE '%etrunia%'AND address LIKE '%Main Street%';进行过滤

若使用ICP：则lastname LIKE '%etrunia%'AND address LIKE '%Main Street%'的过滤操作在二级索引中完成，然后再去基表取相关数据


当使用ICP优化时，执行计划的Extra列显示Using indexcondition提示



