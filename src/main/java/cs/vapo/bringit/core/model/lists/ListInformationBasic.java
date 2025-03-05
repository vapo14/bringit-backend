package cs.vapo.bringit.core.model.lists;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

/**
 * This model is meant to be used for displaying the user's list at a high level view.
 */
@Schema
public class ListInformationBasic {

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

    @Schema(description = "The participant profile picture URLs")
    private List<String> participantImgUrls;

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

    public String getOwnerImgUrl() {
        return ownerImgUrl;
    }

    public void setOwnerImgUrl(String ownerImgUrl) {
        this.ownerImgUrl = ownerImgUrl;
    }

    public List<String> getParticipantImgUrls() {
        return participantImgUrls;
    }

    public void setParticipantImgUrls(List<String> participantImgUrls) {
        this.participantImgUrls = participantImgUrls;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
}
