package com.levi9.demo.wallet.service;

import com.levi9.demo.wallet.controller.request.TransactionCreateRequest;
import com.levi9.demo.wallet.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Transaction create(TransactionCreateRequest request);

    Page<Transaction> read(String filter, Pageable pageable);
}
