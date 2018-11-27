package com.example.transferService.service;

import com.example.transferService.model.Balance;
import com.example.transferService.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;

    public List<Balance> getAllBalances(){
        return balanceRepository.findAll();
    }

    public void saveBalance(@Valid Balance balance){
        balanceRepository.save(balance);
    }

}
