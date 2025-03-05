package cs.vapo.bringit.core.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema
public class CreateUser {

    @Schema(name = "username", description = "The user's username", example = "samus388")
    @NotBlank(message = "Username cannot be blank")
    @Length(message = "Username length must be between 6 and 15 characters", min = 6, max = 15)
    private String username;

    @Schema(name = "email", description = "The user's email", example = "samus.aran@hunter.com")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(name = "password", description = "The user's password", example = "dontusethispasswordplz")
    @NotBlank(message = "Password cannot be blank")
    @Length(message = "Password length must be between 8 and 45 characters", min = 8, max = 45)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
