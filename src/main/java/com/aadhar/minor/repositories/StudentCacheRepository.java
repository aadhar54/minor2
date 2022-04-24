package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class StudentCacheRepository {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    private static final String KEY_PREFIX = "m2std::";
    private static final Integer KEY_EXPIRY = 20;

    public String getKeyWithPrefix(int studentId){
        return KEY_PREFIX+studentId;
    }

    public void saveStudentInCache(Student student) throws Exception {
        if(student.getId()==0){
            throw new Exception("Id for Student provided to save in cache is invalid");
        }
        String key = getKeyWithPrefix(student.getId());
        redisTemplate.opsForValue().set(key,student,KEY_EXPIRY, TimeUnit.DAYS);
    }

    public Student getStudentFromCache(int studentId) throws Exception {
        if(studentId==0){
            throw new Exception("Id for Student provided to get from cache is invalid");
        }
        String key = getKeyWithPrefix(studentId);
        return (Student)redisTemplate.opsForValue().get(key);
    }

}
