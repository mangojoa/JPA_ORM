package example02;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    /* [22.01.05] 일대다 양방향 매핑
    @JoinColumn(name = "TEAM_ID")을 사용하기에 문제가 발생한다.
    그렇기에 읽기만 가능하도록 insertable, updatable = false 설정을 해야한다.
    하지만 고질적인 문제였던 일대다 단방향 매핑이 가지는 단점을 그대로 가지기에 되도록 다대일 양방향 매핑을 이용하자
    */
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
