package cs.vapo.bringit.core.model.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class PatchUser {

    @Schema(name = "username", description = "The user's username", example = "samus388")
    private String username;

    @Schema(name = "email", description = "The user's email", example = "samus.aran@hunter.com")
    private String email;

    @Schema(name = "password", description = "The user's password", example = "dontusethispasswordplz")
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
