package com.bestudent.repository;

import com.bestudent.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();
}
