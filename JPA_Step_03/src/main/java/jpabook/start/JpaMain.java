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
        /* [22.06.20] 엔티티의 생명주기
        * 비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 상태
        * 영속(managed) : 영속성 컨텍스트에 저장된 상태
        * 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
        * 삭제(removed) : 삭제된 상태
        * */

        /* [22.06.20] 비영속
        * 엔티티 객체를 생성했다. 지금은 순수한 객체 상태이며 아직 저장하지 않은 산태이다.
        * 따라서 이는 영속성 컨텍스트와 관련이 없는 단계이다.
        * */
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

        /* [22.06.20] 영속
        * 엔티티 매니저를 통해서 엔티티를 영속성 컨텍스트에 저장했다. 이렇게 영속성 컨텍스트가 관리하는 엔티티를 영속 상태라 한다.
        * 이제 회원 엔티티는 비영속 상태에서 영속 상태가 되었다. 결국 영속 상태라는 것은 영속성 컨텍스트에 의해 관리된다는 뜻이다.
        * */
        em.persist(member);

        member.setAge(20);

        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        List<Member> members =
                em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size = " + members.size());

        /* [22.06.20] 준영속
        * 영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 된다.
        * 특정 엔티티를 준영속 상태로 만들려면 em.detach()를 호출래서 영속성 컨텍스트를 닫거나 em.clear()를 호출해서 영속성 컨텍스트를 초기화해도
        * 영속성 컨텍스트가 관리하던 엔티티는 준영속 상태가 된다.
        * */
        // em.detach(member);

        /* [22.06.20] 삭제
        * 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다.
        * */
        // em.remove(member);
    }

}
