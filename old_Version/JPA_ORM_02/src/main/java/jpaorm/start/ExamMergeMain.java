package jpaorm.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {

    // EntityManagerFactory는 한 번만 사용할 것이기에 상수로 고정
    static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        /*
        병합 (Merge)
        준영속 상태의 엔티티를 다시 영속 상태로 변경하려면 병합을 사용해야 한다.
        merge() 메소드는 준영속 상태의 엔티티를 받아서 그 정보로 새로운 영속 상태의 엔티티를 반환한다.
        */

        Member member = createMember("newMember", "mangojoa");

        member.setUsername("mango");

        mergeMember(member);
    }

    static Member createMember(String id, String username) {
        // 영속성 컨텍스트 시작
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        em1.persist(member);
        tx1.commit();

        em1.clear(); // 영속성 컨텍스트 종료
                     // member 엔티티는 준영속 상태
        // 영속성 컨텍스트 종료

        return member;
    }

    static void mergeMember(Member member) {
        // merge 시작
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        Member mergeMember = em2.merge(member);
        tx2.commit();

        // 준영속 상태
        System.out.println("Member = " + member.getUsername());

        // 영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());

        System.out.println("em2 contains member = " + em2.contains(member));
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));

        em2.close();
        // merge 종료
    }
}
