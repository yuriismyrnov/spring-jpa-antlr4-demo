package com.levi9.demo.wallet.mapper;

import com.levi9.demo.wallet.controller.response.TransactionResponse;
import com.levi9.demo.wallet.controller.response.UserResponse;
import com.levi9.demo.wallet.controller.response.WalletResponse;
import com.levi9.demo.wallet.entity.Currency;
import com.levi9.demo.wallet.entity.Transaction;
import com.levi9.demo.wallet.entity.User;
import com.levi9.demo.wallet.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    default String toString(UUID uuid) {
        return uuid.toString();
    }

    @Mapping(target = "currency", source = "to.currency")
    TransactionResponse toResponse(Transaction transaction);

    WalletResponse toResponse(Wallet wallet);

    UserResponse toResponse(User user);

    default String toResponse(Currency currency) {
        return currency.getCurrencyCode();
    }
}
