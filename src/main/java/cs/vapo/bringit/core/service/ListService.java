package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.list.ListDataService;
import cs.vapo.bringit.core.dao.mapper.MapperUtils;
import cs.vapo.bringit.core.dao.model.ListDM;
import cs.vapo.bringit.core.model.lists.CreateList;
import cs.vapo.bringit.core.model.lists.ListInformationBasic;
import cs.vapo.bringit.core.tools.CurrentUserTools;
import jakarta.transaction.Transactional;
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

    @Autowired
    public ListService(final ListDataService dataService) {
        this.dataService = dataService;
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
            listInfo.setId(list.getPublicId());
            listInfo.setTitle(list.getTitle());
            listInfo.setItemCount(list.getItemCount());
            listInfo.setEventDate(list.getEventDate());
            return listInfo;
        }).toList();
    }
}
