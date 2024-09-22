package cs.vapo.bringit.core.model.lists;

import cs.vapo.bringit.core.model.lists.item.Item;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema
public class ListDetails {

    @Schema(description = "The list's identifier")
    private String id;

    @Schema(description = "The list title", example = "My List")
    private String title;

    @Schema(description = "The count of items in a list", example = "4")
    private int itemCount;

    @Schema(description = "The event's date")
    private LocalDate eventDate;

    @Schema(description = "The list's owner profile picture URL", example = "https://example.com/img-path")
    private String ownerImgUrl;

    @Schema(description = "The list's items")
    private List<Item> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getOwnerImgUrl() {
        return ownerImgUrl;
    }

    public void setOwnerImgUrl(String ownerImgUrl) {
        this.ownerImgUrl = ownerImgUrl;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
