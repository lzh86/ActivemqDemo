package com.simplejob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class ApiMyElasticJobSimple implements SimpleJob {
    public void execute(ShardingContext shardingContext) {
        int key = shardingContext.getShardingItem();
        System.out.println();
        System.out.println("----------------------"+key+"-------------------");
        System.out.println();

        switch (key) {
            case 0:
                System.out.println("任务调度执行3: "+key);
                break;
            case 1:
                System.out.println("任务调度执行3: "+key);
                break;
            case 2:
                System.out.println("任务调度执行3: "+key);
                break;

            default:
                System.out.println("没有任务调度执行");
                break;
        }
    }
}
