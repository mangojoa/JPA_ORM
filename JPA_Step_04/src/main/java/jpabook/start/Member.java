package jpabook.start;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age;

    /* [22.07.26] 다양한 매핑을 사용해보자!
    * 회원인 일반 회원과 관리자로 구분해야 한다 => RoleType 추가
    * 회원 가입일과 수정일이 있어야 한다 => createdDate, lastModifiedDate 추가
    * 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이제한이 없다 => description 추가
    * */

    /* 자바의 enum을 사용해서 회원의 타입을 구분
    * 자바의 enum을 사용하려면 @Enumerated 어노테이션을 매핑해야한다.
    * */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /* 자바의 날짜 타입은 @Temporal을 사용해서 구분
    * */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /* 회원을 설명하는 필드는 길이제한이 없다
    따라서 DB의 VARCHAR 타입 대신에 CLOB 타입으로 저장해야 한다.
    @Lob을 사용하면 CLOB, BLOB 타입을 매핑할 수 있다.
    * */
    @Lob
    private String description;

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

enum RoleType {
    ADMIN, USER
}
