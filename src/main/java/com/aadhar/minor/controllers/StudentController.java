package com.aadhar.minor.controllers;

import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.models.Student;
import com.aadhar.minor.requests.PlaceRequest;
import com.aadhar.minor.requests.StudentCreateRequest;
import com.aadhar.minor.responses.StudentResponse;
import com.aadhar.minor.services.BookService;
import com.aadhar.minor.services.RequestService;
import com.aadhar.minor.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    RequestService requestService;

    @Autowired
    BookService bookService;

    // Permit All -> No Auth
    @PostMapping("/student")
    public StudentResponse createStudent(@Valid @RequestBody StudentCreateRequest studentCreateRequest) throws Exception {
       return studentService.createStudent(studentCreateRequest).toStudentResponse();
    }

    // Student
    @PostMapping("/student/request")
    public String createRequest(@Valid @RequestBody PlaceRequest placeRequest) throws Exception {
        MyUser myUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int studentId = myUser.getStudent().getId();
        Student student = studentService.getStudentById(studentId);


        //ensure student with less than max limit of book to place request
        boolean isValid = requestService.checkIfValidRequest(student,placeRequest);

//        if(!isValid && placeRequest.getRequestType().equals("ISSUE")){
//            return "This is not a valid request";
//        }

        if(!isValid){
            return "This request can not be created as it is Invalid.";
        }

        return studentService.createRequest(placeRequest,student).getExternalRequestId();
    }

    // Admin
    @GetMapping("/student/id/{id}")
    public StudentResponse getStudentById(@PathVariable("id") int id) throws Exception {
       Student student = studentService.getStudentById(id);
        StudentResponse studentResponse = student.toStudentResponse();
       return studentResponse;
    }

    //Student
    @GetMapping("/student")
    public StudentResponse getStudent() throws Exception {
        MyUser myUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int studentId = myUser.getStudent().getId();
        return getStudentById(studentId);
    }


}
