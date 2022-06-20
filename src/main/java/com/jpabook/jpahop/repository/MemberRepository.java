package com.jpabook.jpahop.repository;

import com.jpabook.jpahop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository //Spring이 Component를 Scan해서 Bean으로 등록 - 내부에 @Component 포함하고 있음.
@RequiredArgsConstructor
public class MemberRepository {

    /* // Lombok의 @RequiredArgsConstructor를 활용해서 대체
    @PersistenceContext //JPA가 제공하는 표준 Annotation
    private EntityManager em; //Spring이 EntityManager을 주입
    */

    private EntityManager em; // Lombok의 @RequiredArgsConstructor에 의해 생성자 자동생성

    /* //EntityManagerFactory를 직접 주입하고 싶은 경우
    @PersistenceUnit //EntityManagerFactory 직접 주입
    private EntityManagerFactory emf;
    */

    public void save(Member member) {
        /* em.persist를 하면 영속성 context에 member 객체를 등록 */
        em.persist(member); //Transaction이 commit되는 시점에 DB에 Insert Query 수행
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)   //JPQL - Entity 객체를 대상으로 Query실행
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
