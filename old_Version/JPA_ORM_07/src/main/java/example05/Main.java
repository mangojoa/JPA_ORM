package example05;

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
            // TODO: logic
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public void save() { // 저장
        Member member1 = new Member();
        member1.setUsername("ghldnjs1");
        em.persist(member1);

        Product productA = new Product();
        productA.setName("tkdvna1");
        em.persist(productA);

        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);   // 주문회원 => 연관관계 설정
        memberProduct.setProduct(productA); // 주문상품 => 연관관계 설정
        memberProduct.setOrderAmount(2);    // 주문수량

        em.persist(memberProduct);

    }

    public void find() { // 조회

        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("tkdvna1");

        MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);

        Member member = memberProduct.getMember();
        Product product = memberProduct.getProduct();

        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount = " + memberProduct.getOrderAmount());

    }

}
