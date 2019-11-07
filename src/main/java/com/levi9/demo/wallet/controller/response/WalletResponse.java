package com.levi9.demo.wallet.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletResponse {

    private String id;

    private String name;

    private String currency;

    private UserResponse user;
}
