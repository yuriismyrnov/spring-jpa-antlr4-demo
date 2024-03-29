package com.levi9.demo.wallet.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class TransactionResponse {

    private String id;

    private WalletResponse from;

    private WalletResponse to;

    private BigDecimal amount;

    private String currency;

    private Instant datetime;
}
