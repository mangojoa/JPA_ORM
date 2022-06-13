package example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    // [22.01.03] 양방향 연관관계 매핑
    @Id @Column(name = "TEAM_ID")
    private String id;

    private String name;

    // [22.01.03] 단방향 연관관계 매핑과는 달리 추가되는 부분이 있다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();
    /*
    팀과 회원은 일대다 관계다. 팀 엔티티에 컬랙션인 List<Member> members를 추가했다.
    그리고 일대다 관계를 매핑하기 위해 @OneToMany 매핑 정보를 사용했다.
    mappedBy 속성은 양방향 매핑일 때 사용하는데 반대쪽 매핑의 필드 이름을 값으로 주면 된다.
    반대쪽 매핑이 Member.team이므로 team을 값으로 주었다.
    */

    /* [22.01.03] 연관관계 주인
    mappedBy의 필요성

    객체에는 양방향 연관관계라는 것이 없다. 서로 다른 단방향 연관관계 2개를 어플리케이션 로직으로 잘 묶어서 양방향인 것처럼 보이게 할 뿐이다.
    반면에 데이터베이스 테이블은 왜래 키 하나로 양쪽이 서로 조인할 수 있다. 이를 통해 양방향 연관관걔를 맺는다.

    이를 토대로 보면 객체와 테이블 사사이에 차이가 발행하는 것 알 수 있다.
    이런 차이로 인해 JPA에서는 두 댇체 연관관계 중 하나를 정해서 테이블의 왜래 키를 관리해야 하는데 이것을 연관관계 주인(Owner)이라 한다.

    양방향 매핑의 규칙: 연관관계의 주인
    연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래 키를 관리(CRUD)할 수 있다. 반면에 주인이 아닌 쪽은 읽기만 할 수 있다.
    - 주인(Owner)은 mappedBy 속성을 사용하지 않는다.
    - 주인이 아니면 mappedBy 속성을 사용해서 속성의 값으로 연관관계의 주인을 지정해야 한다.

    연관관계의 주인을 정한다는 것은 사실 외래 키 관리자를 선택하는 것이다.
    연관관계의 주인은 테이블에 외래 키가 있는 곳으로 정해야 한다.
    여기서 회원 테이블이 외래 키를 가지고 있으므로 Member.team이 주인이 된다.
    주인이 아닌 Team.members에는 mappedBy="team" 속성을 사용해서 주인이 아님을 설정한다.
    여기서 mappedBy의 값으로 사용된 team은 연관관계의 주인인 Member 엔티티의 team 필드를 의미한다.
    */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
