package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.model.UserForLoginDM;
import cs.vapo.bringit.core.data.DataService;
import cs.vapo.bringit.core.model.user.CreateUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService {

    @Autowired
    private DataService dataService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates the user with the given data
     * @param createUserRequest the create user request data
     */
    public void createUser(final @Valid CreateUser createUserRequest) {
        // TODO: implement aspect oriented programming for logging
        final UserForLoginDM userData = new UserForLoginDM();
        userData.setUsername(createUserRequest.getUsername());
        userData.setEmail(createUserRequest.getEmail());
        userData.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        dataService.createUser(userData);
    }
}
