package com.imooc.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {

    public static  final  int TOKEN_EXPIRE=3600;
    private MiaoshaUserKey(int prefixSeconds, String prefix) {
        super(prefixSeconds,prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");

}
