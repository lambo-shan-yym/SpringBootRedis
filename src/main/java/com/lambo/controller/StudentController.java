package com.lambo.controller;

import com.lambo.entity.Student;
import com.lambo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by allen on 18-3-28.
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public Student add(Student student){
        student.setCreateTime(new Date());
        return studentService.save(student);
    }
    @PutMapping("/update")
    public Student update(Student student){
        return  studentService.save(student);
    }
    @DeleteMapping("/delete/{id:\\d+}")
    public void delete(@PathVariable("id") Integer id){
          studentService.deleteById(id);
    }
    @GetMapping("/get/{id:\\d+}")
    public Student get(@PathVariable("id")Integer id){
        return  studentService.findById(id);
    }

    @GetMapping("/find")
    public List<Student> queryPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("count") Integer count){
        return studentService.queryPage(pageNum,count);
    }

}
