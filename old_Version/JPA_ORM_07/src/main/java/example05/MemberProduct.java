package example05;

import javax.persistence.*;

@Entity
@IdClass(MemberProduct.class)
/* [22.01.05] 복합키
@IdClass 를 사용해서 복합 기본 키를 매핑

복합 기본 키??
회원상품 엔티티는 기본 키가 MEMBER_ID, PRODUCT_ID로 이루어진 복합 기본키다.
JPA에서 복합 키를 사용하려면 별도의 식별자 클래스를 만들어야 한다. 그리도 엔티티에 @IdClass를 사용해서
식별자 클래스를 지정하면 된다. MemberProductId 클래스를 복합 키를 위한 식별자 클래스로 사용한다.
*/
public class MemberProduct {

    /* [22.01.05] 식별관계
    회원상품은 회원과 상품의 기본 키를 받아서 자신의 기본 키로 사용한다.
    이렇게 부모 테이블의 기본 키를 받아서 자신의 기본 키 + 외래 키로 사용하는 것을 식별관계라고 한다.
    */

    @Id @GeneratedValue
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // MemberProductId.member와 연결

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; // MemberProductId.product와 연결

    private int orderAmount;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
