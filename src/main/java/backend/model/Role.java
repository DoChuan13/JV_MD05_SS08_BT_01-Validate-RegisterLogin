package backend.model;

import backend.model.enums.RoleName;

import javax.persistence.*;

@Entity(name = "Role")
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role() {
    }

    public Role(Integer id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
               "id=" + id +
               ", roleName=" + roleName +
               '}';
    }
}