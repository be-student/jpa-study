package com.bestudent.jpa.service;

import com.bestudent.jpa.entity.Member;
import com.bestudent.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final MemberRepository memberRepository;
    private final DropTransactionService dropTransactionService;

    @Transactional
    public void transactionSaveDropSave() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransaction();
        memberRepository.save(new Member("afterDropTransaction"));
    }

    @Transactional
    public void transactionSaveDropThrow() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransaction();
        throw new RuntimeException();
    }

    public void saveDropSave() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransaction();
        memberRepository.save(new Member("afterDropTransaction"));
    }

    public void saveDropThrow() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransaction();
        throw new RuntimeException();
    }

    @Transactional
    public void transactionSaveDropSaveAndSaveMember() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransactionAndSaveMember();
        memberRepository.save(new Member("afterDropTransaction"));
    }

    @Transactional
    public void transactionSaveDropSaveAndThrow() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransactionAndSaveMember();
        throw new RuntimeException();
    }

    public void saveDropSaveAndSaveMember() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransactionAndSaveMember();
        memberRepository.save(new Member("afterDropTransaction"));
    }

    public void saveDropSaveAndThrow() {
        memberRepository.save(new Member("beforeDropTransaction"));
        dropTransactionService.dropTransactionAndSaveMember();
        throw new RuntimeException();
    }
}
