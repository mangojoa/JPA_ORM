package example;

public class Main {

    // [22.01.03] 순수한 객체 연관관계의 단방향
    public static void main(String[] args) {

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("ghldnjs1");

        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("ghldnjs2");

        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("xla1");

        member1.setTeam(team1);
        member2.setTeam(team1);

        Team findTeam = member1.getTeam();
    }
}
