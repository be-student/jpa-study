package com.bestudent.embedded.repository;

import com.bestudent.embedded.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
