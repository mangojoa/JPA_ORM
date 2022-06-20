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

        /* [22.06.20] 영속성 컨텍스트
        * 엔티티를 영구 저장하는 환경 이라는 의미이다.
        * 엔티티 매니저로 엔티티를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
        *
        * em.persist(member); 이를 엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에 저장한다 는 의미이다.
        *
        * 이는 논리적인 개념이기에 눈으로 확인할 수 없다.
        * 하지만 영속성 컨텍스트는 엔티티 매니저를 생성할 때 하나 만들어진다.
        * 그리고 엔티티 매니저를 통해서 영속성 컨텍스트에 접슨할 수 있고, 영속성 컨텍스트를 관리할 수 있다.
        * */
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
