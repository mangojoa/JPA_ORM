package jpabook.start;

// [22.06.13] 애플리케이션에서 사용할 회원 클래스를 하나 만들어보자
// JPA를 사용하려면 가장 먼저 이를 매핑해야한다.
// 매핑을 하기 위해서는 import 를 먼저 해줘야한다.

// 매핑을 하기 위한 import문
import javax.persistence.*;

/* [22.06.13] 회원 클래스에 매칭 정보를 표시하는 어노테이션에 대해 알아보자
* @Entity
* 이 클래스를 테이블과 매핑한다고 JPA에게 알려주는 어노테이션이다
*
* @Table
* 엔티티 클래스에 매핑할 테이블 정보를 알려준다
* 여기서는 name 속성을 사용해서 Member 엔티티를 MEMBER 테이블에 매핑한다.
*
* @Id
* 엔티티 클래스의 필드를 테이블의 기본키(primary key)에 매핑한다.
* 여기서는 엔티티의 id필드를 테이블의 ID 기본 키 컬럼에 매핑했다.
* 이렇게 @Id가 사용된 필드를 식별자 필드라 한다.
*
* @Column
* 필드를 컬럼에 매핑한다. 여기서는 name 속성을 사용해서 Member 엔티티의 username 필드를
* MEMBER 테이블의 NAME 컬럼에 매핑했다.
*
* 매핑 정보가 없는 필드
* age 필드에는 매핑 어노테이션을 사용하지 않았다.
* 이렇게 매핑 어노테이션을 생략하면 필드명을 사용해서 컬럼명으로 매핑한다.
* (매핑을 하지 않았다고 해서 매핑이 안되는 것은 아니다.)
*
* 만약 대소문자를 구분하는 데이터베이스를 사용하면 @Column(name = "AGE") 처럼 명시적으로 매핑해야 한다.
* */

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    // 매핑 정보가 우선은 필요없는 필드
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
