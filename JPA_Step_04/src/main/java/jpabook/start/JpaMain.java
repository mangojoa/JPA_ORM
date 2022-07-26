package jpabook.start;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    /* [22.07.26] 데이터베이스 스키마 자동 생성
    * JPA는 데이터베이스 스키마를 자동으로 생성하는 기능을 지원한다.
    *
    * ?! 그렇다면 어디서 생성할 스키마의 정보를 가져오는 것인가?
    * 이는 Member.jave 파일에 작성된 메핑정보를 기반으로 어떤 테이블에 어떤 컬럼을 사용하는지 알고 있기에 가능하다.
    * */
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabook");

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

        String id = "망고따는누나";

        Member member = new Member();
        member.setId(id);
        member.setUsername("망고플랜테이션");
        member.setAge(28);

        em.persist(member);

        member.setAge(20);

        Member findMember = em.find(Member.class, id);

        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        List<Member> members =
                em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size = " + members.size());

        // em.detach(member);

        // em.remove(member);
    }

}
