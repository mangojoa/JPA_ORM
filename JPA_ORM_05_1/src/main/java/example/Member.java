package example;

public class Member {

    // [22.01.03] 순수한 객체 연관관계
    private String id;
    private String username;

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
