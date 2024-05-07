package cs.vapo.bringit.core.model.lists;

import cs.vapo.bringit.core.model.lists.item.Item;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema
public class CreateList {

    @Schema(description = "The list title / name", example = "My List")
    private String title;

    // TODO: should the list creation process be divided and handled on the backend ? sort of like a 'cart' feature ?
    @Schema(description = "The list items")
    private List<Item> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
