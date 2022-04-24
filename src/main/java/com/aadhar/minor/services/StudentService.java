package com.aadhar.minor.services;

import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.models.Request;
import com.aadhar.minor.models.Student;
import com.aadhar.minor.repositories.StudentCacheRepository;
import com.aadhar.minor.repositories.StudentRepository;
import com.aadhar.minor.requests.PlaceRequest;
import com.aadhar.minor.requests.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Lazy
    @Autowired
    RequestService requestService;

    @Autowired
    MyUserService myUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentCacheRepository studentCacheRepository;

    @Value("${auth.delimeter.value}")
    private String DELIMETER;

    @Value("${auth.student}")
    private String STUDENT_AUTHORITY;

    @Value("${auth.bookviwer}")
    private String BOOK_VIEWER_AUTHORITY;

    public Student createStudent(StudentCreateRequest studentCreateRequest) throws Exception {
        MyUser myUser = studentCreateRequest.toMyUser();
        myUser.setAuthorities(STUDENT_AUTHORITY+DELIMETER+BOOK_VIEWER_AUTHORITY);
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUser = myUserService.saveMyUser(myUser);

        Student student = studentCreateRequest.to();
        student.setMyUser(myUser);

        // first student persisted in db , now lets save in cache
        student = studentRepository.save(student);
        studentCacheRepository.saveStudentInCache(student);
        return student;
    }


    public Student getStudentById(int id) throws Exception {
        // first check for student in cache
        Student student = studentCacheRepository.getStudentFromCache(id);
        if(student!=null){
            return student;
        }

        //if not found in cache , save in cache and return from db
        student =  studentRepository.findById(id).orElse(null);
        studentCacheRepository.saveStudentInCache(student);
        return student;
    }

    public Request createRequest(PlaceRequest placeRequest,Student student) throws Exception {
        Request request = requestService.createRequest(placeRequest,student);
        student =  studentRepository.findById(student.getId()).orElse(null);
        studentCacheRepository.saveStudentInCache(student);
        return request;
    }



}
