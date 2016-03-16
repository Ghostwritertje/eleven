package be.ghostwritertje.budgetting.domain;

import javax.persistence.*;

/**
 * Created by jorandeboever
 * on 16/03/16.
 */
@Entity
@Table(name = "t_user")
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "username")
    private String username;

    public User() {
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
}
