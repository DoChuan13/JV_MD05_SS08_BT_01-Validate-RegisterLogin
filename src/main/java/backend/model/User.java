package backend.model;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_userName", columnNames = "userName"),
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")})
public class User extends ImplicitNamingStrategyJpaCompliantImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 300)
    private String name;
    @NotEmpty
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 20)
    private String userName;
    @NotEmpty
    @Column(nullable = false, length = 30)
    private String email;
    @NotEmpty
    @Size(min = 8, max = 20)
    @Column(nullable = false, length = 20)
    private String password;

    @Column(columnDefinition = "bit default 0")
    private boolean status;
    @Column(columnDefinition = "varchar(300) default 'Avatar'")
    private String avatar;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_ur_user_id"), name = "user_Id"),
               inverseJoinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_ur_role_id"), name = "role_Id"))
    private Set<Role> roleSet = new HashSet<>();

    public User() {
    }

    public User(
            Long id, String name, String userName, String email, String password, boolean status, String avatar,
            Set<Role> roleSet) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.avatar = avatar;
        this.roleSet = roleSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", userName='" + userName + '\'' + ", email='" +
               email + '\'' + ", password='" + password + '\'' + ", status=" + status + ", avatar='" + avatar + '\'' +
               ", roleSet=" + roleSet + '}';
    }
}
