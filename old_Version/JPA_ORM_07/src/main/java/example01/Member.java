package example01;

import javax.persistence.*;

/* [22.01.04] 다대일 양방향 [N:1 / 1:N]
실선이 연관관계의 주인 / 점선은 연관관계 주인이 아니다.
*/

@Entity
public class Member {

    /* [22.01.05] 다대일 양방향 연관관계 작성
    양방향은 외래 키가 있는 쪽이 연관관계의 주인이다.
    */
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team; // 연관관계 주인 (Member.team)
    /* [22.01.05]
    JPA는 외래 키를 관리할 때 연관관계의 주인만 사용한다.
    주인이 아닌 Team.members는 조회를 위한 JPOL이나 객체 그래프를 탐색할 때 사용한다.
    */

    private String username;

    /* [22.01.05] 양방향 연관관계는 항상 서로를 참조해야 한다.
    어느 한 쪽만 참조하면 양방향 연관관계가 성립하지 않는다.
    항상 서로 참조하게 하려면 연관관계 편의 메소드를 작성하는 것이 좋다.
    Member class => setTeam / Team class => addMember 가 이에 해당한다.
    편의 메소드를 양쪽에 다 작성해서 둘 중 하나만 호출하면 된다.
    */

    public void setTeam(Team team) {
        this.team = team;

        // 무한 루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
