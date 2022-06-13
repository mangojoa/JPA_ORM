package example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    // [22.01.04] 기본 EntityManagerFatory 설정 선언
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {

        try {

        } catch (Exception e) {
            tx.begin();

            // TODO: Logic !!
            save(em); // 이런식으로 설정하면 된다.

            tx.commit();
        } finally {
            tx.rollback();
            em.close();
        }
        emf.close();
    }

    public static void save(EntityManager em) {

    }

    public static void search(EntityManager em) {

    }

    public static void updata(EntityManager em) {

    }

    public static void delete(EntityManager em) {

    }
}
