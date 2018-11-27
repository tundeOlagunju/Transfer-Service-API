package com.example.transferService.controller;

import com.example.transferService.model.Balance;
import com.example.transferService.repository.BalanceRepository;
import com.example.transferService.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
public class BalanceController {

    @Autowired  BalanceService balanceService;


    @RequestMapping(value = "/api/balance",method = RequestMethod.GET)
    public List<Balance> getAllBalances(){
        return balanceService.getAllBalances();
    }

    @RequestMapping(value = "/api/balance",method = RequestMethod.POST)
    public void addBalance(@Valid @RequestBody Balance balance){
        balanceService.saveBalance(balance);
    }
}
