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
            testSave(em);
            queryLogicJoin(em);

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

    // [22.01.03] 저장 / 양방향 연관관계 저장의 주의점
    public static void testSave(EntityManager em) {

        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("xla1");
        em.persist(team1);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("ghldnjs1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("ghldnjs2");
        member2.setTeam(team1);
        em.persist(member2);

        // Team findTeam = member1.getTeam();
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

    // [22.01.03] 연관관계 수정
    public static void updateRelation(EntityManager em) {

        Team team2 = new Team();
        team2.setId("team2");
        team2.setName("xla2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    // [22.01.03] 연관관계 제거 & 삭제
    public static void deleteRelation(EntityManager em) {

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // 연관관계 제거

        /* em.remove(team); 팀 삭제
        연관된 엔티티를 삭제하려면 기존에 있던 연관관계를 먼저 제거하고 삭제해야 한다.
        그렇지 않으면 외래 키 제약 조건으로 인해, 데이터베이스에서 오류가 발생한다.
        */
    }

    // [22.01.03] 일대다 컬랙션 조회
    public void biDirection() {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());

            // 결과 : member.username = ghldnjs1
            // 결과 : member.username = ghldnjs2
        }
    }
}
