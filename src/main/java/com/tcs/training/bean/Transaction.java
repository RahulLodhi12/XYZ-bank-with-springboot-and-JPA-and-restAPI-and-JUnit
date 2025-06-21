package com.tcs.training.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Integer TransactionId;
    private String AccountNumber;
    private String ToAccountNumber;
    private String TransactionsType;
    private Double Amount;
    private Timestamp TransactionsDate;

    public Transaction(Integer transactionId, String accountNumber, String toAccountNumber, String transactionsType, Double amount, Timestamp transactionsDate) {
        TransactionId = transactionId;
        AccountNumber = accountNumber;
        ToAccountNumber = toAccountNumber;
        TransactionsType = transactionsType;
        Amount = amount;
        TransactionsDate = transactionsDate;
    }

    public Transaction(){

    }

    public Transaction(String accountNumber, String toAccountNumber, String transactionsType, Double amt,Timestamp timestamp) {
        this.AccountNumber = accountNumber;
        this.ToAccountNumber = toAccountNumber;
        this.TransactionsType = transactionsType;
        this.Amount = amt;
        this.TransactionsDate = new Timestamp(System.currentTimeMillis());
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getToAccountNumber() {
        return ToAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        ToAccountNumber = toAccountNumber;
    }

    public String getTransactionsType() {
        return TransactionsType;
    }

    public void setTransactionsType(String transactionsType) {
        TransactionsType = transactionsType;
    }

    public Timestamp getTransactionsDate() {
        return TransactionsDate;
    }

    public void setTransactionsDate(Timestamp transactionsDate) {
        TransactionsDate = transactionsDate;
    }

    public Integer getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(Integer transactionId) {
        TransactionId = transactionId;
    }
}
