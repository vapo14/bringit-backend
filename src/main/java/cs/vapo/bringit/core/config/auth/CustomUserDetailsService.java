package cs.vapo.bringit.core.config.auth;

import cs.vapo.bringit.core.dao.user.UserDataService;
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataService.findUserByUsernameForLogin(username);
    }
}
