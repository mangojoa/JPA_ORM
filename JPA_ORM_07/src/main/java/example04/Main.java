package example04;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction tx = em.getTransaction();

    public static void main(String[] args) {

        try {
            tx.begin();
            testSave(em);
            find(em);
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void testSave(EntityManager em) { // 저장
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("tkdvna1");
        em.persist(productA);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("ghldnjs1");
        member1.getProducts().add(productA); // 연관관계 설정
        em.persist(member1);

    }

    public static void find(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        List<Product> products = member.getProducts(); // 객체 그래프 탐색
        for (Product product : products) {
            System.out.println("product.name = " + product.getName());
        }
        // 결과 : product.name = productA
    }

}
