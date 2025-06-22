package com.tcs.training.service;

import com.tcs.training.bean.Customer;
import com.tcs.training.bean.Transaction;
import com.tcs.training.repository.CustomerRepo;
import com.tcs.training.repository.TransactionsRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private TransactionsRepo transactionsRepo;

    @InjectMocks
    private ServiceClass serviceClass;

    @Test
    void testGetAllCustomer_Success(){
        List<Customer> mockCustomers = new ArrayList<>(Arrays.asList(new Customer("11","rahul lodhi","delhi",3500.89),new Customer("12","mohit poonia","jaipur",4500.00)));

        when(customerRepo.findAll()).thenReturn(mockCustomers);

        List<Customer> customerList = serviceClass.getAllCustomer(); //first call to the actual getAllCustomer(), but internally calls to the customerRepo.findAll()

        assertEquals("rahul lodhi",customerList.get(0).getCustomerName());
        verify(customerRepo).findAll();
    }

    @Test
    void testGetAllCustomer_Failure(){
        List<Customer> mockCustomers = new ArrayList<>(Arrays.asList(new Customer("11","rahul lodhi","delhi",3500.89),new Customer("12","mohit poonia","jaipur",4500.00)));

        when(customerRepo.findAll()).thenReturn(Collections.emptyList());

        List<Customer> customerList = serviceClass.getAllCustomer(); //first call to the actual getAllCustomer(), but internally calls to the customerRepo.findAll()

//        assertEquals("rahul",customerList.get(0).getCustomerName());
        assertTrue(customerList.isEmpty());
        verify(customerRepo).findAll();
    }

    @Test
    void testGetCustomerByAccNumber_Success(){
        Customer mockCustomer = new Customer("11","rahul poonia","jodhpur",8990.00); //auto generate the account number

        when(customerRepo.findById(mockCustomer.getAccountNumber())).thenReturn(Optional.of(mockCustomer));

        Optional<Customer> customerByAccNumber = serviceClass.getCustomerByAccNumber(mockCustomer.getAccountNumber());

        System.out.println(mockCustomer.getAccountNumber());
        System.out.println(customerByAccNumber.get().getAccountNumber());

        assertEquals(mockCustomer.getAccountNumber(),customerByAccNumber.get().getAccountNumber());
    }

    @Test
    void testGetCustomerByAccNumber_Failure(){
//        Customer mockCustomer = new Customer("11","rahul poonia","jodhpur",8990.00); //auto generate the account number

        when(customerRepo.findById("1189")).thenReturn(Optional.empty());

        Optional<Customer> customerByAccNumber = serviceClass.getCustomerByAccNumber("1189");

//        System.out.println(mockCustomer.getAccountNumber());
//        System.out.println(customerByAccNumber.get().getAccountNumber());

//        assertEquals("1189",customerByAccNumber.get().getAccountNumber());
        assertTrue(customerByAccNumber.isEmpty());
    }

    @Test
    void testCreateAccount_Success(){
        Customer mockCustomer = new Customer("","rajesh","jodhpur",3456.89);

        when(customerRepo.save(any(Customer.class))).thenReturn(mockCustomer);

        assertTrue(serviceClass.createAccount("rajesh","jodhpur",3456.89));

        verify(customerRepo).save(any(Customer.class));
    }

    @Test
    void testCreateAccount_Failure(){
        assertFalse(serviceClass.createAccount("","jodhpur",3456.89));
    }

    @Test
    void testUpdateAccountByAccountNumber_Success(){
        Customer existing = new Customer("","rahul","hindon",4500.00);
        Customer updateCustomer = new Customer("","newName","newBranch",8894.00);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.of(existing));
        when(customerRepo.save(updateCustomer)).thenReturn(updateCustomer);

        assertTrue(serviceClass.updateAccountByAccountNumber(existing.getAccountNumber(),updateCustomer));

        verify(customerRepo).findById(existing.getAccountNumber());
        verify(customerRepo).save(updateCustomer);
    }

    @Test
    void testUpdateAccountByAccountNumber_Failure(){
        Customer existing = new Customer("","rahul","hindon",4500.00);
        Customer updateCustomer = new Customer("","newName","newBranch",8894.00);

        //Simulating No Customer exist with Given Account Number
        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.empty());

        assertFalse(serviceClass.updateAccountByAccountNumber(existing.getAccountNumber(),updateCustomer));

        verify(customerRepo).findById(existing.getAccountNumber());
    }

    @Test
    void testDeleteAccountByAccountNumber_Success(){
        Customer existing = new Customer("","deepak","gzb",5674.33);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.of(existing));

        assertTrue(serviceClass.deleteAccountByAccountNumber(existing.getAccountNumber()));

        verify(customerRepo).findById(existing.getAccountNumber());
        verify(customerRepo).delete(existing);
    }

    @Test
    void testDeleteAccountByAccountNumber_Failure(){
        Customer existing = new Customer("","deepak","gzb",5674.33);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.empty());

        assertFalse(serviceClass.deleteAccountByAccountNumber(existing.getAccountNumber()));

        verify(customerRepo).findById(existing.getAccountNumber());
    }

    @Test
    void testGetAllTransactions_Success(){
        List<Transaction> mockTransactionList = new ArrayList<>(Arrays.asList(new Transaction(101,"1234","5678","transfer",450.00,null), new Transaction(102,"4567","7899","deposit",900.00,null)));

        when(transactionsRepo.findAll()).thenReturn(mockTransactionList);

        List<Transaction> transactionList = serviceClass.getAllTransactions();

        assertEquals("transfer",transactionList.get(0).getTransactionsType());
        assertEquals("deposit",transactionList.get(1).getTransactionsType());

        verify(transactionsRepo).findAll();
    }

    @Test
    void testGetAllTransactions_Failure(){
        List<Transaction> mockTransactionList = new ArrayList<>(Arrays.asList(new Transaction(101,"1234","5678","transfer",450.00,null), new Transaction(102,"4567","7899","deposit",900.00,null)));

        when(transactionsRepo.findAll()).thenReturn(Collections.emptyList());

        List<Transaction> transactionList = serviceClass.getAllTransactions();

        assertTrue(transactionList.isEmpty());

        verify(transactionsRepo).findAll();
    }

    @Test
    void testDepositByAccountNumber_Success(){
        Customer existing = new Customer("","roop singh","hindon",4600.66);
        Transaction transaction = new Transaction(101,existing.getAccountNumber(),existing.getAccountNumber(),"deposit",450.00,null);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.of(existing));
        when(transactionsRepo.save(any(Transaction.class))).thenReturn(transaction); //At run-time, new Transaction is created, that's why use "any"

        assertTrue(serviceClass.depositByAccountNumber(existing.getAccountNumber(),660.00));

        verify(customerRepo).findById(existing.getAccountNumber());
        verify(transactionsRepo).save(any(Transaction.class));
    }

    @Test
    void testDepositByAccountNumber_Failure(){
        Customer existing = new Customer("","roop singh","hindon",4600.66);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.empty());

        assertFalse(serviceClass.depositByAccountNumber(existing.getAccountNumber(),660.00));

        verify(customerRepo).findById(existing.getAccountNumber());
    }

    @Test
    void testWithdrawByAccountNumber_Success(){
        Customer existing = new Customer("","roop singh","hindon",4600.66);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.of(existing));

        assertTrue(serviceClass.withdrawByAccountNumber(existing.getAccountNumber(),660.00));

        verify(customerRepo).findById(existing.getAccountNumber());
    }

    @Test
    void testWithdrawByAccountNumber_LowBalanceCheck(){
        Customer existing = new Customer("","roop singh","hindon",4600.66);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.of(existing));

        assertFalse(serviceClass.withdrawByAccountNumber(existing.getAccountNumber(),4660.00));

        verify(customerRepo).findById(existing.getAccountNumber());
    }

    @Test
    void testWithdrawByAccountNumber_Failure(){
        Customer existing = new Customer("","roop singh","hindon",4600.66);

        when(customerRepo.findById(existing.getAccountNumber())).thenReturn(Optional.empty());

        assertFalse(serviceClass.withdrawByAccountNumber(existing.getAccountNumber(),660.00));

        verify(customerRepo).findById(existing.getAccountNumber());
    }

    @Test
    void testFundTransferByAccountNumber_Success(){
        Customer customer1 = new Customer("","vijay singh","delhi",7800.99);
        Customer customer2 = new Customer("","roop singh","gzb",9887.99);

        when(customerRepo.findById(customer1.getAccountNumber())).thenReturn(Optional.of(customer1));
        when(customerRepo.findById(customer2.getAccountNumber())).thenReturn(Optional.of(customer2));
        when(customerRepo.save(customer1)).thenReturn(customer1);
        when(customerRepo.save(customer2)).thenReturn(customer2);

        assertTrue(serviceClass.fundTransferByAccountNumber(customer1.getAccountNumber(),customer2.getAccountNumber(),800.88));

        verify(customerRepo).findById(customer1.getAccountNumber());
        verify(customerRepo).findById(customer2.getAccountNumber());
        verify(customerRepo).save(customer1);
        verify(customerRepo).save(customer2);
    }

    @Test
    void testFundTransferByAccountNumber_LowBalanceCheck(){
        Customer customer1 = new Customer("","vijay singh","delhi",7800.99);
        Customer customer2 = new Customer("","roop singh","gzb",9887.99);

        when(customerRepo.findById(customer1.getAccountNumber())).thenReturn(Optional.of(customer1));
        when(customerRepo.findById(customer2.getAccountNumber())).thenReturn(Optional.of(customer2));

        assertFalse(serviceClass.fundTransferByAccountNumber(customer1.getAccountNumber(),customer2.getAccountNumber(),7200.88));

        verify(customerRepo).findById(customer1.getAccountNumber());
        verify(customerRepo).findById(customer2.getAccountNumber());
    }

    @Test
    void testFundTransferByAccountNumber_Failure(){
        Customer customer1 = new Customer("","vijay singh","delhi",7800.99);
        Customer customer2 = new Customer("","roop singh","gzb",9887.99);

        when(customerRepo.findById(customer1.getAccountNumber())).thenReturn(Optional.empty());
        when(customerRepo.findById(customer2.getAccountNumber())).thenReturn(Optional.of(customer2));

        assertFalse(serviceClass.fundTransferByAccountNumber(customer1.getAccountNumber(),customer2.getAccountNumber(),800.88));

        verify(customerRepo).findById(customer1.getAccountNumber());
        verify(customerRepo).findById(customer2.getAccountNumber());
    }

}
