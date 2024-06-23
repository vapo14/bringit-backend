package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.list.ListDataService;
import cs.vapo.bringit.core.dao.model.ListDM;
import cs.vapo.bringit.core.model.lists.CreateList;
import cs.vapo.bringit.core.tools.CurrentUserTools;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {

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

    @Transactional
    public List<ListDM> getUserLists() {
        final long ownerId = CurrentUserTools.retrieveCurrentUserId();
        return dataService.findListsByOwnerId(ownerId);
    }
}
