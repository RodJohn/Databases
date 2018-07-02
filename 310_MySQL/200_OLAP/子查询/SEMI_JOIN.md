




# 概述

定义

    SEMI JOIN 等值连接
    
优化
    
    Oracle和都将SEMI JOIN转换为EXISTS语句,因此执行效率很低
    MySQL5.6默认开启   
    
参数    
    
    
# Table Pullout

作用
    
    如果内部表的结果是唯一的
    将子查询优化为连接查询     
    
    
# Duplicate Weedout

概述

    如果内部查询的结果不是唯一
    
    
    
# Materialization

概述

    如果子查询是独立子查询,\
    则优化器可以将独立子查询的结果单独填充到一张物化临时表中;.    