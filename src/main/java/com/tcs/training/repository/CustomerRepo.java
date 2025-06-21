package com.tcs.training.repository;

import com.tcs.training.bean.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CustomerRepo extends JpaRepository<Customer,String> {

}
