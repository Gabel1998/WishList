package DTO;

//Data transfer object - Vi skal transporterer noget data mellem flere klasser (god dammeldags objekt konstruktor klasse)
public class RegisterDTO {

    private String name;
    private String email;
    private String password;


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
