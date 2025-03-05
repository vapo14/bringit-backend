package cs.vapo.bringit.core.config.auth;

import cs.vapo.bringit.core.dao.user.UserDataService;
import cs.vapo.bringit.core.logging.LogMethodEntry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDataService userDataService;

    @Autowired
    public CustomUserDetailsService(final UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    /**
     * Custom implementation of the {@link UserDetails} loadUserByUsername method. Retrieves a
     * {@link cs.vapo.bringit.core.dao.model.UserForLoginDM} object from the repository.
     * @param username the username identifying the user whose data is required.
     * @return the {@link UserDetails} for the given username
     * @throws UsernameNotFoundException if user not found
     * @throws jakarta.persistence.EntityNotFoundException if user not found
     */
    @Override
    @Transactional
    @LogMethodEntry
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataService.findUserByUsernameForLogin(username);
    }
}
