package com.bestudent.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.bestudent.jpa.entity.Member;
import com.bestudent.jpa.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void transactionSaveDropSave() {
        transactionService.transactionSaveDropSave();

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("beforeDropTransaction", "afterDropTransaction");
    }

    @Test
    void transactionSaveDropThrow() {
        try {
            transactionService.transactionSaveDropThrow();
        } catch (Exception e) {
            // ignore
        }

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).isEmpty();
    }

    @Test
    void saveDropSave() {
        transactionService.saveDropSave();

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("beforeDropTransaction", "afterDropTransaction");
    }

    @Test
    void saveDropThrow() {
        try {
            transactionService.saveDropThrow();
        } catch (Exception e) {
            // ignore
        }

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("beforeDropTransaction");
    }
}
