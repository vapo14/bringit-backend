package cs.vapo.bringit.core.model.lists;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class CreateList {

    @Schema(description = "The list title / name", example = "My List")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
