package com.levi9.demo.wallet.mapper;

import com.levi9.demo.wallet.controller.response.TransactionResponse;
import com.levi9.demo.wallet.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    default String toString(UUID uuid) {
        return uuid.toString();
    }

    @Mapping(target = "from", source = "from.id")
    @Mapping(target = "to", source = "to.id")
    @Mapping(target = "currency", source = "to.currency.currencyCode")
    TransactionResponse toResponse(Transaction transaction);
}
