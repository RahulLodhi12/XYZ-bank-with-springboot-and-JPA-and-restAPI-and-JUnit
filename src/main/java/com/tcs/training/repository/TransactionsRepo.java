package com.tcs.training.repository;

import com.tcs.training.bean.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepo extends JpaRepository<Transaction,Integer> {

}
