package com.aadhar.minor.controllers;

import com.aadhar.minor.models.Admin;
import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.models.Request;
import com.aadhar.minor.requests.AdminCreateRequest;
import com.aadhar.minor.requests.AdminProcessRequest;
import com.aadhar.minor.responses.AdminProcessRequestResponse;
import com.aadhar.minor.services.AdminService;
import com.aadhar.minor.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    RequestService requestService;

    //Admin
    @PostMapping("/admin")
    public Admin createAdmin(@Valid @RequestBody AdminCreateRequest adminCreateRequest){
        return adminService.createAdmin(adminCreateRequest);
    }

    //Admin
    @GetMapping("/admin")
    public Admin getAdmin(){
        MyUser myUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int adminId = myUser.getAdmin().getId();
        return adminService.getAdminById(adminId);
    }

    //Admin
    @PostMapping("/admin/process_request")
    public AdminProcessRequestResponse processRequest(@Valid @RequestBody AdminProcessRequest adminProcessRequest) throws Exception {
        MyUser myUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int adminId = myUser.getAdmin().getId();
        Request request = requestService.getRequestById(adminProcessRequest.getRequestId());
        return adminService.processRequestLogic(adminProcessRequest,request,adminId);
    }

}
