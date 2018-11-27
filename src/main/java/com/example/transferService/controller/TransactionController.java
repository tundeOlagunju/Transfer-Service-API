package com.example.transferService.controller;

import com.example.transferService.model.Transaction;
import com.example.transferService.model.Transfer;
import com.example.transferService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired TransactionService transactionService;

    @RequestMapping(value = "/api/transfer",method = RequestMethod.POST)
    private void transferMoney(@Valid @RequestBody Transfer transfer){
        transactionService.makeTransfer(transfer);
    }

    @RequestMapping(value = "/api/transaction-history",method = RequestMethod.GET)
    private List<Transaction> getAllTransactionsHistory (){
        return transactionService.getAllTransactionsHistory();
    }

    @RequestMapping(value = "/api/transaction-history/{refNo}",method = RequestMethod.GET)
    private Transaction getATransactionHistory (@PathVariable String refNo){
        return transactionService.getATransactionHistory(refNo);
    }

}
