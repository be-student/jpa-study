package com.bestudent.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bestudent.domain.Member;
import com.bestudent.domain.Study;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@DataJpaTest
class StudyRepositoryTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void 멤버가_여러명_저장된다() {
        Member member = new Member();
        Study study = new Study();
        study.addMember(member);
        Study savedStudy = studyRepository.save(study);
        assertNotNull(savedStudy.getId());
    }

    @Test
    void 스터디가_저장되면_멤버도_저장된다() {
        Member member = new Member();
        Study study = new Study();
        study.addMember(member);
        Study savedStudy = studyRepository.save(study);
        assertNotNull(savedStudy.getMembers().get(0).getId());
    }

    @Test
    void 단일_스레드에서_5명_이상을_저장하려고_시도하면_실패한다() {
        Study study = new Study();
        Study saved = studyRepository.save(study);
        for (int i = 0; i < 5; i++) {
            Study savedStudy = studyRepository.findById(saved.getId()).orElseThrow();
            Member save = memberRepository.save(new Member());
            savedStudy.addMember(save);
            testEntityManager.flush();
            testEntityManager.clear();
        }
        Study savedStudy = studyRepository.findById(saved.getId()).orElseThrow();
        Member save = memberRepository.save(new Member());
        assertThatThrownBy(() -> savedStudy.addMember(save))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("스터디 인원은 5명을 초과할 수 없습니다.");
    }

    @Test
    void 스터디에_멤버가_추가되면_변경이_감지된다() {
        Member member = new Member();
        Study study = new Study();
        study.addMember(member);
        Study savedStudy = studyRepository.save(study);
        savedStudy.addMember(new Member());
        testEntityManager.flush();
        testEntityManager.clear();
        Study updatedStudy = studyRepository.findById(savedStudy.getId()).orElseThrow();
        assertEquals(2, updatedStudy.getMembers().size());
    }

    @Test
    void 여러_스레드에서_5명_이상을_저장하려고_하면_성공한다() throws InterruptedException, ExecutionException {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction = transactionManager.getTransaction(definition);
        Study study = new Study();
        Study saved = studyRepository.save(study);
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        List<Callable<Study>> saveMemberTasks = Arrays.asList(
                saveMember(saved.getId()),
                saveMember(saved.getId()),
                saveMember(saved.getId()),
                saveMember(saved.getId()),
                saveMember(saved.getId()),
                saveMember(saved.getId())
        );
        transactionManager.commit(transaction);
        List<Future<Study>> results = executorService.invokeAll(saveMemberTasks);
        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        DefaultTransactionDefinition asdf = new DefaultTransactionDefinition();
        asdf.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction1 = transactionManager.getTransaction(asdf);
        assertTrue(terminated);
        Thread.sleep(1000);
        List<Member> members = testEntityManager.getEntityManager().createQuery("select m from Member m", Member.class)
                .getResultList();
        System.out.println("checking");
        Study result = studyRepository.findById(saved.getId()).orElseThrow();
        assertEquals(6, result.getMembers().size());
    }

    private Callable<Study> saveMember(long studyId) {
        return () -> {
            TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
            Optional<Study> optionalStudy = studyRepository.findById(studyId);
            Member save = memberRepository.save(new Member());
            Study study = optionalStudy.orElseThrow();
            study.addMember(save);
            studyRepository.save(study);
            transactionManager.commit(transaction);
            testEntityManager.flush();
            testEntityManager.clear();
            System.out.println("save complete");
            return studyRepository.findById(studyId).orElseThrow();
        };
    }
}
