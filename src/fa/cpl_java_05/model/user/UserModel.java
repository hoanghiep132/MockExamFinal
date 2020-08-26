package fa.cpl_java_05.model.user;

public class UserModel {

    private Integer id;
    private String username;
    private String password;
    private Boolean deleted;
    private Boolean role;
    // constructor
    public UserModel() {

    }

    public UserModel(Integer id, String username, String password, Boolean deleted, Boolean role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deleted = deleted;
        this.role = role;
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

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }
}
