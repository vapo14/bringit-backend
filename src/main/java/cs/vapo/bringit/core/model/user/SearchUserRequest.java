package cs.vapo.bringit.core.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema
public class SearchUserRequest {

    @Schema(name = "username", description = "The username to search for", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
