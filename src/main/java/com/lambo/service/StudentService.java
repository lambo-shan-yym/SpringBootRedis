package com.lambo.service;

import com.lambo.entity.Student;
import com.lambo.repository.StudentReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by allen on 18-3-28.
 */
@Service
public class StudentService {
    @Autowired
    private StudentReposiroty studentReposiroty;

    public Student findById(int id){
     return studentReposiroty.findById(id);
    }


    public  Student save(Student student){
        return  studentReposiroty.save(student);
    }


    public void deleteById(int id){
         studentReposiroty.deleteById(id);
    }

    public List<Student> queryPage(Integer pageNum, Integer count){
        Pageable pageable=new PageRequest(pageNum,count);
        return studentReposiroty.findAllByOrderByCreateTime(pageable);
    }

}
