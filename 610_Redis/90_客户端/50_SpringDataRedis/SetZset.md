 SetOperations
Redis的Set是string类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。

Redis 中 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。

方法
Long add(K key, V... values);
无序集合中添加元素，返回添加个数

Long remove(K key, Object... values);
移除集合中一个或多个成员

Boolean isMember(K key, Object o);
判断 member 元素是否是集合 key 的成员

Boolean move(K key, V value, K destKey);
将 member 元素从 source 集合移动到 destination 集合
ZSetOperations
Redis 有序集合和无序集合一样也是string类型元素的集合,且不允许重复的成员。 
不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。 
有序集合的成员是唯一的,但分数(score)却可以重复。

参考
http://www.jianshu.com/p/7bf5dc61ca06