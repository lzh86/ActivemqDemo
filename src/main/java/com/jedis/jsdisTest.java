package com.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class jsdisTest {
    public static void main(String[] args) {
//        // 主机地址
//        String host = "127.0.0.1";
//        // 构建连接池配置信息
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 设置最大连接数
//        jedisPoolConfig.setMaxTotal(50);
//        // 超时时间
//        int timeout = 10000;
//        // 授权密码
//        String password = "root";
//        // 构建连接池
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, 6379,
//                timeout, password);
//        // 从连接池中获取连接
//        Jedis jedis = jedisPool.getResource();
//        // 设置访问密码
//        // 读取数据
//        System.out.println(jedis.get("mytest"));
//
//        // 将连接还回到连接池中
//        jedisPool.returnResource(jedis);
//
//        // 释放连接池
//        jedisPool.close();
        String classFile = "com.jd.". replaceAll(".", "/") + "MyClass.class";
        System.out.println(classFile);
        double x=2.0;
        double z=2.0;
        int y=4;
        int b=4;

        x/=++y;
        z/=++b;
        System.out.println(x);
        System.out.println(z);

    }
}
