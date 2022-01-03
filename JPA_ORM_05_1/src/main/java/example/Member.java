package example;

import javax.persistence.*;

@Entity
public class Member {

    // [22.01.03] 객체 관계 매핑
    @Id @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // [22.01.03] 연관관계 매핑

    /*
    @ManyToOne 이름 그대로 다대일(N:1) 관계라는 매핑 정보다.
    회원과 팀은 다대일 관계다. 연관관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 필수로 사용해야한다.

    @JoinColumn(name = "TEAM_ID") 조인 컬럼은 외래 키를 매핑할 때 사용한다.
    name 속성에는 매핑할 외래 키 이름을 지정한다. 회원과 팀 테이블은 TEAM_ID 외래 키로 연관 관계를 맺으므로 이 값을 지정하면 된다.
    어노테이션은 생략할 수 있다.
    */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team; // 팀의 참조를 보관

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

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
}
