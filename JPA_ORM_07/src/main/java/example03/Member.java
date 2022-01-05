package example03;

/* [22.01.05] 일대일 [1:1]
일대일 관계는 양쪽이 서로 하나의 관계만 가진다.
1. 일대일 관계는 그 반대도 일대일 관계다.
2. 테이블 관계에서 일대다, 다대일은 항상 다(N)쪽이 외래 키를 가진다. 반면에 일대일 관계는
   주 테이블이나 대상 테이블 둘 중 어느 곳이나 외래키를 가질 수 있다.

테이블은 주 테이블이든 대상 테이블이든 외래 키 하나만 있으면 양쪽으로 조회할 수 있다.
그리고 일대일 관계는 그 반대쪽도 일대일 관계다.
따라서 일대일 관계는 주 테이블아너 대상 테이블 중에 누가 외래키를 가질지 선택해야 한다.

- 주 테이블에 외래 키
주 객체가 대상 객체를 참조하는 것처럼 주 테이블에 외래 키를 두고 대상 테이블을 참조한다.
외래 키를 객체 참조와 비슷하세 사용할 수 있다. 이 방법의 장점은 주 테이블이 외래 키를 가지고 있으므로
주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있다.

- 대상 테이블에 외래 키
이 방법의 장점은 테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조를 그대로 유지할 수 있다.
*/

import javax.persistence.*;

// [22.01.05] 일대일 양방향 연관관계 - 주 테이블에 외래 키
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @OneToMany
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

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

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}
