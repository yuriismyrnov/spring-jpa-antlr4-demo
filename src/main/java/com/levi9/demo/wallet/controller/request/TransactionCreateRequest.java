package com.levi9.demo.wallet.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
public class TransactionCreateRequest {

    @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private String from;

    @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private String to;

    @NotNull
    @Positive
    private BigDecimal amount;
}
