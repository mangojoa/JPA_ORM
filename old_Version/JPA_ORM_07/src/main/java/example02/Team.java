package example02;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* [22.01.05] 일대다 관계
일대다 관계는 다대일 관계의 반대 방향이다. 엔티티를 하나 이상 참조할 수 있으므로
자바 컬렉션인 Collection, List, Set, Map 중에 하나를 사용해야 한다.

일대다 단방향[1:N]
약간 특이하게 보통 자신이 매핑한 테이블의 왜래키를 관리하는데,
이 매핑은 반대쪽 테이블에 있는 외래 키를 관리한다.

그럴 수밖에 없는 것이 일대다 관계에는 외래 키는 항상 다쪽 테이블에 있다.
하지만 다 쪽인 엔티티에는 외래키를 매필할 수 있는 참조 필드가 없다.
대신에 반대쪽 1 엔티티에만 참조필드가 있어 특이한 모습이 나타난다.
*/

/* [22.01.05] 일대다 양방향 [1:N / N:1]
일대다 양방향 매핑은 존재하지 않난다. 대신 다대일 양방향 매핑을 사용해야 한다
양방향 매핑에서 @OneToMany는 연관관계의 주인이 될 수 없다.
왜냐하면 관계형 데이터베이스의 특성상 일대다, 다대일 관계는 항상 다 쪽에 외래 키가 있다.
따라서 @OneToMany, @ManyToOne 둘 중에 연관관계의 주인은 항상 다 쪽인 @ManyToOne을 사용한 곳이다.
이런 이유로 @ManyToOne에는 mappedBy 속성이 없다.

그렇다고 일대다 양방향 매핑이 완전히 불가능한 것은 아니다.
일대다 단방향 매핑 반대편에 같은 외래키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 하나 추가하면 된다!
*/

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID") // MEMBER 테이블의 TEAM_ID (FK)
    private List<Member> members = new ArrayList<Member>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
