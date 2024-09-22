package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.item.ItemDataService;
import cs.vapo.bringit.core.model.lists.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for operations on specific items.
 */
@Service
public class ItemService {

    private ItemDataService dataService;

    @Autowired
    public ItemService(final ItemDataService dataService) {
        this.dataService = dataService;
    }

    public void editItem(final Item itemInfo) {

    }
}
