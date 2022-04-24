package com.aadhar.minor.services;

import com.aadhar.minor.models.*;
import com.aadhar.minor.repositories.AdminRepository;
import com.aadhar.minor.repositories.RequestRepository;
import com.aadhar.minor.repositories.StudentCacheRepository;
import com.aadhar.minor.repositories.StudentRepository;
import com.aadhar.minor.requests.AdminCreateRequest;
import com.aadhar.minor.requests.AdminProcessRequest;
import com.aadhar.minor.responses.AdminProcessRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RequestRepository requestRepository;

    @Lazy
    @Autowired
    TransactionService transactionService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MyUserService myUserService;

    @Autowired
    StudentCacheRepository studentCacheRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${auth.delimeter.value}")
    private String DELIMETER;

    @Value("${auth.admin}")
    private String ADMIN_AUTHORITY;

    @Value("${auth.bookviwer}")
    private String BOOK_VIEWER_AUTHORITY;


    public Admin createAdmin(AdminCreateRequest adminCreateRequest){
        MyUser myUser = adminCreateRequest.toMyUser();
        myUser.setAuthorities(ADMIN_AUTHORITY+DELIMETER+BOOK_VIEWER_AUTHORITY);
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));

        // imp to get the saved user back with id
        myUser = myUserService.saveMyUser(myUser);

        Admin admin = adminCreateRequest.to();
        admin.setMyUser(myUser);

        return adminRepository.save(admin);
    }

    public Admin saveOrUpdateAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Admin getAdminById(int id){
        return adminRepository.findById(id).orElse(null);
    }

    public AdminProcessRequestResponse processRequestLogic(AdminProcessRequest adminProcessRequest,Request request, int adminId) throws Exception {
        //check if request is valid
        if (request == null) {
            throw new Exception("Request Id is not valid");
        }

        //check if admin is valid to process this request
        if (request.getAdmin().getId() != adminId) {
            throw new Exception("This request is assigned to another admin");
        }

        //check if request is not already processed
        if (!request.getRequestStatus().equals(RequestStatus.PENDING)) {
            throw new Exception("Request is already processed");
        }

        //If REJECTED
        //change request status
        //set admin comment in request if its not null
        //save request [NO TRANSACTION CREATED]
        if (adminProcessRequest.getRequestStatus().equals("DECLINED")) {
            request.setRequestStatus(RequestStatus.DECLINED);
            request.setAdminComment(adminProcessRequest.getAdminComment());
            request = requestRepository.save(request);

            //storing changes in student object in chache
            int studentId = request.getStudent().getId();
            Student student =  studentRepository.findById(studentId).orElse(null);
            studentCacheRepository.saveStudentInCache(student);


            return AdminProcessRequestResponse.builder()
                    .requestId(adminProcessRequest.getRequestId())
                    .requestStatus("DECLINED")
                    .adminComment(adminProcessRequest.getAdminComment())
                    .build();
        }

        // If APPROVED

        //change request status
        //set admin comment in request if its not null
        //save request
        //create Transaction
        try{
            request.setRequestStatus(RequestStatus.APPROVED);
            request.setAdminComment(adminProcessRequest.getAdminComment());
            request = requestRepository.save(request);

            //storing changes in student object in chache
            int studentId = request.getStudent().getId();
            Student student =  studentRepository.findById(studentId).orElse(null);
            studentCacheRepository.saveStudentInCache(student);

            return transactionService.createTransaction(request);
        }
        catch (Exception e){
            request.setRequestStatus(RequestStatus.DECLINED);
            request.setAdminComment(adminProcessRequest.getAdminComment());
            requestRepository.save(request);
            return AdminProcessRequestResponse.builder()
                    .requestId(adminProcessRequest.getRequestId())
                    .requestStatus("REJECTED")
                    .adminComment("REJECTED")
                    .build();
        }
    }

}
