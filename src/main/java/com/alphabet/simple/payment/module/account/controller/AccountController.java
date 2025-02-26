package com.alphabet.simple.payment.module.account.controller;

import com.alphabet.simple.payment.annotations.Permission;
import com.alphabet.simple.payment.module.account.entity.AccountEntity;
import com.alphabet.simple.payment.module.account.model.AccountModel;
import com.alphabet.simple.payment.module.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/save")
    @Permission("saveAccount")
    public ResponseEntity<AccountEntity> save(@RequestBody AccountModel model){
        return ResponseEntity.ok(accountService.save(model));
    }
}
