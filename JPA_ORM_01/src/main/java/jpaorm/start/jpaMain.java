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

            [21.12.27] 준영속 상태 => 영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속 컨텍스트가 관리하지 않으면
                                    준영속 상태가 된다.
            특정 엔티티를 준영속 상태로 만들려면 em.detach()를 호출하면 된다.
            em.close()를 호출해서 영속 컨텍스트를 닫거나
            em.clear()를 호출해서 영속 컨텍스트를 초기화해도 된다.
             */
            em.close(); // 엔티티 매니저 => 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 => 종료
    }

    public static void logic(EntityManager em) {

        String id = "id2";
        // [21.12.27] 비영속 상태 => 순수한 객체상태를 이야기하며 저장되지 않은 상태
        Member member = new Member();
        member.setId(id);
        member.setUsername("mango");
        member.setAge(20);

        /* 등록
        영속성 컨텍스트 => 엔티티를 영구 저장하는 환경
        엔티티 매니저로 엔티티를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
         */

        // [21.12.27] 영속 상태 => 엔티티 매니저를 통해서 엔티티를 영속성 컨텍스트에 저장한 상태.
        em.persist(member); // persist() 메소드는 엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에 저장한다.

        // 수정
        member.setAge(21);

        // 한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());

        // [21.12.27] 삭제 상태 => 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다.
        em.remove(member);

    }
}
