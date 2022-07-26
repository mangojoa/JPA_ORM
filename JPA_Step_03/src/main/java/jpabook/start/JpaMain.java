package jpabook.start;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    /* [22.06.20] 영속성 컨텍스트의 특징
    * ## 영속성 컨텍스트와 식별자 값
    * 엔티티를 식별자 값(@Id로 테이블의 기본키와 매핑항 값)으로 구분한다
    * 따라서 영속 상태는 식별자 값이 반드시 있어야 한다.
    * 식별자 값이 없으면 예외가 발생한다.
    *
    * ## 영속성 컨텍스트와 데이터베이스 저장
    * 엔티티를 저장하면 이 엔티티는 언제 데이터베이스에 저장이 되는가?
    * JPA는 보통 트랜젝션을 커밋하는 순간 영속성 컨텍스트에 새로 저장된 엔티티를 데이터베이스에 반영하는데
    * 이것을 flush(플러시)라 한다.
    *
    * ## 영속성 컨텍스트가 엔티티를 관리하면 생기는 이점
    * 1. 1차 캐시
    * 2. 동일성 보장
    * 3. 트렌잭션을 지원하는 쓰기 지연
    * 4. 변경 감지
    * 5. 지연 로딩
    * */

    public static void main(String[] args) {
        /* [22.06.20] 엔티티 매니저 팩토리
        * EntityManagerFactory 를 생성하는 코드이다.
        * 하지만 이는 생성하는데 비용이 매우 많이 들기에 처음에 한번만 생성하는 것이 좋다.
        *
        * 나아가 이는 여러 스레드가 동시에 접근해도 안전하다.
        * 그러므로 다른 스레드 간에 공유해도 된다.
        *
        * EntityManagerFactory 는 어디서 정보를 가져오는건가 ?
        * 이는 persistence.xml의 jpabook에서 호출한다.
        * */
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabook");

        /* [22.06.20] 엔티티 매니저
        * EntityManager는 생성하는 비용은 거의 들지 않는다.
        *
        * 이는 EntityManagerFactory와는 달리 여러 스레드가 동시에 접근하면
        * 동시성 문제가 발생하므로 스레드 간에 절대 공유하면 안 된다.
        * */
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
        /* [22.06.20] 엔티티의 생명주기
        * 비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 상태
        * 영속(managed) : 영속성 컨텍스트에 저장된 상태
        * 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
        * 삭제(removed) : 삭제된 상태
        * */

        /* [22.06.20] 비영속
        * 엔티티 객체를 생성했다. 지금은 순수한 객체 상태이며 아직 저장하지 않은 산태이다.
        * 따라서 이는 영속성 컨텍스트와 관련이 없는 단계이다.
        * */
        String id = "망고대농장";
        /* [22.07.26] 데이터베이스에서 조회
        * 만약 em.find()를 호출했는데 엔티티가 1차 캐시에 없으면 em은 데이터베이스를 조회해서 엔티티를 생성한다.
        * 그리고 1차 캐시에 저장한 후에 영속성 상태의 엔티티를 반환한다.
        *
        * if 현재는 id = "mangojoa" 밖에 1차 캐시와 DB에 존재할때,
        * id = "망고대농장"을 추가하면 1차 캐시에 영속성 상태로 존재하기 않기 때문에 해당 ID를 DB에서도 조회한다.
        * 이때도 없다면 해당 엔티티를 생성한다. 그리고 1차 캐시에 영속성 상태로 보관된다는 개념이다.
        * */

        Member member = new Member();
        member.setId(id);
        member.setUsername("mango Platation");
        member.setAge(28);

        /* [22.06.20] 영속성 컨텍스트
        * 엔티티를 영구 저장하는 환경 이라는 의미이다.
        * 엔티티 매니저로 엔티티를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
        *
        * em.persist(member); 이를 엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에 저장한다 는 의미이다.
        *
        * 이는 논리적인 개념이기에 눈으로 확인할 수 없다.
        * 하지만 영속성 컨텍스트는 엔티티 매니저를 생성할 때 하나 만들어진다.
        * 그리고 엔티티 매니저를 통해서 영속성 컨텍스트에 접슨할 수 있고, 영속성 컨텍스트를 관리할 수 있다.

           [22.06.20] 영속
        * 엔티티 매니저를 통해서 엔티티를 영속성 컨텍스트에 저장했다. 이렇게 영속성 컨텍스트가 관리하는 엔티티를 영속 상태라 한다.
        * 이제 회원 엔티티는 비영속 상태에서 영속 상태가 되었다. 결국 영속 상태라는 것은 영속성 컨텍스트에 의해 관리된다는 뜻이다.
        * */
        em.persist(member);

        member.setAge(20);

        /* [22.06.20] 엔티티 조회
        * 영속성 컨텍스트는 내부에 캐시를 가지고 있는데 이를 1차 캐시라 한다.
        * 영속 상태의 엔티티는 모두 이곳에 저장된다.
        *
        * Member member = em.find(Member.class, id);
        * find()메소드를 보면 파라미터가 (엔티티 클래스의 타입, 조회할 엔티티의 식별자 값) 구성되어 있다.
        *
        * find()를 호출하면 먼저 1차 캐시에서 엔티티를 찾고 만약 찾는 엔티티가 1차 캐시에 없으면 데이터베이스에서 조회한다.
        *
        * 만약 em.find()를 호출했는데 엔티티가 1차 캐기에 없으면 엔티티 매니저는
        * 데이터베이스를 조회해서 엔티티를 생성한다.
        * 그리고 1차 캐시에 저장한 후에 영속 산태의 엔티티를 반환한다.
        *
        * 즉, 한번 부른 이후 엔티티를 다시 조회하면 메모리에 있는 1차 캐시에서 불러온다.
        * 이를 통해 성능상 이점을 누릴 수 있다.
        * */
        Member findMember = em.find(Member.class, id);

        /* [22.07.26] 영속 엔티티의 동일성 보장
         * 각각의 member1, member2에 같은 id 값을 넣어 조회할 때, 여기서 member1 == member2는 참?거짓?
         * em.find(Member.class, id); 를 반복해서 호출해도 영속성 컨텍스트는 1차 캐시에 있는 같은 엔티티 인스턴스를 반환한다.
         * 따라서 둘은 같은 인스턴스고 결과는 당연히 참이다.
         *
         * 따라서 영속성 컨텍스트는 성능상 이점과 엔티티의 동일성을 보장한다.
         *
         * [22.07.26] 동일성과 동등성
         * 동일성 : 실제 인스턴스가 같다. 따라서 참조 값을 비교하는 == 비교하는 값이 같다.
         * 동등성 : 실제 인스턴스는 다를 수 있지만 인그턴스가 가지고 있는 값이 같다. 자바에서 동등성 비교는 equals() 메소드를 구현해야 한다.
         * */
        Member member1 = em.find(Member.class, id);
        Member member2 = em.find(Member.class, id);

        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        List<Member> members =
                em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size = " + members.size());

        /* [22.06.20] 준영속
        * 영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 된다.
        * 특정 엔티티를 준영속 상태로 만들려면 em.detach()를 호출래서 영속성 컨텍스트를 닫거나 em.clear()를 호출해서 영속성 컨텍스트를 초기화해도
        * 영속성 컨텍스트가 관리하던 엔티티는 준영속 상태가 된다.
        * */
        // em.detach(member);

        /* [22.06.20] 삭제
        * 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다.
        * */
        // em.remove(member);
    }

}
