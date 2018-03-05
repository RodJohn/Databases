# 概述

 JDBC Statement，CallableStatement和PreparedStatement接口定义了可用于发送SQL或PL/SQL命令，
 并从数据库接收数据的方法和属性。
它们还定义了有助于在Java和SQL数据类型的数据类型差异转换的方法。




# Statement 

## 创建

Statement stmt = conn.createStatement( );

## 执行


boolean execute (String SQL) ： 
如果可以检索到ResultSet对象，则返回一个布尔值true; 否则返回false。
使用此方法执行SQLDDL语句或需要使用真正的动态SQL，可使用于执行创建数据库，创建表的SQL语句等等。

int executeUpdate (String SQL): 
返回受SQL语句执行影响的行数。使用此方法执行预期会影响多行的SQL语句，例如:INSERT，UPDATE或DELETE语句。

ResultSet executeQuery(String SQL)：
返回一个ResultSet对象。 如使用SELECT语句一样。


# PreparedStatement

PreparedStatement接口扩展了Statement接口，它添加了比Statement对象更好一些优点的功能。

## 创建

String SQL = "Update Employees SET age = ? WHERE id = ?";
pstmt = conn.prepareStatement(SQL);

   
   	JDBC中的所有参数都由 ? 符号作为占位符，这被称为参数标记。 在执行SQL语句之前，必须为每个参数(占位符)提供值。
setXXX()方法将值绑定到参数，其中XXX表示要绑定到输入参数的值的Java数据类型。
 如果忘记提供绑定值，则将会抛出一个SQLException。
每个参数标记是它其顺序位置引用。第一个标记表示位置1，下一个位置2等等。


## 执行

执行
所有Statement对象与数据库交互的方法(a)execute()，(b)executeQuery()和(c)executeUpdate()
也可以用于PreparedStatement对象。 
但是，这些方法被修改为可以使用输入参数的SQL语句。


# PreparedStatement VS Statement

好处
防止注入

数据库缓存
使用PreparedStatement的好处是数据库会对sql语句进行预编译，
下次执行相同的sql语句时，数据库端不会再进行预编译了，而直接用数据库的缓冲区，提高数据访问的效率，如果sql语句只执行一次，以后不再复用。 


# CallableStatement

CallableStatement将用于执行对数据库存储过程的调用。





# 批处理
	
当你有10条SQL语句要执行时，一次向服务器发送一条SQL语句，这么做效率上很差！处理的方案是使用批处理，即一次向服务器发送多条SQL语句，然后由服务器一次性处理。
批处理只针对更新（增、删、改）语句，批处理没有查询什么事儿！


void addBatch(String sql)：添加一条语句到“批”中；
int[] executeBatch()：执行“批”中所有语句。返回值表示每条语句所影响的行数据；
void clearBatch()：清空“批”中的所有语句。

PreparedStatement的批处理有所不同，因为每个PreparedStatement对象都绑定一条SQL模板。所以向PreparedStatement中添加的不是SQL语句，而是给“?”赋值。

