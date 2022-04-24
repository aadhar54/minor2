package com.aadhar.minor.services;

import com.aadhar.minor.models.*;
import com.aadhar.minor.repositories.TransactionRepository;
import com.aadhar.minor.responses.AdminProcessRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookService bookService;

    @Lazy
    @Autowired
    StudentService studentService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AdminService adminService;

    @Value("${max.book.issueDays}")
    private int MaxIssueDays;

    @Value("${max.finePerDay.perBook}")
    private double finePerDay;


    public AdminProcessRequestResponse createTransaction(Request request) throws Exception {

        Transaction transaction = Transaction.builder()
                .externalTransactionId(UUID.randomUUID().toString())
                .request(request)
                .transactionStatus(TransactionStatus.PENDING)
                .fine(calFine(request))
                .build();

        try{
            // save transaction
            transaction = transactionRepository.save(transaction);

            // Actual transaction process
            switch(request.getRequestType()){

                case ISSUE :
                    issue_book(request);
                    break;

                case RETURN :
                    return_book(request);
                    break;
            }

            // Transaction as complete and save transaction
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);


            return AdminProcessRequestResponse.builder()
                    .requestId(request.getId())
                    .requestStatus("SUCCESS")
                    .adminComment(request.getAdminComment())
                    .build();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            transactionRepository.save(transaction);

            return AdminProcessRequestResponse.builder()
                    .requestId(request.getId())
                    .requestStatus("FAIL")
                    .adminComment("REJECTED")
                    .build();
        }

    }

    private Double calFine(Request request) throws Exception {

        if(request.getRequestType().equals(RequestType.ISSUE)){
            return 0.0;
        }

        // Here we wil get the book from the return request and get the list of successsful transaction for this book .
        // The last successful transaction should be a ISSUE one and it will give us the issue date .
        int bookId = request.getBook().getId();
        Transaction lastIssueTransactionForBook = transactionRepository.findIssueTransactionsByBookIdDesc(bookId).get(0);

        if(lastIssueTransactionForBook==null){
            throw new Exception("Last Txn is not an ISSUE transaction");
        }

        Date TxnIssueDate = lastIssueTransactionForBook.getCreatedOn();
        long issueDateInMillis = TxnIssueDate.getTime();

        long timeDiff = System.currentTimeMillis() - issueDateInMillis;
        long noOfDaysPassed = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        double fine = 0.0;
        if(noOfDaysPassed>MaxIssueDays){
            long daysEligibleForFine = noOfDaysPassed - MaxIssueDays;
            fine+= daysEligibleForFine * finePerDay;
            return fine;
        }

        return fine;

    }


    private void issue_book(Request request) throws Exception {
        //case ISSUE - //create transaction
        // assign student in book table and save book
        Book book = bookService.getBookById(request.getBook().getId());
        Student student = studentService.getStudentById(request.getStudent().getId());
        book.setStudent(student);
        bookService.saveOrUpdateBook(book);

    }

    private void return_book(Request request) {
        //case RETURN - //create transaction
        //remove student in book table and save book
        Book book = bookService.getBookById(request.getBook().getId());
        book.setStudent(null);
        bookService.saveOrUpdateBook(book);
    }

}
