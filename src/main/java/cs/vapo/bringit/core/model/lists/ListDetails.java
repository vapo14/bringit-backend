package cs.vapo.bringit.core.model.lists;

import cs.vapo.bringit.core.model.lists.item.Item;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema
public class ListDetails extends ListInformationBasic {

    @Schema(description = "The list's items")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
