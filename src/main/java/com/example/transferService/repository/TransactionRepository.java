package com.example.transferService.repository;

import com.example.transferService.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that interacts with the Transaction table in the database
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    /**
     * Custom JPA query method to get the latest transaction in the database
     * @return
     */
    Transaction findFirstByOrderByTransactionDateDesc();
}
