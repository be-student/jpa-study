package com.bestudent.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.bestudent.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 멤버를_저장할_수_있다(){
        Member member = new Member();
        Member savedMember = memberRepository.save(member);
        assertNotNull(savedMember.getId());
    }
}
