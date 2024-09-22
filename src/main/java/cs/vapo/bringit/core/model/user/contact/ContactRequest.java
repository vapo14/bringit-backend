package cs.vapo.bringit.core.model.user.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema
public class ContactRequest {

    @Schema(name = "contactId", description = "The userId of the contact to add", example = "4873984",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String contactId;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}
