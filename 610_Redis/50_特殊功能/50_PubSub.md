
# 概述

特点

缺点



Redis的消息多用于实时性较高的消息推送，并不保证可靠性。Redis的消息也无法支持水平扩展4

Pub/Sub功能（means Publish, Subscribe）即发布及订阅功能。

分类：
按照订阅方式分为基于主题（topic-based）、
基于内容（content-based）、
基于类型（type-based）的pub/sub方式。


基础订阅
模式订阅



概述
Redis中pub/sub特性，可以用来实现类似与JMS的“topic”功能，只不过这些消息无法被持久化而已。spring-data-redis组件中对pub/sub提供了类似JMS的编程模式，我们通过实例来展示如何使用。
    需要注意的是，在redis中消息的订阅端(subscribe)需要独占链接，那么消息接收将是阻塞的。
    代码实例中，使用了“连接池”/“消息异步接受”“消息并发处理”，请根据需要调整相关参数。
    1) Redis中"pub/sub"的消息,为"即发即失",server不会保存消息,如果publish的消息,没有任何client处于"subscribe"状态,消息将会被丢弃.如果client在subcribe时,链接断开后重连,那么此期间的消息也将丢失.Redis server将会"尽力"将消息发送给处于subscribe状态的client,但是仍不会保证每条消息都能被正确接收.
    2) 如果期望pub/sub的消息时持久的,那么需要借助额外的功能.参见"pub/sub持久化订阅"


缺点
http://blog.csdn.net/canot/article/details/51975566



# 操作

SUBSCRIBE

    SUBSCRIBE channel [channel ...]
    
    订阅给定的一个或多个频道的信息。

PUBLISH
    
    PUBLISH channel message
    
    将信息 message 发送到指定的频道 channel 。

PSUBSCRIBE
    
    PSUBSCRIBE pattern [pattern ...]
    
    订阅一个或多个符合给定模式的频道。



每个模式以 * 作为匹配符

充当订阅者订阅（first 与second）两个topic
SUBSCRIBE first second


l  发布者（redis-cli）连接发布
/usr/local/bin/redis-cli  -h 10.54.37.212 -p 6380
Pubish first wangbin
Publish second wangbin_second

取消订阅UNSUBSCRIBE测试
Redis-cli对取消订阅有bug可以使用telnet 进行测试


匹配模式订阅PSUBSCRIBE测试

发布者：
publish news.wangbin wangbin_patterncommand

匹配模式取消订阅PUNSUBSCRIBE测试
在telnet下测试如下：
PSUBSCRIBE news.*  wangbin*
punsubscribe wangbin*

topic
命令
订阅	:	subscribe chanel
发布:	publish chanel  message
举例
客户端1订阅CCTV1:
127.0.0.1:6379> subscribe CCTV1
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "CCTV1"
3) (integer) 1
客户端2订阅CCTV1和CCTV2:
127.0.0.1:6379> subscribe CCTV1 CCTV2
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "CCTV1"
3) (integer) 1
1) "subscribe"
2) "CCTV2"
3) (integer) 2
客户端3发送消息到CCTV1
127.0.0.1:6379> publish CCTV1 "cctv1 is good"
(integer) 2
//返回2表示两个客户端接收了次消息

客户端1，客户端2
1) "message"
2) "CCTV1"
3) "cctv1 is good"

如上的订阅/发布也称订阅发布到频道(使用publish与subscribe命令)，此外还有订阅发布到模式(使用psubscribe来订阅一个模式)
订阅CCTV的全部频道
127.0.0.1:6379> psubscribe CCTV*
Reading messages... (press Ctrl-C to quit)
1) "psubscribe"
2) "CCTV*"
3) (integer) 1



应用场景

服务器集群监控管理


参考

pub/sub 的命令行/jredis/spring等的实现
http://blog.csdn.net/canot/article/details/51938955
 



