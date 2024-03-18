package cs.vapo.bringit.core.model.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class GetUser {

    @Schema(name = "userId", description = "The user's ID", example = "UID03938798279")
    private String id;

    @Schema(name = "email", description = "The user's email", example = "samus.aran@hunter.com")
    private String email;

    @Schema(name = "username", description = "The user's username", example = "samus388")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String userId) {
        this.id = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
