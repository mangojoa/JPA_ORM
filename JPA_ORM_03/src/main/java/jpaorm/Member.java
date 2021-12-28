package jpaorm;

import javax.persistence.*;
import java.util.Date;

/*
객체와 테이블 매핑 => @Entity @Table
기본 키 매핑 => @Id
필드와 컬럼 매핑 => @Column
연관관계 매핑 => @ManyToOne @JoinColumn

다음과 같은 조건 추가
1. 회원은 일반 회원과 관리자로 구분해야 한다.
2. 회원 가입일과 수정일이 있어야 한다.
3. 회원을 설면할 수 있는 필드가 있으며 필드 길이는 제한이 없다.
*/

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id // primary key
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age;

    // 조건에 따른 추가부분
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    /*
    자바의 enum을 사용해서 회원의 타입을 구분했다. 이처럼 자바의 enum을 사용하려면 @Enumerated 어노테이션으로 매핑해야한다.
    */

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    /*
    자바의 날짜 타입은 @Temporal을 사용해서 매핑한다.
    */

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    // getter setter 추가
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // 기존 getter setter
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
