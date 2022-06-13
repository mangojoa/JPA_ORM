package jpaorm.start;

import javax.persistence.*;

@Entity // 클래스를 테이블과 매핑한다고 JPA에게 알려준다. @Entity가 사용된 클래스를 엔티티 클래스라고 한다.
@Table(name = "MEMBER") // 엔티티 클래스에 매핑할 테이블 정보를 알려준다.
// 여기서는 name 속성을 사용해 Member 엔티티를 MEMBER 테이블에 매핑한다.
public class Member {

    @Id // primary key
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    // 매핑정보가 없는 필드
    // age 필드에는 매핑 어노테이션이 없다. 이렇게 매핑 어노테이션을 생략하면
    // 필드명을 사용해서 컬럼명으로 매핑한다.
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
