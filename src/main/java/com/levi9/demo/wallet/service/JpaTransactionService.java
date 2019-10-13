package com.levi9.demo.wallet.service;

import com.levi9.demo.wallet.controller.request.TransactionCreateRequest;
import com.levi9.demo.wallet.entity.Transaction;
import com.levi9.demo.wallet.entity.Wallet;
import com.levi9.demo.wallet.repository.TransactionRepository;
import com.levi9.demo.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class JpaTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final WalletRepository walletRepository;

    @Override
    public Transaction create(TransactionCreateRequest request) {
        Wallet from = findWallet(request.getFrom());
        Wallet to = findWallet(request.getTo());
        if (from.equals(to)) {
            throw new IllegalArgumentException("Sender and receiver must not be the same wallet");
        }
        if (!Objects.equals(from.getCurrency(), to.getCurrency())) {
            throw new IllegalArgumentException("Sender and receiver wallets must have the same currency");
        }
        int scale = to.getCurrency().getFractionDigits();
        BigDecimal amount = request.getAmount().setScale(scale, RoundingMode.HALF_UP);
        if (amount.compareTo(BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP)) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        var transaction = Transaction.create(from, to, amount);
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<Transaction> read(String filter, Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    private Wallet findWallet(String id) {
        return walletRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Wallet " + id + " doesn't exist"));
    }
}
