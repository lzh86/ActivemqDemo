package com.jedis;

import java.util.*;

import com.alibaba.fastjson.JSON;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import org.junit.FixMethodOrder;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJedis {
    private static Jedis jedis;

    private static ShardedJedis sharding;

    private static ShardedJedisPool pool;


    @BeforeClass

    public static void setUpBeforeClass() throws Exception {

        List<JedisShardInfo> shards = Arrays.asList(

                new JedisShardInfo("localhost", 6379),

                //使用相同的ip:port,仅作测试
                new JedisShardInfo("localhost", 6379));


        jedis = new Jedis("localhost");

        sharding = new ShardedJedis(shards);


        pool = new ShardedJedisPool(new JedisPoolConfig(), shards);

    }


    @AfterClass

    public static void tearDownAfterClass() throws Exception {

        jedis.disconnect();

        sharding.disconnect();

        pool.destroy();

    }

    /**
     * 普通同步调用
     */
    @Test
    public void test1Normal() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = jedis.set("n" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple SET: " + ((end - start) / 1000.0) + " seconds");
        //输出结果：Simple SET: 12.407 seconds

    }

    /**
     * 事务方式
     */
    @Test
    public void test2Trans() {
        long start = System.currentTimeMillis();
        Transaction tx = jedis.multi();
        for (int i = 0; i < 100000; i++) {
            tx.set("t" + i, "t" + i);
        }
        //System.out.println(tx.get("t1000").get());
        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start) / 1000.0) + " seconds");
        //输出结果：Transaction SET: 0.64 seconds
    }

    /**
     * 管道(异步)
     */
    @Test
    public void test3Pipelined() {

        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {

            pipeline.set("p" + i, "p" + i);

        }
        //System.out.println(pipeline.get("p1000").get());
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start) / 1000.0) + " seconds");
        //Pipelined SET: 0.626 seconds

    }

    /**
     * 管道中调用事务
     */
    @Test
    public void test4combPipelineTrans() {
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("" + i, "" + i);
        }
        pipeline.exec();
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined transaction: " + ((end - start) / 1000.0) + " seconds");
        //Pipelined transaction: 0.67 seconds

    }


    @Test
    //分布式直连同步调用
    public void test5shardNormal() {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {

            String result = sharding.set("sn" + i, "n" + i);

        }

        long end = System.currentTimeMillis();

        System.out.println("Simple@Sharing SET: " + ((end - start) / 1000.0) + " seconds");

    }


    @Test
    //分布式直连异步调用
    public void test6shardpipelined() {

        ShardedJedisPipeline pipeline = sharding.pipelined();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {

            pipeline.set("sp" + i, "p" + i);

        }

        List<Object> results = pipeline.syncAndReturnAll();

        long end = System.currentTimeMillis();

        System.out.println("Pipelined@Sharing SET: " + ((end - start) / 1000.0) + " seconds");

    }


    @Test
  //分布式连接池同步调用
    public void test7shardSimplePool() {
        ShardedJedis one = pool.getResource();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = one.set("spn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Simple@Pool SET: " + ((end - start) / 1000.0) + " seconds");
    }
    @Test
    //分布式连接池异步调用
    public void test8shardPipelinedPool() {
        ShardedJedis one = pool.getResource();
        ShardedJedisPipeline pipeline = one.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sppn" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Pipelined@Pool SET: " + ((end - start) / 1000.0) + " seconds");

    }
    @Test
    public void testHash(){
        ShardedJedis one = pool.getResource();
        ArrayList<Map<String,String>> list = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>(16);
        hashMap.put("name","李" );
        hashMap.put("age","23" );
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("打球");
        stringBuilder.append("看书");
        hashMap.put("hobby",stringBuilder.toString());
        StringBuilder builder = new StringBuilder();
        builder.append("123");
        builder.append("789");
        builder.append("456");
        hashMap.put("score",builder.toString() );
        list.add(hashMap);
        HashMap<String, String> hashMap1 = new HashMap<>(16);
        hashMap1.put("name","李" );
        hashMap1.put("age","23" );
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("打球");
        stringBuilder1.append("看书");
        hashMap1.put("hobby",stringBuilder1.toString());
        StringBuilder builder1 = new StringBuilder();
        builder1.append("123");
        builder1.append("789");
        builder1.append("456");
        hashMap1.put("score",builder1.toString() );
        list.add(hashMap1);
        String string = JSON.toJSON(list).toString();
        one.set("3", string);
    }
    @Test
    public void  getArrayList(){
        ShardedJedis one = pool.getResource();
        String s = one.get("3");
        List<Map<String,String>> parseList = (List) JSON.parse(s);
        for (Map<String,String> map : parseList){
            String name = map.get("name");
            System.out.println(name);

        }
        System.out.println(s);
    }

}
