package cs.vapo.bringit.core.model.lists;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request for PATCH method")
public class PatchList {

    @Schema(name = "title", description = "the new list title", example = "My shopping list")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
