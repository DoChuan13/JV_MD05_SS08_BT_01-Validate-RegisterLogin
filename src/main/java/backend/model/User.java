package backend.model;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

import javax.persistence.*;
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
    private String name;

    private String userName;
    private String email;
    private String password;
    private String avatar;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_ur_user_id"), name = "user_Id"),
               inverseJoinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_ur_role_id"), name = "role_Id"))
    private Set<Role> roleSet = new HashSet<>();
}
