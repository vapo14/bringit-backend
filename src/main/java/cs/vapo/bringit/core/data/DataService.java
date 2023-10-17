package cs.vapo.bringit.core.data;

import cs.vapo.bringit.core.dao.model.UserForLoginDM;
import cs.vapo.bringit.core.dao.user.UserDataService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DataService {

    @Autowired
    private UserDataService userDataService;

    public void createUser(final UserForLoginDM userData) {
        userDataService.createUser(userData);
    }
}
