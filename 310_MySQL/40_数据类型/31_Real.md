


# 5 实数

    实数是带有小数部分的数字
    
    单精度FLOAT
    双精度DOUBLE
    高精度DECIMAL
    
    M*G/G 不一定等于 M
    
# 6 浮点数

    FLOAT 4 
    Double 8 
    FLOAT和DOUBLE类型支持使用标准的浮点运算进行近似计算（具体实现依赖于所使用的平台），
    内部使用double进行计算
    
    FLOAT(size,d)
    带有浮动小数点的小数字。在 size 参数中规定显示最大位数
    在 d 参数中规定小数点右侧的最大位数。

# 7 定点数

    DECIMAL类型用于存储精确的小数。
    使用二进制字符串进行保存数据
    由mysql服务器自身实现高精度计算(5.0以上) 运算还是用double
    根据小数的位数剩以相应的倍数即可。
   
    Decimal(18,9)
    
    
# 运用

    使用DECIMAL类型需要额外的空间和计算开销，数据量比较大的时候，可以考虑使用BIGINT代替DECIMAL，

    