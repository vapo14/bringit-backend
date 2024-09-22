package cs.vapo.bringit.core.model.lists;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema
public class CreateList {

    @Schema(description = "The list title / name", example = "My List")
    @NotBlank
    private String title;

    @Schema(description = "The event date where participants are expected to bring their assigned items", example = "2024-06-20")
    @NotNull
    private LocalDate eventDate;

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
}
