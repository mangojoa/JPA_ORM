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

        3. JPOL 쿼리 실행 시 플러시가 자동 호출된다.
        JPOL이나 Criteria 같은 객체지향 쿼리를 호출할 때도 플러시가 실행된다. 왜 자동으로 실행이 될까??
        먼저 em.persist()를 호출해서 엔티티 Member를 영속 상태로 만들었다.
        이 엔티티는 영속성 컨텍스트ㅔ는 있지만 아직 데이터베이스에는 반영되지 않았다.
        이때 JPOL을 실행하면 JPOL은 SQL로 변환되어 데이터베이스의 엔티티를 조회한다.
        그런데 Member는 아직 데이터베이스에 없으므로 쿼리 결과로 조회되지 않는다.
        따라서 쿼리를 실행하기 직전에 영속선 컨텍스트를 플러시해서 변경 내용을 데이터베이스에 반영해야 한다.
        JPA는 이런 문제를 예방하기 위해 JPOL을 실행할 때도 플러시를 자동 호출한다.
        이로 인해 Member도 쿼리 결과에 포함된다.
        (참고로 식별자를 기준으로 조회하는 find() 메소드를 호출할 때는 플러시가 실행되지 않는다.

        플러스 모드 옵션
        엔티티 매니저에 플러시 모드를 직접 지정하려면 javax.persistence.FlushModeType을 사용하면 된다.
        FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시(기본값)
        FlushModeType.COMMIT : 커밋할 때만 플러시
        플러시모드를 별도로 설정하지 않으면 auto로 동작한다. 따라서 트랜잭션 커밋이나 쿼리 실행시에 플러시를 자동으로 호출한다.

        준영속
        영속 => 준영속 상태변화
        영속성 컨텍스트가 관리하는 영속 상태의 엔티티가 영속성 컨텍스트에서 분리된 것을 준영속 상태라 한다.
        준영속 상태의 엔티티는 영속성 컨텍스트가 제공하는 기능을 사용할 수 없다.

        준영속 상태를 만드는 방법
        1. em.detach(Member);
        더이상 영속성 컨텍스트에게 해당 엔티티를 관리하지 말라는 것이다.
        이 메소드를 호출하는 순간 1차 캐시부터 쓰기 지연 SQL 저장소까지 해당 엔티티를 관리하기 위한 모든 정보가 제거된다.

        2. em.clear();
        em.detach(Member); 가 특정 엔티티 하나를 준영속 상태로 만들었다면 이는 영속성 컨텍스트를 초기화해서
        해당 영속성 컨텍스트의 모든 엔티티를 준영속 상태로 만든다.

        3. em.close();
        영속성 컨텍스트를 종료하면 해당 영속성 컨텍스트가 관리하던 영속 상태의 엔티티가 모두 준영속 상태가 된다.
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
