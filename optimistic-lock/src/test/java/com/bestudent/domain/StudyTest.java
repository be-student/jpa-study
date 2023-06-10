package com.bestudent.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters"})
@DisplayNameGeneration(ReplaceUnderscores.class)
class StudyTest {

    @Test
    void 스터디를_생성할_수_있다() {
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    void 스터디는_멤버를_여러명_가질_수_있다() {
        Study study = new Study();
        Member member1 = new Member();
        Member member2 = new Member();
        study.addMember(member1);
        study.addMember(member2);
        assertEquals(2, study.getMembers().size());
    }

    @Test
    void 스터디는_멤버_6명이_넘어가면_예외가_발생한다() {
        Study study = new Study();
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        Member member4 = new Member();
        Member member5 = new Member();
        Member member6 = new Member();
        study.addMember(member1);
        study.addMember(member2);
        study.addMember(member3);
        study.addMember(member4);
        study.addMember(member5);
        assertThatThrownBy(() -> study.addMember(member6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("스터디 인원은 5명을 초과할 수 없습니다.");
    }
}
