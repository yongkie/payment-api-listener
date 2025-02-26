package com.alphabet.simple.payment.module.account.service;

import com.alphabet.simple.payment.module.account.entity.AccountEntity;
import com.alphabet.simple.payment.module.account.model.AccountModel;
import com.alphabet.simple.payment.module.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountEntity save(AccountModel model){
        var entity = new AccountEntity();
        BeanUtils.copyProperties(model, entity);
        return accountRepository.save(entity);
    }
}
