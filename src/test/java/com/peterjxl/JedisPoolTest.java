package com.peterjxl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jedis连接池的测试类
 */
public class JedisPoolTest {

    @Test
    public void helloJedisPool(){
        JedisPool jedisPool = new JedisPool();
        Jedis jedis = jedisPool.getResource();
        jedis.set("hello", "world");
        jedis.close();  //归还到连接池中
        jedisPool.close();  //关闭连接池
    }

    @Test
    public void JedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);  //连接实例的最大连接数
        config.setMaxIdle(10);  //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认是8
        JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
        jedisPool.close();
    }
}
