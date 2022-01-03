package example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    static EntityManager em = emf.createEntityManager();
    static EntityTransaction tx = em.getTransaction();

    // [22.01.03] 연관관계 사용 (CRUD 사용)
    public static void main(String[] args) {
        try {
            tx.begin();
            testSave();

            // [22.01.03] 조회 (객체 그래프 탐색)
            Member member = em.find(Member.class, "member1");
            Team team = member.getTeam();
            System.out.println("team name = " + team.getName());

            // 출력결과 : team name = xla1
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

    // [22.01.03] JPOL 조인 검색
    public static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member m join m.team t where " + "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "xla1").getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }

        // 결과 : [query] member.username = ghldnjs1
        // 결과 : [query] member.username = ghldnjs2
    }


}
