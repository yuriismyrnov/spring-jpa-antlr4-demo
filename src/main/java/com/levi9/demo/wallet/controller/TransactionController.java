package com.levi9.demo.wallet.controller;

import com.levi9.demo.wallet.controller.request.TransactionCreateRequest;
import com.levi9.demo.wallet.controller.response.TransactionResponse;
import com.levi9.demo.wallet.mapper.TransactionMapper;
import com.levi9.demo.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("v1/transactions")
@RestController
public class TransactionController {

    private final TransactionService service;

    private final TransactionMapper mapper;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody @Valid TransactionCreateRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.create(request)));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> read(@RequestParam(required = false) String filter,
                                                          Pageable pageable) {
        return ResponseEntity.ok(service.read(filter, pageable).map(mapper::toResponse));
    }
}
