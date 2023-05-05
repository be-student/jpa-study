package com.bestudent.jpa.service;

import com.bestudent.jpa.entity.Member;
import com.bestudent.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DropTransactionService {

    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void dropTransaction() {
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void dropTransactionAndSaveMember() {
        memberRepository.save(new Member("fromDropTransactionService"));
    }
}
