参考资料

spring redis相关资料
http://shift-alt-ctrl.iteye.com/blog/1887370


https://docs.spring.io/spring-data/redis/docs/current/reference/html/
 
 
 
 
spring 封装了 RedisTemplate 对象来进行对redis的各种操作，它支持所有的 redis 原生的 api。
RedisTemplate中定义了对5种数据结构操作 
redisTemplate.opsForValue();//操作字符串 
redisTemplate.opsForHash();//操作hash 
redisTemplate.opsForList();//操作list 
redisTemplate.opsForSet();//操作set 
redisTemplate.opsForZSet();//操作有序set


#ValueOpration

	ValueOpration是用于处理String类型的存储数据
	可以是字符串、整数或者浮点数	
	对象和浮点数执行自增(increment)或者自减(decrement)	

#常用方法

###SET

	*set void set(K key, V value);
		存在更新，不存在新增
		默认存活时间-1,也就是永久
	multiSet void multiSet(Map<? extends K, ? extends V> m);
		批量设置
	set void set(K key, V value, long timeout, TimeUnit unit);
		指定时间设置
	set void set(K key, V value, long offset);
		从偏移量开始覆盖



###过期时间

	Long getExpire(K key)
		获取存活毫秒数
	*expire Boolean expire(K key, long timeout, TimeUnit unit)
		指定存活时间
	expire Boolean expireAt(K key, Date date)
		指定存活时间
	persist Boolean persist(K key)
		持久化存活时间为-1


###*特殊
	*setIfAbsent Boolean setIfAbsent(K key, V value);
		key不存在,设置成功,返回true
	getAndSet V getAndSet(K key, V value);
		设置键的字符串值并返回其旧值
	*increment Long/Double  increment(K key, long delta);
		对数字类型的值进行自增,并返回,否则报错
	append Integer append(K key, String value);
		如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为改字符串。


	
	
###查询
	*get V get(Object key);
	multiGet List<V> multiGet(Collection<K> key
		获取
	get String get(K key, long start, long end);
		从偏移量开始获取
	size Long size(K key);
		返回key所对应的value值得长度


###delete
	*delete void delete(K key)



#StringRedisTemplate
	
	StringRedisTemplate继承RedisTemplate,是一个方便使用的Template.
	
	两者的数据是不共通的；也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，RedisTemplate只能管理RedisTemplate中的数据,主要原因就是序列化的策略。
	
	SDR默认采用的序列化策略有两种，一种是String的序列化策略，一种是JDK的序列化策略。
	
	StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
	
	RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
StringRedisTemplate源码
```
public class StringRedisTemplate extends RedisTemplate<String, String> {

	/**
	 * Constructs a new <code>StringRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
	 * and {@link #afterPropertiesSet()} still need to be called.
	 */
	public StringRedisTemplate() {
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		setKeySerializer(stringSerializer);
		setValueSerializer(stringSerializer);
		setHashKeySerializer(stringSerializer);
		setHashValueSerializer(stringSerializer);
	}

	/**
	 * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
	 * 
	 * @param connectionFactory connection factory for creating new connections
	 */
	public StringRedisTemplate(RedisConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
		afterPropertiesSet();
	}

	protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
		return new DefaultStringRedisConnection(connection);
	}
}
```


RedisTemplate配置
```
@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
```

#SpringBoot

###依赖

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-redis</artifactId>
	</dependency>

###配置

	application.properties
	# REDIS (RedisProperties)
	# Redis数据库索引（默认为0）
	spring.redis.database=6
	# Redis服务器地址
	spring.redis.host=127.0.0.1
	# Redis服务器连接端口
	spring.redis.port=6379
	# Redis服务器连接密码（默认为空）
	spring.redis.password=
	# 连接池最大连接数（使用负值表示没有限制）
	spring.redis.pool.max-active=8
	# 连接池最大阻塞等待时间（使用负值表示没有限制）
	spring.redis.pool.max-wait=-1
	# 连接池中的最大空闲连接
	spring.redis.pool.max-idle=8
	# 连接池中的最小空闲连接
	spring.redis.pool.min-idle=0
	# 连接超时时间（毫秒）
	spring.redis.timeout=0



###获取operation

	1.注入Template
		@Autowired private StringRedisTemplate stringRedisTemplate;
		@Autowired	private RedisTemplate<String, String> redisTemplate;
		
	
	2.获取operation
		ValueOperations<String, String> opsForValue1 = stringRedisTemplate.opsForValue();
			获取通用操作器
		BoundValueOperations<String, String> opsForValue1 = stringRedisTemplate.boundValueOps("Taaa");
			获取绑定键的操作器,在下面的操作中就不用指定key值

###范例
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringBootTest(classes= MainApplication.class)
	public class TestUserTemplate {
		
		@Autowired private RedisTemplate<String, User> redisTemplate;
		
		private ValueOperations<String, User> opsForValue = null;
		
		@Before
		public void before() {
			opsForValue = redisTemplate.opsForValue();
	    }
		
		@Test
		public void upsert() throws Exception {
			User user = new User("超人", 20);
			opsForValue.set(user.getUsername(), user);
	    }
		
		
		@Test
		public void append() throws Exception {
			User user = opsForValue.get("超人");
			System.out.println(user);
		}




#SetOperations

Redis的Set是string类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。

Redis 中 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。

##方法

	Long add(K key, V... values);
	无序集合中添加元素，返回添加个数

	Long remove(K key, Object... values);
	移除集合中一个或多个成员
	
	Boolean isMember(K key, Object o);
	判断 member 元素是否是集合 key 的成员
	
	Boolean move(K key, V value, K destKey);
	将 member 元素从 source 集合移动到 destination 集合


#ZSetOperations

Redis 有序集合和无序集合一样也是string类型元素的集合,且不允许重复的成员。
不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
有序集合的成员是唯一的,但分数(score)却可以重复。


#参考
http://www.jianshu.com/p/7bf5dc61ca06










RedisTemplate介绍

spring 封装了 RedisTemplate 对象来进行对redis的各种操作，它支持所有的 redis 原生的 api。

RedisTemplate中定义了对5种数据结构操作 
redisTemplate.opsForValue();//操作字符串 
redisTemplate.opsForHash();//操作hash 
redisTemplate.opsForList();//操作list 
redisTemplate.opsForSet();//操作set 
redisTemplate.opsForZSet();//操作有序set

@Bean 
public RedisTemplate

ZSetOperations
ListOperations
Redis的List其实是一个LinkedList，按照插入顺序排序。你可以添加一个元素导列表的头部（左边）或者尾部（右边）

Long leftPush(K key, V value);
    将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从左边插入）
V leftPop(K key, long timeout, TimeUnit unit);
    移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
V rightPopAndLeftPush(K sourceKey, K destinationKey);
    用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。
参考
很有用 http://www.jianshu.com/p/7bf5dc61ca06
