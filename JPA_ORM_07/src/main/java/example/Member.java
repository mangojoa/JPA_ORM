package example;

import javax.persistence.*;

@Entity
public class Member {

    /* [22.01.04] 다대일 단방향 연관관계 작성
    회원은 Member.team 으로 팀 엔티티를 참조할 수 있으나
    반대로 팀에는 회원을 참조하는 필드가 없다.
    */
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    /* [22.01.04]
    @JoinColumn(name = "TEAM_ID")를 사용해서
    Member.team 필드를 TEAM_ID 외래 키와 매핑했다.
    따라서 Member.team 필드로 회원 테이블의
    TEAM_ID 외래 키를 관리한다.
    */

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}