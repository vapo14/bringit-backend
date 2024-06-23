package cs.vapo.bringit.core.model.lists.item;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class Item {

    @Schema(description = "The item name", example = "Coke")
    private String name;

    @Schema(description = "The item description", example = "Make sure it's Diet Coke")
    private String description;

    @Schema(description = "The item quantity, how many the person should bring", example = "2")
    private int quantity;

    @Schema(description = "The item's image url / icon url")
    private String imageUrl;

    /* TODO: add assignee user object, we want the item to have a preview of the assignee's profile as well as their
        username information which will display on hover.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
