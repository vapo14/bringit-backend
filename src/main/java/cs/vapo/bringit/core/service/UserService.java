package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.dao.mapper.MapperUtils;
import cs.vapo.bringit.core.dao.model.UserDM;
import cs.vapo.bringit.core.dao.model.UserForLoginDM;
import cs.vapo.bringit.core.dao.user.UserDataService;
import cs.vapo.bringit.core.exceptions.BadRequestException;
import cs.vapo.bringit.core.model.user.CreateUser;
import cs.vapo.bringit.core.model.user.GetUser;
import cs.vapo.bringit.core.model.user.PatchUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Service
@Validated
public class UserService {

    private final UserDataService userDataService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(final UserDataService userDataService, final PasswordEncoder passwordEncoder,
                       final ModelMapper modelMapper) {
        this.userDataService = userDataService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates the user with the given data
     * @param createUserRequest the create user request data
     */
    @Transactional
    public String createUser(final @Valid CreateUser createUserRequest) {
        // TODO: implement aspect oriented programming for logging
        final UserForLoginDM userData = new UserForLoginDM();
        userData.setUsername(createUserRequest.getUsername());
        userData.setEmail(createUserRequest.getEmail());
        userData.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        return userDataService.createUser(userData);
    }

    /**
     * Fetches the user data from the repository
     * @param userId the userId to retrieve
     * @return the user data
     */
    @Transactional
    public GetUser retrieveUser(final String userId) {
        final UserDM userData = userDataService.findUserById(userId);
        return modelMapper.map(userData, GetUser.class);
    }

    /**
     * Updates the user in the repository
     * @param userId the user id to update
     * @param userData the updated user data
     */
    @Transactional
    public void updateUser(final String userId, final PatchUser userData) {
        final boolean passwordUpdated = StringUtils.isNotBlank(userData.getPassword());
        if (passwordUpdated) {
            validatePasswordFields(userData);
            userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        }

        final Set<String> ignoreProperties = new HashSet<>();
        ignoreProperties.add("confirmPassword");
        final UserForLoginDM updatedUserData = new UserForLoginDM();
        MapperUtils.mapNonNull(userData, updatedUserData, ignoreProperties);
        userDataService.updateUser(userId, updatedUserData);
    }

    /**
     * Validates if the confirm-password and password match
     * @param userData the user data
     */
    public void validatePasswordFields(final PatchUser userData) {
        if (!StringUtils.equals(userData.getPassword(), userData.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match, please confirm your password.");
        }
    }
}
