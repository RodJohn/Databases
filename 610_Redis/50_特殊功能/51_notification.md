


# 通知功能介绍

客户端可以通过 订阅与发布功能（pub/sub）功能，来接收那些以某种方式改动了Redis数据集的事件。

目前Redis的订阅与发布功能采用的是发送即忘（fire and forget）的策略，当订阅事件的客户端断线时，它会丢失所有在断线期间分发给它的事件。


#  配置

通知功能的配置默认是关闭状态，
开启通知功能的两种方式
修改配置文件redis.conf中的notify-keyspace-events
CONFIG SET notify-keyspace-events 字符


https://blog.csdn.net/men_wen/article/details/71104369