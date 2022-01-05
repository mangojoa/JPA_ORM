package example04;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id @Column(name = "PRODUCT_ID")
    private String id;

    @ManyToMany(mappedBy = "products") // 역방향 추가
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
