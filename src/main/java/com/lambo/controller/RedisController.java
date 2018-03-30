package com.lambo.controller;

import com.lambo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by allen on 18-3-27.
 */
@RequestMapping("/redis")
@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/string/stringOperation")
    public String string(){
       stringRedisTemplate.opsForValue().set("name","lambo");
       return stringRedisTemplate.opsForValue().get("name").toString();
    }

    @RequestMapping("/stringOperation")
    public String stringOperation() {
        redisTemplate.opsForValue().set("name","lambo");
        return redisTemplate.opsForValue().get("name").toString();
    }
    @RequestMapping("/string/setOperation")
    public String set(){
        Set<String> set=new HashSet<>();
        set.add("zhangsan");
        set.add("lisi");
        set.add("lambo");
        stringRedisTemplate.opsForSet().add("lambo","zhaoiu","zhangsan");
        return stringRedisTemplate.opsForSet().members("lambo").toString();
    }

    @RequestMapping("/setOperation")
    public String setOperation(){
        Set<String> set=new HashSet<>();
        set.add("zhangsan");
        set.add("lisi");
        set.add("lambo");
        redisTemplate.opsForSet().add("lambo",set);
        System.out.println(redisTemplate.opsForSet().members("lambo"));
        return redisTemplate.opsForSet().members("lambo").toString();
    }

    @RequestMapping("/string/mapOperation")
    public String map(){
        Map<String,String> map=new HashMap<>();
        map.put("keyOne","valueOne");
        map.put("keyTwo","valueTwo");
        stringRedisTemplate.opsForHash().putAll("mapOne",map);
        Map<Object,Object> map_one=stringRedisTemplate.opsForHash().entries("mapOne");
        System.out.println(map_one);
        Set<Object>set=stringRedisTemplate.opsForHash().keys("mapOne");
        System.out.println(set);
        List<Object>list= stringRedisTemplate.opsForHash().values("mapOne");
        System.out.println(list);
        String key=(String)stringRedisTemplate.opsForHash().get("mapOne","keyOne");
        System.out.println(key);
        return "SUCCESS";
    }

    @RequestMapping("/mapOperation")
    public String mapOperation(){
        Map<String,String> map=new HashMap<>();
        map.put("keyOne","valueOne");
        map.put("keyTwo","valueTwo");
        redisTemplate.opsForHash().putAll("mapOne",map);

        Map<String,String> map_one=redisTemplate.opsForHash().entries("mapOne");
        System.out.println(map_one);
        Set<String>set=redisTemplate.opsForHash().keys("mapOne");
        System.out.println(set);
        List<String>list= redisTemplate.opsForHash().values("mapOne");
        System.out.println(list);
        String key=(String)redisTemplate.opsForHash().get("mapOne","keyOne");
        System.out.println(key);
        return "SUCCESS";
    }

    @RequestMapping("/string/listOperation")
    public String list(){
        stringRedisTemplate.opsForList().leftPush("list","leftOne");
        stringRedisTemplate.opsForList().leftPush("list","leftTwo");
        stringRedisTemplate.opsForList().rightPush("list","rightOne");
        stringRedisTemplate.opsForList().rightPush("list","rightTwo");
        List<String> list=stringRedisTemplate.opsForList().range("list",0,1);
        System.out.println(list);
        //stringRedisTemplate.opsForList().leftPop("list");
        return "SUCEESS";
    }

    @RequestMapping("/listOperation")
    public String listOperation(){
        List<String> list=new ArrayList<>();
        list.add("listLeftOne");
        list.add("listLeftTwo");
        list.add("listLeftThree");
        redisTemplate.opsForList().leftPush("listLeft",list);

        List<String> listRight=new ArrayList<>();
        listRight.add("listRightOne");
        listRight.add("listRightTwo");
        redisTemplate.opsForList().rightPush("listRight",listRight);

        List<String> resultListOne=(List<String>)redisTemplate.opsForList().leftPop("listLeft");
       // List<String> resultRightOne=(List<String>)redisTemplate.opsForList().rightPop("listRight");
        System.out.println(resultListOne);
       // System.out.println(resultRightOne);
        return "SUCCESS";
    }

    @RequestMapping("/studentOperation")
    public String studentOperation(){
        List<Student>list=new ArrayList<>();
        list.add(new Student(1,"lambo",18));
        list.add(new Student(2,"lambo2",20));
        redisTemplate.opsForValue().set("studentList",list);
        List<Student>tempList=( List<Student>)redisTemplate.opsForValue().get("studentList");
        System.out.println(tempList);
        return  "SUCCESS";
    }
}
