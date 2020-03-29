package app.jwt;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    @Value("{jwt.serialuid}")
    private static long serialVersionUid;

    private String email, password;

    public JwtRequest() { }

    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
