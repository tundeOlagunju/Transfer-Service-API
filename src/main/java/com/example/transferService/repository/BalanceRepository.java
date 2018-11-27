package com.example.transferService.repository;

import com.example.transferService.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, BigInteger> { }

