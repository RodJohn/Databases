
# 概述

ResultSet 接口表示数据库查询的结果集




# 结果集特点

con.createSttement()：生成的结果集：不滚动、不敏感、不可更新！ (正常人使用)
con.createStatement(int,int)：
第一个参数：
ResultSet.TYPE_FORWARD_ONLY：不滚动结果集；
ResultSet.TYPE_SCROLL_INSENSITIVE：滚动结果集，但结果集数据不会再跟随数据库而变化；
ResultSet.TYPE_SCROLL_SENSITIVE[没有数据库驱动会支持它！]：滚动结果集，但结果集数据会再跟随数据库而变化；
第二个参数：
CONCUR_READ_ONLY：结果集是只读的，不能通过修改结果集而反向影响数据库；
CONCUR_UPDATABLE：结果集是可更新的，对结果集的更新可以反向影响数据库。一般不用

# 结果集解析


查询结果ResultSet的结果是一个带有列名二维列表


获取行数据
next()方法使ResultSet的游标向下移动

获取列数据
String getString(int columnIndex)：获取指定列的String类型数据；
Object getObject(int columnIndex)：获取指定列的Object类型的数据	
参数columnIndex表示列的索引，列索引从1开始，而不是0
	
String getString(String columnName)：获取名称为columnName的列的String数据；
Object getObject(String columnName)：获取名称为columnName的列的Object数据；