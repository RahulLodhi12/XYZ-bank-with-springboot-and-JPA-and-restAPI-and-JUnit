package com.tcs.training.bean;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Random;


@Entity
public class Customer {
    @Id
    private String accountNumber;
    private String customerName;
    private String branch;
    private Double balance;

    public Customer() {}

    public Customer(String accountNumber, String customerName, String branch, Double balance) {
        long timestamp = System.currentTimeMillis(); // 13-digit positive number
        int randomNum = 10000 + new Random().nextInt(90000); // 5-digit positive number
        String accNo = timestamp + "" + randomNum; // 13 + 5 = 18-digit positive number (as String)

        this.accountNumber = accNo;
        this.customerName = customerName;
        this.branch = branch;
        this.balance = balance;
    }



    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}

/*
Q. Why we can't use @Component or @Bean in "Bean" Package, for Classes those represent the "table" in our DB.

Ans.
| Annotation   | Managed By    | Purpose                                 | Example Use              |
| ------------ | --------------| --------------------------------------- | ------------------------ |
| `@Entity`    | JPA/Hibernate | Maps class to **DB table**              | `Customer`, `User`, etc. |
| `@Component` | Spring IoC    | Marks a **service/helper** class        | `CustomerService`, etc.  |
| `@Bean`      | Spring IoC    | Declares a **manually configured bean** | Beans in config class    |

*/
