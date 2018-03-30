package com.lambo.repository;

import com.lambo.entity.Student;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by allen on 18-3-28.
 */
@CacheConfig(cacheNames="student")
public interface StudentReposiroty extends PagingAndSortingRepository<Student, Integer> {
    @Cacheable(key = "#p0")
    Student findById(int id);

    @Override
    @CachePut(key="#p0.id")
    Student save(Student student);

    @Transactional
    @Modifying
    @CacheEvict(key="#p0")
    void deleteById(int id);


    List<Student> findAllByOrderByCreateTime(Pageable pageable);

    List<Student> findAllOrOrderByCreateTime(Pageable pageable);
}
