package com.temporaryTest;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.logging.SimpleFormatter;

public  class ASCD {
    public static void main(String[] args) {
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MONTH,0 );
//        c.set(Calendar.DAY_OF_MONTH,1 );
//        String format = DateFormatUtils.format(c.getTime(), "yyyy-mm-dd HH:mm:ss");
//        System.out.println(format);
//        LocalTime localTime = LocalTime.now();
//        System.out.println(localTime);
        HashMap<Integer, User> users = new HashMap<>(16);
        users.put(1,new User("张三",25 ) );
        users.put(3,new User("李四",22 ) );
        users.put(2,new User("王五",28 ) );
        users.put(4,new User("王五",28 ) );
        System.out.println(users);
        HashMap<Integer, User> sortHashMap = ASCD.sortHashMap(users);
        System.out.println(sortHashMap);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(23);
        list.add(22);
        list.add(25);
        list.add(24);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);

    }
    public static HashMap<Integer,User> sortHashMap(HashMap<Integer,User> map){
        //拿到map键值对集合
        Set<Map.Entry<Integer, User>> entrySet = map.entrySet();
        //set 集合转换为List 集合 使用工具类的排序方法
        ArrayList<Map.Entry<Integer, User>> list = new ArrayList<>(entrySet);
        Collections.sort(list, new Comparator<Map.Entry<Integer, User>>() {
            @Override
            public int compare(Map.Entry<Integer, User> o1, Map.Entry<Integer, User> o2) {
                // 根据年龄倒序排列
                return o2.getValue().getAge() - o1.getValue().getAge();
            }
        });
//        Collections.sort(list);
        //创建新的 有序HashMap 集合
        LinkedHashMap<Integer, User> linkedHashMap = new LinkedHashMap<>();
        //将list 中数据存在 linkedHashMap
        for(Map.Entry<Integer,User> entry : list){
            linkedHashMap.put(entry.getKey(),entry.getValue() );
        }
        return linkedHashMap;

    }


}
