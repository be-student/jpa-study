package com.bestudent.connectionlimit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewTransactionService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void startNewTransaction() {
    }
}
