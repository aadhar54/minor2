package com.aadhar.minor.services;

import com.aadhar.minor.models.*;
import com.aadhar.minor.repositories.RequestRepository;
import com.aadhar.minor.requests.PlaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Value("${max.book.limit}")
    private int MAX_BOOK_LIMIT;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    public Request createRequest(PlaceRequest placeRequest, Student student){
        List<Admin> adminList = adminService.getAllAdmins();
        Comparator<Admin> AdminSorterByAscLoad = (o1, o2) -> o1.getRequestsToProcess().stream().
                filter(r->r.getRequestStatus().equals(RequestStatus.PENDING))
                .collect(Collectors.toList()).size()-(o2.getRequestsToProcess().stream().
                filter(r->r.getRequestStatus().equals(RequestStatus.PENDING))
                .collect(Collectors.toList()).size());

        Collections.sort(adminList,AdminSorterByAscLoad);
        Admin admin = adminList.get(0);

        Request request = placeRequest.to(student);
        request.setAdmin(admin);
        return requestRepository.save(request);
    }

    public Request getRequestById(int id){
        return requestRepository.findById(id).orElse(null);
    }

    public boolean checkIfValidRequest(Student student,PlaceRequest placeRequest){
        if(student!=null && student.getBookList()!=null && student.getBookList().size() >= MAX_BOOK_LIMIT){
            return false;
        }

        //check if there are already pending issue requests
        if(student.getRequestList()!=null){
            List<Request> pendingIssueRequests = student.getRequestList().stream().filter(x->x.getRequestStatus().equals(RequestStatus.PENDING) && x.getRequestType().equals(RequestType.ISSUE)).collect(Collectors.toList());
            if(pendingIssueRequests.size()+student.getBookList().size() >= MAX_BOOK_LIMIT){
                return false;
            }
        }


        Book book = bookService.getBookById(placeRequest.getBookId());
        //if request is ISSUE , book should not be already issued by anyone
        if(placeRequest.getRequestType().equals("ISSUE")){

             return (book.getStudent() == null);

        }

        //if request is RETURN , book should be issued to this student
        if(placeRequest.getRequestType().equals("RETURN")){
            if(book.getStudent() == null){
                return false;
            }
            return book.getStudent().equals(student);
        }

        //reaches here only if everything is valid
        return true;

    }
}
