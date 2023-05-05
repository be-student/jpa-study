package com.bestudent.connectionlimit.service;

import org.springframework.stereotype.Service;

@Service
public class SleepService {

    public void sleep() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
