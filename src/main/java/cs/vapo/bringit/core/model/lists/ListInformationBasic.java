package cs.vapo.bringit.core.model.lists;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema
public class ListInformationBasic {

    @Schema(description = "The list title", example = "My List")
    private String title;

    @Schema(description = "The count of items in a list", example = "4")
    private String itemCount;

    @Schema(description = "The list's owner profile picture URL", example = "https://example.com/img-path")
    private String ownerImgUrl;

    @Schema(description = "The participant profile picture URLs")
    private List<String> participantImgUrls;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
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
}
