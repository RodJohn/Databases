
MyBatipse




MyBatipse

详细介绍
http://www.jianshu.com/p/4a309d9882ca
https://github.com/mybatis/mybatipse
安装
在Eclipse Marketplace中搜索MyBatipse安装即可
JavaEditor 的增强
给接口中的参数添加@Param注解
右键选中整个方法名,Ctrl+1,选择add Param
${}#{}中提示输入参数
支持在select/insert/update/delete注解语句中
#{}和${}   alt +/
@ResultMap注解的提示
中对resultMap的引用提示   alt +/
从接口导航到xml
按住Ctrl键,光标移到接口上,会出现open
XML 的增强
xml跳转接口
点击namespace
点击id
resultMap 
type   自动提示  		 alt +/
自动补充全部的映射	 alt +/
properties  自动提示   	 alt +/

语句
result Map  sql  select   自动提示  
alt+/
#{}和${}自动显示定义的参数
输入 ${   #{   alt+/
动态语句的判断  自动提示 Java 属性    
alt+/

系统
取消校验
因为会自动校验 引用的 接口 类属性,对通用mapper和
所以可以在 preference--validate中取消校验
MyBatis Nature
默认情况下，每一个XML文件的修改保存动作都会让mybatipse自动完成验证检查，但是如果你想让mybatipse在你对Java对象进行修改保存之后，
马上同步去验证XML文件的话，需要为项目添加mybatis nature。要添加mybatis nature，选择项目-->Configure-->Add Mybatis Nature即可：











