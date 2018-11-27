package com.example.transferService.service;

import com.example.transferService.exception.TransactionException;
import com.example.transferService.model.Balance;
import com.example.transferService.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Service
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;

    /**
     * Get all the existing account numbers and their balance
     * @return all account numbers
     */
    public List<Balance> getAllBalances(){
        return balanceRepository.findAll();
    }

    /**
     * Save a new balance and generate the unique account number i.e create a new account
     * @param balance
     */
    public void saveBalance(@Valid Balance balance){
        balanceRepository.save(balance);
    }

    /**
     * Update the balance of an existing account number when money is deposited
     * @param balance the balance object
     * @param accNo is the account number
     */
    public void updateBalance(@Valid Balance balance, BigInteger accNo) {
        Balance balance1 = balanceRepository.findById(accNo)
                .orElseThrow(() -> new TransactionException("This accNo: " + accNo + " does not exist. Please check it well" +
                        " and try again."));

        balance1.setBalance(balance1.getBalance().add(balance.getBalance()));
        balanceRepository.save(balance1);

    }

    /**
     * Get the balance of an account number
     * @param accNo is the account number
     * @return the balance
     */
    public Balance getABalance(BigInteger accNo) {
        Balance balance1 = balanceRepository.findById(accNo)
                .orElseThrow(() -> new TransactionException("This accNo: " + accNo + " does not exist. Please check it well" +
                        " and try again."));
        return balance1;
    }
}
