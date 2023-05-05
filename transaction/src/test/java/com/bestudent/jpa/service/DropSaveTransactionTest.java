package com.bestudent.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.bestudent.jpa.entity.Member;
import com.bestudent.jpa.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/truncate.sql")
class DropSaveTransactionTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void transactionSaveDropSaveAndSaveMember() {
        transactionService.transactionSaveDropSaveAndSaveMember();

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("beforeDropTransaction", "fromDropTransactionService",
                "afterDropTransaction");
    }

    @Test
    void transactionSaveDropSaveAndThrow() {
        try {
            transactionService.transactionSaveDropSaveAndThrow();
        } catch (Exception e) {
            // ignore
        }

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("fromDropTransactionService");
    }

    @Test
    void saveDropSaveAndSaveMember() {
        transactionService.saveDropSaveAndSaveMember();

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("beforeDropTransaction", "fromDropTransactionService",
                "afterDropTransaction");
    }

    @Test
    void saveDropSaveAndThrow() {
        try {
            transactionService.saveDropSaveAndThrow();
        } catch (Exception e) {
            // ignore
        }

        List<String> result = memberRepository.findAll()
                .stream()
                .map(Member::getName)
                .toList();

        assertThat(result).containsExactly("beforeDropTransaction", "fromDropTransactionService");
    }
}
