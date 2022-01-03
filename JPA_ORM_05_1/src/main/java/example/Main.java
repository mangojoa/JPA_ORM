package example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    static EntityManager em = emf.createEntityManager();
    static EntityTransaction tx = em.getTransaction();

    // [22.01.03] 연관관계 사용 (CRUD 사용)
    public static void main(String[] args) {
        try {
            tx.begin();
            testSave();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

    // [22.01.03] 저장
    public static void testSave() {

        // JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속상태여야 한다.
        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("xla1");
        em.persist(team1);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("ghldnjs1");
        member1.setTeam(team1); // 회원 => 팀 참조
        em.persist(member1); // 저장

        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("ghldnjs2");
        member2.setTeam(team1);
        em.persist(member2);

        Team findTeam = member1.getTeam();
    }


}
