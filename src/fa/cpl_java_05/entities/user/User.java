package fa.cpl_java_05.entities.user;

public class User {

    private Integer id;
    private String username;
    private String password;
    private Boolean deleted;
    // constructor
    public User() {

    }

    public User(Integer id, String username, String password, Boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_name) {
        this.username = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
