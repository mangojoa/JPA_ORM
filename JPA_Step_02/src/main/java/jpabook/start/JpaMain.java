package jpabook.start;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        /* [22.06.13] [엔티티 매니저 팩토리] - 생성
        * 우선 persistence.xml의 설정 정보를 사용해서 엔티티 매니저 팩토리를 생성해야한다.
        * 이때 Persistence 클래스를 사용하는데 이 클래스는 엔티티 매니저 팩토리를 생성해서 JPA를 사용할 수 있게 준비한다.
        * */
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabool");
        /* META-INF/persistence.xml에서 이름이 jpabook인 영속성 유닛 persistence-unit을 찾아서 엔티티 매니저 팩토리를 생성한다.
        * 따라서 엔티티 매니저 팩토리는 애플리케이션 전체에서 "딱 한번만 생성하고 공유해서 사용해야 한다."
        * */

        /* [22.06.13] [엔티티 매니저] - 생성
        * 엔티티 매니저 팩토리에서 엔티티 매니저를 생성한다. JPA의 기능 대부분은 이 엔티티 매니저가 제공한다.
        * 대표적으로 엔티티 매니저를 사용해서 엔티티를 데이터베이스에 등록/수정/삭제/조회할 수 있다.
        * 참고로 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드간에 공유하거나 재사용하면 안된다.
        * */
        EntityManager em = emf.createEntityManager();

        /* [22.06.14] [트랜잭션] - 획득
        * JPA를 사용하면 항상 트랜젝션 안에서 데이터를 변경해야 한다. 트랜잭션 없이 데이터를 변경하면 예외가 발생한다.
        * 트랜젝션을 시작하려면 엔티티 매니저에서 트랜잭션 API를 받아야한다.
        *  */
        EntityTransaction tx = em.getTransaction();

        try {
            // 트랜잭션 API를 사용해서 비즈니스 로직이 정상 동작하면 트랜잭션을 커밋하고 예외가 발생하면 롤백한다.
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            /* [22.06.13] 종료
            * 마지막으로 사용이 끝난 엔티티 매니저는 다음처럼 반드시 종료해야 한다.
            * */
            em.close();
        }
        /* [22.06.13] 애플리케이션을 종료할 때 엔티티 매니저 팩토리도 다음처럼 종료해야 한다.
        */
        emf.close();
    }

    /* [22.06.14] 비즈니스 로직
    * 회원 엔티티를 하나 생성한 다음 엔티티 매니저를 통해 데이터베이스에 등록, 수정, 삭제, 조회한다.
    * */
    private static void logic(EntityManager em) {
        /* [22.06.14] 등록하기 전...
        * 엔티티를 저장하려면 엔티티 매니저의 persist() 메소드에 저장할 엔티티티 넘겨주면 된다.
        * */
        String id = "mangojoa";
        Member member = new Member();
        member.setId(id);
        member.setUsername("mango Platation");
        member.setAge(28);

        /* 등록
        * JPA는 회원 엔티티의 매핑 정보(어노테이션)를 분석해서 다음과 같은 SQL을 실행한다.
        * insert into member (id, name, age) values ('mangojoa', 'mango Platation', 28);
        * */
        em.persist(member);

        /* 수정
        * 엔티티를 수정한 후에 수정 내용을 반영하려면
        * em.update() 와 같은 메소드를 호출해야 할것 같지만 아직 JPA를 몰라서 이런 생각이 들 수 있다.
        * JPA는 어떤 엔티티가 변경되었는지 추적하는 기능을 갖추고 있다.
        * 그렇기에 member.setAge(20) 처럼 엔티티의 값만 변경하면 다음과 같은 UPDATE SQL을 생성해서 데이터 베이스에 값을 변경한다.
        * update member set age=20, name='망고대농장' where ID='mangojoa';
        * */
        member.setAge(20);

        /* 한 건 조회
        * find() 메소드는 조회할 엔티티 타입과 @Id로 데이터베이스 테이블의 기본 키와 매핑한 식별자 값으로 엔티티 하나를 조회하는 가장 단순한 조회 메소드이다.
        * select * from member where ID="mangojoa";
        * */
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        /* 목록 조회(JPQL)
        * JPA를 사용하면 애플리케이션 개발자는 엔티티 객체를 중심으로 개발하고 데이터베이스에 대한 처리는 JPA에 맡겨야 한다.
        * 바로 앞에서 사용한 모든 과정들은 SQL을 전혀 사용하지 않는다.
        *
        * 문제는 검색 쿼리에 있다.
        * JPA는 엔티티 객체를 중심으로 개발하므로 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야 한다.
        *
        * 그런데 테이블이 아닌 엔티티 객체를 대상으로 검색하려면 데이터베이스의 모든 데이터를 애플리케이션으로 불러와서
        * 엔티티 객체로 변경한 다음 검색해야 하는데. 이는 사실상 불가능하다.
        *
        * 애들리케이션이 필요한 데이터만 데이터베이스에서 불러오려면 결국 검색 조건이 포함된 SQL을 사용해야 한다.
        * JPA는 JPQL이라는 쿼리 언어로 이런 문제를 해결한다.
        *
        * JPA는 SQL을 추상화한 JPQL이라는 객체지향 쿼리 언어를 제공한다.
        * JPQL은 SQL과 문법이 거의 유사하다. (SELECT, FROM, WHERE, GROUP, BY, HAVING, JOIN)
        *
        * 하지만 유사할 뿐 차이점은 존재한다.
        * JPQL은 엔티티 객체를 대상으로 쿼리한다. 쉽게 이야기해서 클래스와 필드를 대상으로 쿼리한다.
        * SQL은 데이터베이스 테이블을 대상으로 쿼리한다.
        *
        * em.createQuery("select m from Member m", Member.class).getResultList();
        * 여기에서 "select m from Member m"이 바로 JPQL이다.
        * 여기서 from Member는 회원 엔티티 객체를 말하는 것이지 MEMBER 테이블이 아니다.
        * JPQL은 데이터베이스 테이블을 전혀 알지 못한다.
        *
        * JPQL을 사용하려면 먼저 em.createQuery(JPQL, 반환타입) 메소드를 실행해서
        * 쿼리 객체를 생성한 후 쿼리 객체의 getResultList() 메소드를 호출하면 된다.
        * */
        List<Member> members =
                em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size = " + members.size());

        /* 삭제
        * 엔티티를 삭제하려면 엔티티 매니저의 remove() 메소드에 삭제하려는 엔티티를 넘겨준다.
        * delete from member where ID='mongojoa';
        * */
//        em.remove(member);
    }

}
