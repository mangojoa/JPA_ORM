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
