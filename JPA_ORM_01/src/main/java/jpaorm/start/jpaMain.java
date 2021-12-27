package jpaorm.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class jpaMain {
    public static void main(String[] args) {

        /*
        앤티티 매니저 팩토리 생성
        JPA를 시작하려면 Persistence 클래스를 사용하는데 이 클래스틑 엔티티 매니저 팩토리를 생성하는 JPA를 사용할 수 있게 준비한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        이렇게 하면 META-INF/persistence.xml에서 이름이 jpabook인 영속성 유닛 persistence-unit을 찾아서 엔티티 매니저 팩토리를 생성한다.
        !! 엔티티 매니저 팩토리는 어플리케이션 전체에서 딱 한 번만 생성하고 공유해서 사용해야 한다. (비용이 매무 크기 때문에)
        */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        /*
        엔티티 매니저 생성
        엔티티 매니저 팩토리에서 엔티티 매니저를 생성한다. JPA의 기능 대부분은 이 엔티티 매니저가 제공한다.
        대표적으로 엔티티 매니저를 사용해서 엔티티를 데이터베이스에 CRUD를 할 수 있다.
        엔티티 매니저는 내부에 데이터소스를 유지하면서 데이터베이스와 통신한다.
        데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드 간에 공유하거나 재사용하면 안된다.
        */
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 => 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션 시작
            logic(em); // 트랜잭션 실행
            tx.commit(); // 트랜잭션 커밋

        } catch (Exception e) {
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            /*
            마지막으로 사용이 끝난 엔티티 매니저 / 엔티티 매니저 팩토리는 반드시 종료해야한다.
            */
            em.close(); // 엔티티 매니저 => 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 => 종료
    }

    public static void logic(EntityManager em) {

        String id = "id2";
        Member member = new Member();
        member.setId(id);
        member.setUsername("mango");
        member.setAge(20);

        // 등록
        em.persist(member);

        // 수정
        member.setAge(21);

        // 한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());
        /*
        JPA를 사용하면 어플리케이션 개발자는 엔티티 객체를 중심으로 개발하고 데이터 베이스에 대한 처리를 JPA에 맡겨야 한다.
        JPA는 엔티티 객체를 중심으로 개발하므로 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야 한다.
        하지만 문제점이 하나 존재한다. 하나의 데이터를 찾기 위해서 여러 엔티티 불러와서 객체로 변경한 다음 검색해야 하는데 사실상 불가능하다.
        그렇기에 JPA는 JPOL이라는 쿼리 언어로 이런 문제를 해결한다.

        JPOL => SQL을 추상화한 객체지향 쿼리 언어를 제공한다.
        문법은 거의 유사 .
        차이점 => 엔티티 객체를 대상으로 쿼리한다. => 쉽게 말해서 클래스와 필드를 대상으로 쿼리한다.
                 기존은 데이터베이스 테이블을 대상으로 쿼리한다.

                 select m from Member m이 바로 JPOL이다. 여기서 from Member는 회원 엔티티 객체를 말하는 것이지
                 MEMBER 테이블이 아니다. JPOL은 데이터베이스 테이블을 전혀 알지 못한다.

        JPOL을 사용하려면 먼저 EntityManager를 통해 em.createQuery(JPOL, 반환타입) 메소드를 실행해서 쿼리 객체를 생성한 후
        쿼리 객체의 getResultList() 메소드를 호출하면 된다.
        */

        // 삭제
        em.remove(member);

    }
}
