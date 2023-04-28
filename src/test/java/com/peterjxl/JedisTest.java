package com.peterjxl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jedis的测试类
 */
public class JedisTest {
    Jedis jedis;

    @Before
    public void init(){
        // 1.获取连接
        jedis = new Jedis();
    }

    @After
    public void destroy(){
        // 3.关闭连接
        jedis.close();
    }

    @Test
    public void helloJedis(){
        // 2. 操作Redis
        jedis.set("username", "peterjxl");
    }

    @Test
    public void jedisString(){
        jedis.set("username", "peterjxl");
        String username = jedis.get("username");
        System.out.println(username);

        jedis.setex("activecode", 20, "hehe");
    }

    @Test
    public void jedisHash(){
        // 存储数据
        jedis.hset("user", "name", "lisi");
        jedis.hset("user", "age", "23");
        jedis.hset("user", "gender", "female");

        // 获取数据
        String name = jedis.hget("user", "name");
        System.out.println("name: " + name);

        // 获取hash的所有map中的数据
        Map<String, String> user = jedis.hgetAll("user");
        Set<String> keySet = user.keySet();
        for(String key : keySet){
            String value = user.get(key);
            System.out.println(key + " : " + value);
        }
    }

    @Test
    public void jedisList(){
        // 存储数据
        jedis.lpush("myList", "a", "b", "c");   //从左开始存，结果是cba
        jedis.rpush("myList2", "a", "b", "c");   //从右开始存，结果是abc

        // 获取数据
        List<String> myList = jedis.lrange("myList", 0, -1);
        List<String> myList2 = jedis.lrange("myList2", 0, -1);
        System.out.println("myList: " + myList);
        System.out.println("myList2: " + myList2);

        // list 弹出数据
        String element1 = jedis.lpop("myList"); //c
        String element2 = jedis.rpop("myList"); //a
        System.out.println("element1: " + element1);
        System.out.println("element2: " + element2);
    }

    @Test
    public void jedisSet(){
        // set 存储
        jedis.sadd("mySet","java","php","c++");

        // set 获取
        Set<String> myset = jedis.smembers("mySet");
        System.out.println(myset);
    }

    @Test
    public void jedisSortedSet(){
        // sortedset 存储
        jedis.zadd("mySortedSet", 3,  "雷姆");
        jedis.zadd("mySortedSet", 30, "拉姆");
        jedis.zadd("mySortedSet", 55, "艾米莉雅");

        // sortedset 获取
        Set<String> mysortedset = jedis.zrange("mySortedSet", 0, -1);
        System.out.println(mysortedset);
    }
}
