package com.allanweber.customers.transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Integer> {

    List<AccountTransaction> findByAccount(Integer account);
}
