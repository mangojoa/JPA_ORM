package example02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    // JPA 기본 선언
    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpabook");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {

        try {
            tx.begin();
            testSave(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    /* [22.01.05] 일대다 단방향 매핑의 단점
    매핑한 객체가 관리하는 외래키가 다른 테이블에 있다는 점이다.
    본인 테이블에 외래키가 있으면 엔티티의 저장과 연관관계 처리를 insert sql 한 번으로
    끝낼 수 있지만, 다른 테이블에 외래 키가 있으면 연관관계 처리를 위한 update sql을
    추가로 실행해야 한다.
    */
    public static void testSave(EntityManager em) {
        Member member1 = new Member();
        member1.setUsername("member1");

        Member member2 = new Member();
        member2.setUsername("member2");

        Team team1 = new Team();
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1);
        em.persist(member2);
        em.persist(team1);

        tx.commit();

        /* [22.01.05] 일대다 매핑의 결과
        Member 엔티티를 저장할 때는 MEMBER 테이블의 TEAM_ID 외래 키에 아무 값도 저장되지 않는다.
        대신 Team 엔티티를 저장할 때 Team.members의 참조 값을 확인해서 Member 테이블에 있는
        TEAM_ID 외래 키를 업데이트 한다.

        일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자.
        일대다 단방향 매핑을 사용하면 엔티티를 매핑한 테이블이 아닌 다른 테이블의 외래 키를 관리해야 한다.
        성능 상에도 문제가 있지만 관리도 부담스럽다. 이보다 좋은 방법은 다대일 양방향 매핑을 사용하는 것이다.
        다대일 양방향 매핑은 관리해야 하는 외래 키가 본인 테이블에 있다. 따라서 일대다 단방향 매핑 같은 문제가 발생하지 않는다.
        */
    }


}
