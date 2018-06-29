
# 


    日期类型    	占用空间     表示范围
    DATETIME	8 +         1000-01-01 00:00:00 ~ 9999-12-31 23:59:59
    DATE	    3	        1000-01-01 ~ 9999-12-31
    TIMESTAMP	4 +         1970-01-01 00:00:00UTC ~ 2038-01-19 03:14:07UTC
    YEAR	    1	        YEAR(2):1970-2070, YEAR(4):1901-2155
    TIME	    3 +         -838:59:59 ~ 838:59:59



# DATETIME
     
    能保存从1001年到9999年，精度为(微)秒。
    日期和时间封装到格式为YYYYMMDDHHMMSS的整数中。
    使用8(+)个字节的存储空间。
    与时区无关

# TIMESTAMP 

概述

    保存1970年到2038年的范围
    保存了从1979年1月1日午夜（格林尼治标准时间）到现在的(微)秒数，它和UNIX时间戳相同。
    使用4(+)个字节的存储空间
    保存了时区信息
    
使用
    
    MySQL提供了FROM_UNIXTIEM()函数把UNIX时间戳转换为日期，提供了UNIX_TIMESTAMP()函数把日期转换为UNIX时间戳。 
    通常应该尽量使用TIMESTAMP，因为它比DATETIME空间效率更高。


# 微秒

    MySQL5.6及以后datetime timestamp time 支持微秒
    
    例如 datetime(3)
    
    微秒位数	   所需存储空间
    0	       0
    1, 2	   1 byte
    3, 4	   2 bytes
    5, 6	   3 bytes
    
    

    
