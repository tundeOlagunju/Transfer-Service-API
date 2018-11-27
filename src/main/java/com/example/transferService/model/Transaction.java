package com.example.transferService.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


@Entity
@Table(name ="transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction  {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private  String referenceNumber;

    private BigDecimal amount;

    private BigInteger accountNumber;

    private Date transactionDate =  new Date();


    public Transaction(){

    }

    public Transaction(BigDecimal amount, BigInteger accountNumber){
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigInteger getAccountNumber() { return accountNumber; }

    public Date getTransactionDate() { return transactionDate;
    }
}