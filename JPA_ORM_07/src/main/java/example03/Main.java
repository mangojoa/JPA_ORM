package example03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {

        try {
            tx.begin();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
