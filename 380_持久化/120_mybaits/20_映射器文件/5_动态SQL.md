动态SQL

ognl
MyBatis中可以使用OGNL的地方有两处：
动态SQL表达式中
${param}参数中

参考资料
http://blog.csdn.net/isea533/article/details/50061705

简单的表达式
非0的值为true  0为false
e1 or e2
e1 and e2
e1 == e2,e1 eq e2
e1 != e2,e1 neq e2
e1 lt e2：小于
e1 lte e2：小于等于，其他gt（大于）,gte（大于等于）
e1 in e2
e1 not in e2
e1 + e2,e1 * e2,e1/e2,e1 - e2,e1%e2
!e,not e：非，求反

e.method(args)调用对象方法
e.property对象属性值
e1[ e2 ]按索引取值，List,数组和Map
@class@method(args)调用类的静态方法
@class@field调用类的静态字段值

例子
<if test="name != null and name != ''" >
		name like '${'%' + name + '%'}'
</if>


if/choose

if  
  条件符合就添加
 要考虑他是ognl的if,不是Java
  要考虑全部条件都不符合的情况

  <select id="searchStudents" resultMap="stuMap" parameterType="map">  
        select * from mybatis_student where 1=1  
        <if test="name!=null">  
            and name like #{name}  
        </if>  
        <if test="age!=null">  
            and age=#{age}  
        </if>  
    </select> 

实现insert的动态

choose
选择符合条件的第一个,或者使用默认值

<select id="findActiveBlogLike"  resultType="Blog">  
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’  
  <choose>  
    <when test="title != null">  
      	 AND title like #{title}  
    </when>  
    <when test="author != null and author.name != null">  
        AND author_name like #{author.name}  
    </when>  
    <otherwise>  
        AND featured = 1  
    </otherwise>  
  </choose>  
</select>  


where/set/trim

where
<where>元素只有在其内部标签有返回内容时才会在动态语句上插入WHERE条件语句。
如果WHERE子句以AND或者OR打头，则打头的AND或OR将会被移除。

<select id="findActiveBlogLike"   resultType="Blog">  
  SELECT * FROM BLOG  
  <where>  
      <if test="state != null">  
        state = #{state}  
      </if>  
  </where>  
</select>  

set
如果<if>条件返回了任何文本内容，<set>将会插入set关键字和其文本内容，并且会剔除将末尾的 “，”。
考虑到都不存在的情况,最好添加 id = #{id}

<update id="updateAuthorIfNecessary">  
  update Author  
    <set>  
      <if test="username != null">username=#{username},</if>  
      <if test="password != null">password=#{password},</if>  
      <if test="email != null">email=#{email},</if>  
      <if test="bio != null">bio=#{bio}</if>  
      id = #{id}
    </set>  
  where id=#{id}  
</update>  


trim
属性:
   　 prefix：当trim元素包含内容的时候,添加该值作为前缀
　    suffix：当trim元素包含内容的时候,添加该值作为后缀
　　prefixOverrides：当指定内容是语句的前缀时,会被去掉
　　suffixOverrides：当指定内容是语句的后缀时,会被去掉

trim替代where 
最好直接使用where
否则prefix="AND  |OR  ",不然会 匹配到or开始的单词

<select id="searchStudents3" resultMap="stuMap" parameterType="map">  
    select * from mybatis_student   
    <trim prefix="WHERE" prefixOverrides="AND  | OR  ">  
        <if test="name!=null">  
            and name like #{name}  
        </if>  
        <if test="age!=null">  
            and age=#{age}  
        </if>  
    </trim>  
</select>  


foreach
你可以将任何可迭代对象（如列表、集合等）和任何的字典或者数组对象传递给foreach作为集合参数。
但是要考虑集合为空的情况

foreach属性
collection
即将被循环的对象 	
List<?>对象默认用list代替作为键，数组对象有array代替作为键，Map对象没有默认的键。
当然在作为入参时可以使用@Param("keyName")来设置键，设置keyName后，list,array将会失效。
item	
循环体中的临时对象。
支持属性的点路径访问，如item.age,item.info.details。
具体说明：在list和数组中是其中的对象，在map中是value。
separator	
元素之间的分隔符，例如在in()的时候，separator=","会自动在元素中间用“,“隔开，避免手动输入逗号导致sql错误，如in(1,2,)这样。该参数可选。
open	
foreach代码的开始符号，一般是(和close=")"合用。常用在in(),values()时。该参数可选。
close	
foreach代码的关闭符号，一般是)和open="("合用。常用在in(),values()时。该参数可选。
index	
在list和数组中,index是元素的序号，在map中，index是元素的key，该参数可选。


普通举例
<select id="selectPostIn" resultType="domain.blog.Post">  
  SELECT *  
  FROM POST P  
  WHERE ID in  
  <foreach item="item" index="index" collection="list"  
      open="(" separator="," close=")">  
        #{item}  
  </foreach>  
</select> 


map举例
Map类型没有默认的map，所以不能直接写collection="map",如果这么写，需要保证传入的Map参数有@Param("map")注解。
<select id="sel_key_cols" resultType="int">  
	select count(*) from key_cols where  
	<foreach item="item" index="key" collection="map"  
		open="" separator="AND" close="">${key} = #{item}</foreach>  
</select>  


bind
bind 元素可以从 OGNL 表达式中创建一个变量并将其绑定到上下文。比如：

<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>



使用注意

动态语句的中的坑
0.if判断
string判断相等要使用双引号“”,使用单引号会被解析成char类型的的数字,例如 id = "abc"
数字0 相当于false ,非0为true
1.使用foreach   
需要先判断对应的集合是否存在,如果不存在的话该怎么办
比如使用嵌套  1.使用choose判断集合是否存在,2.再使用foreah
2.使用set
为了避免所有的设置属性都不存在,的最好加上 set id = #{id}之类的
3.不使用trim代替where
避免prefix="AND  |OR  "
4.where使用中
要考虑全部的条件都不存在的情况,特别是在delete
5.like
like需要拼接占位符可以使用 concat
同时为了避免null,"","  "可以在ignl表达式中使用类的静态方法(@class@method(args))


参考资料
http://www.jianshu.com/p/91ed365c0fdd
