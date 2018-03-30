package com.lambo.aspect;

import com.lambo.entity.Student;
import com.lambo.repository.StudentReposiroty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by allen on 18-3-28.
 */
@Component
@Aspect
public class StudentAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StudentReposiroty studentReposiroty;
    private static final String STUDENT_SET_KEY="stuent_set";
    @Pointcut(value = "within(com.lambo.service.StudentService)")
    public void studentAccess(){

    }
    @Around("studentAccess()")
    public Object around(ProceedingJoinPoint pjp){
        //方法名
        String methodName=pjp.getSignature().getName();
        //参数
        Object[] objects=pjp.getArgs();
        if("queryPage".equals(methodName)){
            int pageNum=(int)objects[0];
            int count=(int)objects[1];
            //desc
            Set<Integer> ids=redisTemplate.opsForZSet().reverseRange(STUDENT_SET_KEY,pageNum*count,pageNum*count+count-1);
            //asc
            //Set<Integer> ids=redisTemplate.opsForZSet().range(STUDENT_SET_KEY,pageNum*count,pageNum*count+count-1);

            List<Student> list=new ArrayList<>(ids.size());
            for (Integer id : ids) {
                list.add(studentReposiroty.findById(id));
            }
            return list;
        }else if("save".equals(methodName)){
            Student student=null;
            try {
                student=(Student)pjp.proceed();
                System.out.println(student);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return  null;
            }
            redisTemplate.opsForZSet().add(STUDENT_SET_KEY,student.getId(),student.getCreateTime().getTime());
            return student;

        }else if("deleteById".equals(methodName)){
            int id=(int)objects[0];
            redisTemplate.opsForZSet().remove(STUDENT_SET_KEY,id);
        }
        try {
            return pjp.proceed();
        }catch (Throwable throwable){
            throwable.printStackTrace();
            return null;
        }
    }
}
