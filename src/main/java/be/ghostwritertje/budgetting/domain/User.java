package be.ghostwritertje.budgetting.domain;


/**
 * Created by jorandeboever
 * on 16/03/16.
 */

public class User {

    private Integer Id;


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
