package com.tcs.training.service;

import com.tcs.training.bean.Customer;
import com.tcs.training.bean.Transaction;
import com.tcs.training.repository.CustomerRepo;
import com.tcs.training.repository.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceClass {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TransactionsRepo transactionsRepo;

    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

    public Optional<Customer> getCustomerByAccNumber(String accountNumber){
        return customerRepo.findById(accountNumber);
    }

    public boolean createAccount(String name, String branch, Double balance){
        Customer customer = new Customer("",name,branch,balance);
        if(name.length()==0 || branch.length()==0) {
            return false;
        }
        else {
            customerRepo.save(customer);
            return true;
        }
    }

    public boolean updateAccountByAccountNumber(String accountNumber, Customer customer){
        Optional<Customer> exist = customerRepo.findById(accountNumber);
        if(exist.isPresent()){
            customer.setAccountNumber(accountNumber); //setting the accountNumber, so we don't need to pass the accountNumber from user or postman.
            customerRepo.save(customer);
            return true;
        }
        else{
            System.out.println("Not Found..");
            return false;
        }
    }

    public boolean deleteAccountByAccountNumber(String accountNumber){
        Optional<Customer> exist = customerRepo.findById(accountNumber);
        if(exist.isPresent()){
            customerRepo.delete(exist.get());
            return true;
        }
        else{
            System.out.println("Not Found..");
            return false;
        }
    }

    public List<Transaction> getAllTransactions(){
        return transactionsRepo.findAll();
    }

    public boolean depositByAccountNumber(String accountNumber, Double amt){
        Optional<Customer> exist = customerRepo.findById(accountNumber);
        if(exist.isPresent()){
            //operations on Customer Table
            Double currentBalance = exist.get().getBalance();
            exist.get().setBalance(currentBalance+amt);

            //operations on Transaction Table
            Transaction transaction = new Transaction(exist.get().getAccountNumber(),exist.get().getAccountNumber(),"deposit",amt,null);
            transactionsRepo.save(transaction);

            customerRepo.save(exist.get());
            return true;
        }
        else{
            System.out.println("Not Found..");
            return false;
        }
    }

    public boolean withdrawByAccountNumber(String accountNumber, Double amt){
        Optional<Customer> exist = customerRepo.findById(accountNumber);
        if(exist.isPresent()){
            Double currentBalance = exist.get().getBalance();
            if(currentBalance-amt<1000){
                System.out.println("Minimum Balance should be 1000 after withdraw..");
                return false;
            }
            //Operations on Customer Table
            exist.get().setBalance(currentBalance-amt);

            //Operations on Transaction Table
            Transaction transaction = new Transaction(exist.get().getAccountNumber(),exist.get().getAccountNumber(),"withdraw",amt,null);
            transactionsRepo.save(transaction);

            //Update/Save Customer Details
            customerRepo.save(exist.get());
            return true;
        }
        else{
            System.out.println("Not Found..");
            return false;
        }
    }

    public boolean fundTransferByAccountNumber(String accountNumber, String accNo2, Double amt){
        Optional<Customer> customer1 = customerRepo.findById(accountNumber);
        Optional<Customer> customer2 = customerRepo.findById(accNo2);
        if(customer1.isPresent() && customer2.isPresent()){
            //Withdraw from Customer1
            Double currentBalance1 = customer1.get().getBalance();
            if(currentBalance1-amt<1000){
                System.out.println("Minimum Balance should be 1000 after withdraw..");
                return false;
            }
            customer1.get().setBalance(currentBalance1-amt);

            //Deposit to Customer2
            Double currentBalance2 = customer2.get().getBalance();
            customer2.get().setBalance(currentBalance2+amt);

            //Update Transaction Table
            Transaction transaction = new Transaction(customer1.get().getAccountNumber(),customer2.get().getAccountNumber(),"transfer",amt,null);
            transactionsRepo.save(transaction);

            //Update Both Customer Details
            customerRepo.save(customer1.get());
            customerRepo.save(customer2.get());
            return true;
        }
        else{
            System.out.println("Not Found..");
            return false;
        }
    }
}
