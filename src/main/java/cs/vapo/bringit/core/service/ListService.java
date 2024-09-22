package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.list.ListDataService;
import cs.vapo.bringit.core.dao.model.ItemDM;
import cs.vapo.bringit.core.dao.model.ListDM;
import cs.vapo.bringit.core.exceptions.BadRequestException;
import cs.vapo.bringit.core.model.lists.CreateList;
import cs.vapo.bringit.core.model.lists.ListInformationBasic;
import cs.vapo.bringit.core.model.lists.item.Item;
import cs.vapo.bringit.core.tools.CurrentUserTools;
import cs.vapo.bringit.core.tools.MapperUtils;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ListService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ListDataService dataService;

    private final ImageGeneratorService imageGeneratorService;

    @Autowired
    public ListService(final ListDataService dataService, final ImageGeneratorService imageGeneratorService) {
        this.dataService = dataService;
        this.imageGeneratorService = imageGeneratorService;
    }

    /**
     * Creates a new list for the current user.
     * @param request the provided list data
     */
    @Transactional
    public void createList(final CreateList request) {
        final long ownerId = CurrentUserTools.retrieveCurrentUserId();
        final ListDM newList = new ListDM();
        newList.setTitle(request.getTitle());
        newList.setEventDate(request.getEventDate());
        dataService.createList(ownerId, newList);
    }

    /**
     * Retrieves the current user's lists
     * @return Lists that belong to the current logged-in user
     */
    @Transactional
    public List<ListInformationBasic> getUserLists() {
        final long ownerId = CurrentUserTools.retrieveCurrentUserId();
        log.info("Retrieving lists for userId: {}", ownerId);
        final List<ListDM> usersLists = dataService.findListsByOwnerId(ownerId);
        return usersLists.stream().map(list -> {
            final ListInformationBasic listInfo = new ListInformationBasic();
            listInfo.setId(String.valueOf(list.getId()));
            listInfo.setTitle(list.getTitle());
            listInfo.setItemCount(list.getItemCount());
            listInfo.setEventDate(list.getEventDate());
            return listInfo;
        }).toList();
    }

    /**
     * Adds a new Item to the given list
     * @param listId the list to add the item to
     * @param itemInfo the item information to create the new item
     */
    @Transactional
    public void addItem(final String listId, final Item itemInfo) {
        final long currentUserId = CurrentUserTools.retrieveCurrentUserId();
        final long dbListId = Long.parseLong(listId);
        if (!isOwner(currentUserId, dbListId)) {
            throw new BadRequestException("The current user does not own this list");
        }
        final ItemDM itemToSave = MapperUtils.mapItemToItemDM(itemInfo);
        if (StringUtils.isBlank(itemToSave.getImage())) {
            imageGeneratorService.generateDefaultItemImage(itemToSave);
        }
        dataService.addItemToList(dbListId, itemToSave);
    }

    /**
     * Checks if the given userId owns the list tied to the provided listId
     * @param userId the userId to compare
     * @param listId the listId for lookup
     * @return true if the userId provided is the owner of the list
     */
    @Transactional
    private boolean isOwner(final long userId, final long listId) {
        final long listOwnerId = dataService.retrieveOwnerId(listId);
        return listOwnerId == userId;
    }
}
