package com.bestudent.connectionlimit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final NewTransactionService newTransactionService;
    private final NotSupportedTransactionService notSupportedTransactionService;

    @Transactional
    public void transaction() {
        newTransactionService.startNewTransaction();
    }

    @Transactional
    public void transactionAndSleep() {
        notSupportedTransactionService.sleep();
    }
}
