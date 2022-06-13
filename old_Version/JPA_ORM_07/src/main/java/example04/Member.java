package example04;

/* [22.01.05] 다대다 [N:N]
관계형 데이터 베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다.
보통 다대다 관계를 일대다, 다대일 관계로 풀어내는 연결 테이블을 사용한다.
다대다 관계를 즉 일대다, 다대일 관계로 재정의 하여 구성하는 방법이다.
우선, 주/대상 테이블의 사이에 연결 테이블을 만들어 풀어낼 수 있다.
*/

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    /* [22.01.05]
    Member 엔티티와 Product 엔티티를 @ManyToMany로 매핑했다.
    @ManyToMany와 @JoinTable을 사용해서 연결 테이블을 바로 매핑한 것이다.
    따라서 Member와 Product을 연결하는 Member_Product 엔티티 없이 매핑을 완료할 수 있다.

    @JoinTable.name => 연결 테이블을 지정한다. 여기서는 MEMBER_PRODUCT 테이블을 선택
    @JoinTable.JoinColumn => 현재 방향인 Member와 매핑할 조인 컬럼 정보를 지정한다. 여기서는 MEMBER_ID로 지정
    @JoinTable.inverseJoinColumns => 반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정한다. 여기서는 PRODUCT_ID로 지정

    MEMBER_PRODUCT 테이블은 다대다 관계를 일대다, 다대일 관계로 풀어내기 위해 필요한 연결 테이블일 뿐이다.
    @ManyToMany로 매핑한 덕분에 다대다 관계를 사용할 때는 이 연결 태이블을 신경쓰지 않아도 된다.
    */

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT", joinColumns = @JoinColumn(name = "MEMBER_ID"),
                inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<Product> products = new ArrayList<Product>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // [22.01.05] 연관관계 편의 메소드 추가 (양방향 관리를 위한)
    public void addProduct(Product product) {
        // 무한 루프에 빠지지 않도록 체크
        if (!product.getMembers().contains(this)) {
            product.getMembers().add(this);
        }

        products.add(product);
        product.getMembers().add(this);
    }

    /* [22.01.05]
    실행된 SQL을 보면 연결 테이블인 MEMBER_PRODUCT와 상품 테이블을 조인해서 연관된 상품을 조회한다.
    @ManyToMany 덕분에 복잡한 대다대 관계를 어플리케이션에서는 아주 단순하게 사용할 수 있다.
    */
}
