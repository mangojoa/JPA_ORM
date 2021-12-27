package jpaorm.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class jpaMain {
    public static void main(String[] args) {
        /*
        엔티티 수정 = SQL 수정 쿼리의 문제점

        실수로 등급 정보를 입력하지 않거나, 등급을 변경하는 데 실수로 이름과 나이를 입력하지 않을 수 있다.
        결국 부담스러운 상황을 피하기 위해 수정 쿼리를 상황에 따라 계속해서 추가한다.

        이런 개발 방식의 문제점은 수정 쿼리가 많아지는 것은 물론이고 비즈니스 로직을 분석하기 위해 SQL을 계속 확인해야 한다는 번거로움? 이 발생한다.
        결국 직접적이든 간접적이든 비즈니스 로직이 SQL에 의존하게 된다.

        그럼 변경을 감지 하기 위해선 어떻게 해야 할까???

        이렇게 데이터를 업데이트 하는 JPA가 있다고 가정하자.
        */


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // 영속 엔티티 조회
        Member member = em.find(Member.class, "member");

        // 영속 엔티티 데이터 수정
        Member.setUsername("h1");
        Member.setAge(19);

        em.Updata(member); // 대충 이런 코드를 통해 변경을 감지 할 수 있지 않을까 ?!
        /* 유감이지만 없다 ...
       엔티티의 변경사항을 데이터베이스에 자동으로 반영하는 기능을 '변경 감지'라 한다.
       JPA는 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해둔다. = > 이를 스냅샷이라 한다.
       그리고 플러시 시점에 스냅샷과 엔티티를 비교해서 변경된 엔티티를 찾는다.

       1. 트랜잭션을 커밋하면 엔티티 매니저 내부에서 먼저 플러시가 호출된다.
       2. 엔티티와 스냅샷을 비교해서 변경된 엔티티를 찾는다.
       3. 변경된 엔티티가 있으면 수정 쿼리를 생성해서 쓰기 지연 SQL 저장소에 보낸다.
       4. 쓰기 지연 저장소의 SQL을 데이터베이스에 보낸다.
       5. 데이터베이스 트랜잭션을 커밋한다.

       변경 감지는 영속성 컨텍스트가 관리하는 영속성 상태의 엔티티에만 적용된다.
       => 비영속, 준영속에는 해당되지 않는다.

       이를 통한 장.단점
       JPA의 기본 전략에는 엔티티의 모든 필드를 업데이트 한다. 이는 곳 데이터 전송량이 증사하는 단점으로 이어진다.

       하지만, 모든 필드를 사용하면 수정 쿼리가 항상 같다.(물론 바인딩 되는 데이터는 다르다)
       따라서 어플리케이션 로딩 시점에 수정 쿼리를 미리 생성해두고 재사용할 수 있다.

       데이터베이스에 동일한 쿼리를 보내면 데이터베이스는 이전에 한 번 파싱된 뭐리를 재사용할 수 있다.
        */
        transaction.commit(); // 트랜젝션 커밋


        em.remove(member); // 엔티티 삭제
        /*
        먼저 삭제 대상 엔티티를 조회해야 한다.

        물론 엔티티를 즉시 삭제하는 것이 아니라 엔티티 등록과 비슷하게 삭제 쿼리를 쓰기 지연 SQL 저장소에 등록한다.
        이후 트랜잭션을 커밋해서 플러시를 호출하면 실제 데이터베이스에 삭제 쿼리를 전달한다.
        .. 이렇게 삭제된 엔티티는 재사용하지 말고 자연스럽게 가비지 컬렉션의 대상이 되도록 두는 것이 좋다.
        */

        /*
        플러시 = 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영한다.
        구체적으로 다음과 같은 일이 일어난다.

        1. '변경 감지'가 동작해서 영속성 컨텍스트에 있는 모든 엔티티를 스냅샷과 비교해서 수정된 엔티티를 찾는다.
        수정된 엔티티는 수정 쿼리를 만들어 쓰기 지연 SQL 저장소에 등록한다.
        2. 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(CRUD)

        영속성 컨텍스트를 플러시하는 방법은 3가지다.

        1. em.flush()를 직접 호출한다.
        강제로 플러시 하는 개념이다 . 테스트나 다른 프레임워크와 JPA를 함께 사용할 때를 제외하고 거의 사용하지 않는다.

        2. 트랜잭션 커밋 시 플러시가 자동 호출된다.
        트랜잭션을 커밋하기 전에 꼭 플러시를 호출해서 영속성 컨택스트의 변경 내용을 데이터베이스에 반영해야한다.
        JPA는 이런 문제를 예방하기 위해 트랜잭션을 커밋할 때 플러시를 자동으로 호출한다.

        3. SPOL 쿼리 실행 시 플러시가 자동 호출된다.



        */
    }
    public static void logic(EntityManager em) {

        String id = "id2";
        Member member = new Member();
        member.setId(id);
        member.setUsername("mango");
        member.setAge(20);

        em.persist(member);

        member.setAge(21);

        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());

        em.remove(member);

    }
}
