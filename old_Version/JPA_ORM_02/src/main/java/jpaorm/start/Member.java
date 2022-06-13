package jpaorm.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@org.hibernate.annotations.DynamicUpdate // hibernate 확장 기능을 사용해야 한다.
// @org.hibernate.annotations.DynamicUpdate를 사용하면 수정된 데이터만 사용해서 동적으로 UPDATE SQL을 생성한다.
// 참고로 데이터를 저장할 때 데이터가 존재하는 필드만으로 INSERT SQL을 동적으로 생성하는 @DynamicInsert도 있다.
@Table(name = "MEMBER")
public class Member {

    @Id // primary key
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

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
