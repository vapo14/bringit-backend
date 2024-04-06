package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.config.auth.CustomUserDetailsService;
import cs.vapo.bringit.core.dao.mapper.MapperUtils;
import cs.vapo.bringit.core.dao.model.UserDM;
import cs.vapo.bringit.core.dao.model.UserForLoginDM;
import cs.vapo.bringit.core.dao.user.UserDataService;
import cs.vapo.bringit.core.exceptions.BadRequestException;
import cs.vapo.bringit.core.logging.LogMethodEntry;
import cs.vapo.bringit.core.model.user.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@Validated
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserDataService userDataService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UserService(final UserDataService userDataService, final PasswordEncoder passwordEncoder,
                       final ModelMapper modelMapper, final JwtService jwtService,
                       final AuthenticationManager authManager, final CustomUserDetailsService userDetailsService) {
        this.userDataService = userDataService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Creates the user with the given data
     * @param createUserRequest the create user request data
     */
    @Transactional
    @LogMethodEntry
    public CreateUserResponse createUser(final @Valid CreateUser createUserRequest) {
        final UserForLoginDM userData = new UserForLoginDM();
        userData.setUsername(createUserRequest.getUsername());
        userData.setEmail(createUserRequest.getEmail());
        userData.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        final CreateUserResponse responseData = new CreateUserResponse();
        responseData.setUserId(userDataService.createUser(userData));
        responseData.setJwt(jwtService.generateToken(new HashMap<>(), userData));
        return responseData;
    }

    /**
     * Fetches the user data from the repository
     * @param userId the userId to retrieve
     * @return the user data
     */
    @Transactional
    @LogMethodEntry
    public GetUser retrieveUser(final String userId) {
        final UserDM userData = userDataService.findUserById(userId);
        return modelMapper.map(userData, GetUser.class);
    }

    /**
     * Logs in a user and generates the corresponding jwt token.
     * @param request the login request info
     */
    @Transactional
    public void loginUser(final LoginUser request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserForLoginDM userData = userDataService.findUserByUsernameForLogin(request.getUsername());
        final String jwt = jwtService.generateToken(new HashMap<>(), userData);
        log.info("jwt: {}", jwt);
    }

    /**
     * Updates the user in the repository
     * @param userId the user id to update
     * @param userData the updated user data
     */
    @Transactional
    @LogMethodEntry
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

    /**
     * Deletes the user that corresponds to the provided userId
     * @param userId the userId to delete
     */
    @Transactional
    @LogMethodEntry
    public void deleteUser(final String userId) {
        // TODO: before deleting the user, check what lists are owned by the user and delete those first.
        // what if the lists are not deleted but rather scheduled for deletion after a period of time ?
        // first validate if the user is actually logged in. Only the user logged in can delete their own account.
    }
}
