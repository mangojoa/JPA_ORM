package example05;

/* [22.01.05] 다대다: 매핑의 한계와 극복, 연결 엔티티 사용
@ManyToMany를 사용하면 연결 테이블을 자동으로 처리해주므로 도메인 모델이 단순해지고 여러 가지로 편리하다.
하지만 이 매핑을 실무에서 사용하기에는 한계가 있다. 이는 연결 테이블에 컬럼이 추가되는 경우이다.

컬럼이 추가되면 @ManyToMany를 더이상 사용할 수 없다.
왜냐하면 다른 엔티티에서 추가한 컬럼들을 매핑할 수 없기 때문이다.
결국 연결 테이블을 매핑하는 연결 엔티티를 만들고 이곳에 추가한 컬럼들을 매핑해야 한다.
그렇기에 지금부터는 다대다 였던 구조를 다대일, 일대다 구조로 다시 재구축을 해야한다.
*/

import javax.persistence.*;
import java.util.List;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    /* [22.01.05] 역방향
    회원과 회원상품을 양방향 관계로 만들었다
    회원상품 엔티티 쪽이 외래키를 가지고 있으므로 연관관계의 주인이다.
    그렇기에 연관관계의 주인이 아닌 회원의 Member.memberProducts에는 mappedBy를 사용
    */
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts;

    public List<MemberProduct> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProduct> memberProducts) {
        this.memberProducts = memberProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
