package com.example.transferService.service;

import com.example.transferService.exception.TransactionException;
import com.example.transferService.model.Balance;
import com.example.transferService.model.Transaction;
import com.example.transferService.model.Transfer;
import com.example.transferService.repository.BalanceRepository;
import com.example.transferService.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    public void makeTransfer(Transfer transfer) {

        BigInteger accFrom = transfer.getFrom();
        BigInteger accTo = transfer.getTo();
        BigDecimal amountToTransfer = transfer.getAmount();

        validateTransaction(accFrom,accTo,amountToTransfer);
        updateBalance(accFrom, accTo, amountToTransfer);
        updateTransaction(accFrom,amountToTransfer);
    }

    private void validateTransaction(BigInteger accFrom,BigInteger accTo, BigDecimal amountToTransfer) {
        if (accFrom.equals(accTo)) { throw new TransactionException("You cannot transfer money from " +
                "this account to this same account. Please check the account number and try again.") ;}
        Transaction lastTransaction = transactionRepository.findFirstByOrderByTransactionDateDesc();
        long currentTime = new Date().getTime();
        long lastTransactionTime = lastTransaction.getTransactionDate().getTime();
        System.out.println(TimeUnit.MILLISECONDS.toMinutes(currentTime) - TimeUnit.MILLISECONDS.toMinutes(lastTransactionTime));
        System.out.println(lastTransaction.getAmount());
        System.out.println(lastTransaction.getAccountNumber());
        if(TimeUnit.MILLISECONDS.toMinutes(currentTime) - TimeUnit.MILLISECONDS.toMinutes(lastTransactionTime) < 1
                && lastTransaction.getAmount().toBigInteger().equals(amountToTransfer.toBigInteger()) && lastTransaction.getAccountNumber().equals(accFrom)){
            throw new TransactionException("Possible duplicate transaction. This transaction looks" +
                    "similar to the previous one. Please wait for at least a minute and try again");
        }
    }

    private void updateTransaction(BigInteger accFrom,BigDecimal amountToTransfer) {
        Transaction transaction = new Transaction(amountToTransfer,accFrom);
        transactionRepository.save(transaction);
    }

    private void updateBalance(BigInteger accFrom, BigInteger accTo, BigDecimal amountToTransfer) {

        Balance balanceFrom = balanceRepository.findById(accFrom)
                .orElseThrow(() -> new TransactionException("Account number to transfer from does not exist. Please check"+
                        "the account number and try again."));

        Balance balanceTo = balanceRepository.findById(accTo)
                .orElseThrow(() -> new TransactionException("Account number to transfer to does not exist. Please check" +
                        "the account number and try again."));

        BigDecimal accFromBalance = balanceFrom.getBalance();
        BigDecimal accToBalance = balanceTo.getBalance();

        if(amountToTransfer.compareTo(accFromBalance) > 0)  {throw new TransactionException("Insufficient Balance in the" +
                "account to transfer from. Please deposit more money and try again"); }

        balanceFrom.setBalance(accFromBalance.subtract(amountToTransfer));
        balanceTo.setBalance(accToBalance.add(amountToTransfer));

        balanceRepository.save(balanceFrom);
        balanceRepository.save(balanceTo);
    }

    public List<Transaction> getAllTransactionsHistory(){
        return transactionRepository.findAll();
    }

    public Transaction getATransactionHistory(String refNo) {
        return transactionRepository.findById(refNo)
                .orElseThrow(() -> new TransactionException("The transaction with this reference number does not exist. " +
                        "Please check the reference number and try again."));
    }

}
