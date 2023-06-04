package backend.model;

import javax.persistence.*;

@Entity(name = "Sample")
@Table(name = "sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_sp_user_id"), name = "user_Id")
    private User user;
}
