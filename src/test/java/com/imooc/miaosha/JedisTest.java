package com.imooc.miaosha;

import org.junit.Test;
import redis.clients.jedis.Jedis;


public class JedisTest {
    @Test
    public static void main(String [] args){
        Jedis jedis= new Jedis("39.104.23.56");
        System.out.println("je"+jedis.ping());
        System.out.println(jedis.get("key1"));
    }


}
