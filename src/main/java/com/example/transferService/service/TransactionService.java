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

    /**
     * Transfer money from one account to another one
     * @param transfer is the object to be transferred
     */
    public void makeTransfer(Transfer transfer) {

        BigInteger accFrom = transfer.getFrom();
        BigInteger accTo = transfer.getTo();
        BigDecimal amountToTransfer = transfer.getAmount();

        validateTransaction(accFrom,accTo,amountToTransfer);
        updateBalance(accFrom, accTo, amountToTransfer);
        updateTransaction(accFrom,amountToTransfer);
    }

    /**
     * Helper Method to Validate Transaction details before making the transaction
     * @param accFrom is the account number to transfer money from
     * @param accTo   is the account number to transfer money to
     * @param amountToTransfer is the amount to be transferred
     */
    private void validateTransaction(BigInteger accFrom,BigInteger accTo, BigDecimal amountToTransfer) {
        //Ensure that money is not transferred between same account
        if (accFrom.equals(accTo)) { throw new TransactionException("You cannot transfer money from " +
                "this account to this same account. Please check the account number and try again.") ;}

        //Ensure transactions with the same properties have at least a minute interval to avoid possible duplicate transaction
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

    /**
     * Helper method to save a transaction in the database
     * @param accFrom is the account number money was transferred from i.e account that made the transaction
     * @param amountTransferred is the amount transferred
     */
    private void updateTransaction(BigInteger accFrom,BigDecimal amountTransferred) {
        Transaction transaction = new Transaction(amountTransferred,accFrom);
        transactionRepository.save(transaction);
    }


    /**
     * Helper method to save/update balance of the two transaction accounts in the database
     * @param accFrom as defined above
     * @param accTo as defined above
     * @param amountToTransfer as defined above
     */
    private void updateBalance(BigInteger accFrom, BigInteger accTo, BigDecimal amountToTransfer) {

        Balance balanceFrom = balanceRepository.findById(accFrom)
                .orElseThrow(() -> new TransactionException("Account number to transfer from: " + accFrom + "does not exist. " +
                        "Please check the account number and try again."));

        Balance balanceTo = balanceRepository.findById(accTo)
                .orElseThrow(() -> new TransactionException("Account number to transfer to: " + accTo +
                        "does not exist. Please check the account number and try again."));

        BigDecimal accFromBalance = balanceFrom.getBalance();
        BigDecimal accToBalance = balanceTo.getBalance();

        //Ensure there is sufficient balance in the account to transfer money from
        if(amountToTransfer.compareTo(accFromBalance) > 0)  {throw new TransactionException("Insufficient Balance in this" +
                "account: " + accFrom + "to transfer money from. Please deposit more money and try again"); }

        balanceFrom.setBalance(accFromBalance.subtract(amountToTransfer));
        balanceTo.setBalance(accToBalance.add(amountToTransfer));

        balanceRepository.save(balanceFrom);
        balanceRepository.save(balanceTo);
    }

    /**
     * Get all transactions history between any two accounts
     * @return all transactions
     */
    public List<Transaction> getAllTransactionsHistory(){
        return transactionRepository.findAll();
    }

    /**
     * Get the transaction history of a reference Number if it exists
     * @param refNo is the unique number of a particular transaction
     * @return a transaction
     */
    public Transaction getATransactionHistory(String refNo) {
        return transactionRepository.findById(refNo)
                .orElseThrow(() -> new TransactionException("The transaction with this reference number: " + refNo +
                        " does not exist. " + "Please check the reference number and try again."));

    }


}
