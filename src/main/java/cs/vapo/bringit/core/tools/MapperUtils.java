package cs.vapo.bringit.core.tools;

import cs.vapo.bringit.core.dao.model.ItemDM;
import cs.vapo.bringit.core.model.lists.item.Item;

public class MapperUtils {

    private MapperUtils() {

    }

    /**
     * Maps the given {@link Item} information to the corresponding {@link ItemDM} object
     * @param itemInfo the info to map
     * @return the mapped {@link ItemDM} for DB related operations
     */
    public static ItemDM mapItemToItemDM(final Item itemInfo) {
        final ItemDM result = new ItemDM();
        result.setDescription(itemInfo.getDescription());
        result.setImage(itemInfo.getImageUrl());
        result.setName(itemInfo.getName());
        result.setQuantity(itemInfo.getQuantity());
        return result;
    }
}
