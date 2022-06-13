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

        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();

        try {
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

    // 비즈니스 로직
    private static void logic(EntityManager em) {
        // write logic
    }

}
