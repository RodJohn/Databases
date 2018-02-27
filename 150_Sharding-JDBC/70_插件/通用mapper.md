
http://blog.csdn.net/erhei0317/article/details/52693712
http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md


源码
https://github.com/abel533
https://git.oschina.net/free/Mapper



插入返回主键到对象中

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



		team.setState(State.enable);
		team.setUpdateBy(userId);
		team.setUpdateTime(new Date());
		Integer insertCount = teamMapper.insertSelective(team);
		Assert.notZero(num, message);(1, insertCount, "创建团队失败");
返回值为--2147482646  不知道为什么


属性==空字符串的时候 也不能更新   team的edit     就是长度为0的空字符串


<select id="getAllStudents1" resultMap="studentMap">  
    select * from mybatis_Student  
</select>  

public List<Student> getAllStudents1(RowBounds rowBounds);  

    List<Student> list = mapper.getAllStudents1(new RowBounds(limit* (pageIndex-1) ,limit));  




http://git.oschina.net/free/Mybatis_PageHelper

