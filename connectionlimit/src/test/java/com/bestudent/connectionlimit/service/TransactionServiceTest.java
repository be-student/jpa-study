package com.bestudent.connectionlimit.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void 커넥션이_한개여서_에러() {
        assertThatThrownBy(() -> transactionService.transaction())
                .isInstanceOf(Exception.class);
    }

    @Test
    void 커넥션_하나_테스트() throws InterruptedException {
        Runnable first = () -> {
            transactionService.transactionAndSleep();
        };
        Runnable second = () -> {
            transactionService.transactionAndSleep();
        };

        Thread thread1 = new Thread(first);
        Thread thread2 = new Thread(second);

        thread1.start();
        thread2.start();

        //will throw error
        thread1.join();
        thread2.join();
    }
}
