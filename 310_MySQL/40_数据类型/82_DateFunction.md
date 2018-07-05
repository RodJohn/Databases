

当前时间

    now()
        执行SQL时的时间;
        now(n)用于显示微秒数
    current_timestamp()
        相当于now()
    sysdate()
        调用函数的时间        

时间格式化

    DATE_FORMAT
    

时间加减


    DATE_ADD(date,INTERVAL expr)    
    
    DATE_ADD(now(),INTERVAL 1 DAY)
    
    数字可以是正数或者负数
    单位可以是SECOND/MINUTE/HOUR/MOUTH
    
    计算后的结果是符合规律的日期


转换毫秒数
    
    FROM_UNIXTIEM()函数把UNIX时间戳转换为日期
    UNIX_TIMESTAMP()函数把日期转换为UNIX时间戳。 
    
    
