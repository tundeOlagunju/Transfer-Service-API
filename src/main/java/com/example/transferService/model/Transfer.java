package com.example.transferService.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;


public class Transfer{

    @Min(value = 0, message = "Invalid account number :It cannot be negative")
    private BigInteger from;

    @Min(value = 0, message = "Invalid account number :It cannot be negative")
    private BigInteger to;

    @DecimalMin(value = "10.00",message = "Amount to transfer must be at least 10.00")
    private BigDecimal amount;

    public BigInteger getFrom() {
        return from;
    }

    public BigInteger getTo() {
        return to;
    }

    public @DecimalMin("10.00") BigDecimal
    getAmount() {
        return amount;
    }

}
