

# insert   

配置
一个INSERT SQL语句可以在<insert>元素在映射器XML配置文件中配置

<insert id="insertStudent" >  
    INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL, PHONE) VALUES(#{studId},#{name},#{email},#{phone})  
</insert> 

xml属性 
id 	命名空间中的唯一表示
parameterType 	 传入参数  mybatis 可以推断出来,可以不写
flashCashe	是否刷新一级和二级缓存   默认为true
statementType	语句类型 默认prepared ,可选statement,callable 
timeot  等待数据库的时间

调用/返回值
int count =sqlSession.insert("com.mybatis3.mappers.StudentMapper.insertStudent", student); 
sqlSession.insert()方法返回执行INSERT语句后所影响的行数。


设置主键

自增长主键
<insert id="insertStudent"  useGeneratedKeys="true" keyProperty="id" keyColumn="id">  
    INSERT INTO STUDENTS(NAME, EMAIL, PHONE)  VALUES(#{name},#{email},#{phone})  
</insert>  

设置了useGeneratedKeys = true 以后，
mybatis会使用JDBC的useGeneratedKeys方法来取出由insert以后数据库生成的主键并赋值给 keyProperty对应属性

序列主键
<insert id="insertStudent" >  
    <selectKey keyColumn="studId" keyProperty="studId" resultType="int" order="BEFORE">  
SELECT ELEARNING.STUD_ID_SEQ.NEXTVAL FROM DUAL
    </selectKey>  
    INSERT INTO STUDENTS(STUD_ID,NAME,EMAIL, PHONE) VALUES(#{studId},#{name},#{email},#{phone})  
</insert>  

序列主键中使用<selectKey>子元素来生成keyColumn值，并将值保存到Student对象的studId属性上。
oracle中,属性order设置为“before”表示MyBatis将取得序列的下一个值作为主键值，并且在执行INSERTSQL语句之前将值设置到studId属性上.
而在mysql中也可以使用 select LAST_INSERT_ID() 获取本连接最后一次插入的主键, 并赋给keyProperty ,所以order设置为after




# update

配置
UPDATE SQL语句可以在<update>元素在映射器XML配置文件中配置
<update id="updateStudent" >  
    UPDATE STUDENTS SET NAME=#{name}, EMAIL=#{email}, PHONE=#{phone}  WHERE STUD_ID=#{studId}  
</update>

xml属性


调用/返回值
int noOfRowsUpdated =  sqlSession.update("com.mybatis3.mappers.StudentMapper.updateStudent", student);  
sqlSession.update() 方法返回执行UPDATE语句之后影响的行数。


# delete


配置
DELETE SQL语句可以在<update>元素在映射器XML配置文件中配置
<delete id="deleteStudent" >  
   DELETE FROM STUDENTS WHERE STUD_ID=#{studId}  
</delete>  


调用/返回值
int noOfRowsDeleted =  sqlSession.delete("com.mybatis3.mappers.StudentMapper.deleteStudent", studId); 
sqlSession.delete()方法返回delete语句执行后影响的行数



SQL
可以被用来定义可重用的 SQL 代码段，可以包含在其他语句中。它可以被静态地(在加载参数) 参数化. 不同的属性值通过包含的实例变化