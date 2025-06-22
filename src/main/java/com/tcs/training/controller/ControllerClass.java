package com.tcs.training.controller;

import com.tcs.training.bean.Customer;
import com.tcs.training.bean.Transaction;
import com.tcs.training.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/xyz/bank")
public class ControllerClass {

    @Autowired
    ServiceClass serviceClass;

    @GetMapping("/customers")
    public List<Customer> getAllCustomer(){
        return serviceClass.getAllCustomer();
    }

    @GetMapping("/customers/{accountNumber}")
    public Optional<Customer> getCustomerByAccNumber(@PathVariable String accountNumber){
        return serviceClass.getCustomerByAccNumber(accountNumber);
    }

    @PostMapping("/customers/createAccount")
    public boolean createAccount(@RequestParam String name, @RequestParam String branch, @RequestParam Double balance){
        return serviceClass.createAccount(name,branch,balance);
    }

    @PutMapping("/customers/{accountNumber}")
    public boolean updateAccountByAccountNumber(@PathVariable String accountNumber, @RequestBody Customer customer){
        return serviceClass.updateAccountByAccountNumber(accountNumber,customer);
    }

    @DeleteMapping("/customers/{accountNumber}")
    public boolean deleteAccountByAccountNumber(@PathVariable String accountNumber){
        return serviceClass.deleteAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(){
        return serviceClass.getAllTransactions();
    }

//    @PathVariable -> Used to extract values from the path of the URL (static URL)
//    @RequestParam -> Used to extract values from the runtime URL parameters (URL change at Runtime)
    @PostMapping("/customers/{accountNumber}/deposit")
    public void depositByAccountNumber(@PathVariable String accountNumber, @RequestParam Double amt){
        serviceClass.depositByAccountNumber(accountNumber,amt);
    }

    @PostMapping("/customers/{accountNumber}/withdraw")
    public boolean withdrawByAccountNumber(@PathVariable String accountNumber, @RequestParam Double amt){
        return serviceClass.withdrawByAccountNumber(accountNumber,amt);
    }

    @PostMapping("/customers/{accountNumber}/fundTransfer")
    public boolean fundTransferByAccountNumber(@PathVariable String accountNumber, @RequestParam String accNo2, @RequestParam Double amt){
        return serviceClass.fundTransferByAccountNumber(accountNumber,accNo2,amt);
    }

}
