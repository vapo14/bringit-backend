package cs.vapo.bringit.core.model.lists;

import cs.vapo.bringit.core.model.lists.item.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Schema
public class CreateList {

    @Schema(description = "The list title / name", example = "My List")
    @NotBlank
    private String title;

    @Schema(description = "The event date where participants are expected to bring their assigned items", example = "2024-06-20")
    @NotNull
    private LocalDate eventDate;

    @Schema(description = "The list of items, can be null or empty")
    @Nullable
    private List<Item> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    @Nullable
    public List<Item> getItems() {
        return items;
    }

    public void setItems(@Nullable List<Item> items) {
        this.items = items;
    }
}
