package com.aadhar.minor.repositories;

import com.aadhar.minor.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select t.id , t.created_on , t.external_transaction_id , t.fine, t.transaction_status ,t.request_id " +
            "from transaction t JOIN request r on t.request_id = r.id where r.book_id=?1 and t.transaction_status='SUCCESS' " +
            "and r.request_type=\"ISSUE\"  order by t.created_on desc",nativeQuery = true)
    List<Transaction> findIssueTransactionsByBookIdDesc(int bookId);
}
