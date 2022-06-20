package jpabook.start;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        /* [22.06.20] 엔티티 매니저 팩토리
        * EntityManagerFactory 를 생성하는 코드이다.
        * 하지만 이는 생성하는데 비용이 매우 많이 들기에 처음에 한번만 생성하는 것이 좋다.
        *
        * 나아가 이는 여러 스레드가 동시에 접근해도 안전하다.
        * 그러므로 다른 스레드 간에 공유해도 된다.
        *
        * EntityManagerFactory 는 어디서 정보를 가져오는건가 ?
        * 이는 persistence.xml의 jpabook에서 호출한다.
        * */
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabool");

        /* [22.06.20] 엔티티 매니저
        * EntityManager는 생성하는 비용은 거의 들지 않는다.
        *
        * 이는 EntityManagerFactory와는 달리 여러 스레드가 동시에 접근하면
        * 동시성 문제가 발생하므로 스레드 간에 절대 공유하면 안 된다.
        * */
        EntityManager em = emf.createEntityManager();


        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {

        String id = "mangojoa";
        Member member = new Member();
        member.setId(id);
        member.setUsername("mango Platation");
        member.setAge(28);

        em.persist(member);

        member.setAge(20);

        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());


        List<Member> members =
                em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size = " + members.size());

//        em.remove(member);
    }

}
