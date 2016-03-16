package be.ghostwritertje.budgetting.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Rekening> rekeningen = new ArrayList<Rekening>();

    @Column
    private String username;

    public User() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Rekening> getRekeningen() {
        return rekeningen;
    }

    public void setRekeningen(List<Rekening> rekeningen) {
        this.rekeningen = rekeningen;
    }
}
