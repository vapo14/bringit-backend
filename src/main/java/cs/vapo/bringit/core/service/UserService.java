package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.config.ApplicationConfiguration;
import cs.vapo.bringit.core.dao.mapper.MapperUtils;
import cs.vapo.bringit.core.dao.model.UserDM;
import cs.vapo.bringit.core.dao.model.UserForLoginDM;
import cs.vapo.bringit.core.dao.user.UserDataService;
import cs.vapo.bringit.core.exceptions.BadRequestException;
import cs.vapo.bringit.core.logging.LogMethodEntry;
import cs.vapo.bringit.core.model.user.CreateUser;
import cs.vapo.bringit.core.model.user.CreateUserResponse;
import cs.vapo.bringit.core.model.user.GetUser;
import cs.vapo.bringit.core.model.user.LoginUser;
import cs.vapo.bringit.core.model.user.PatchUser;
import cs.vapo.bringit.core.model.user.SearchUserRequest;
import cs.vapo.bringit.core.model.user.contact.ContactRequest;
import cs.vapo.bringit.core.tools.CurrentUserTools;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    private final ApplicationConfiguration configuration;

    @Autowired
    public UserService(final UserDataService userDataService, final PasswordEncoder passwordEncoder,
                       final ModelMapper modelMapper, final JwtService jwtService,
                       final AuthenticationManager authManager, final ApplicationConfiguration configuration) {
        this.userDataService = userDataService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.configuration = configuration;
    }

    /**
     * Creates and authenticates the user with the given data
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
     * @return the user data
     */
    @Transactional
    @LogMethodEntry
    public GetUser retrieveCurrentUser() {
        final long userId = CurrentUserTools.retrieveCurrentUserId();
        final UserDM userData = userDataService.findUserById(userId);
        return modelMapper.map(userData, GetUser.class);
    }

    /**
     * Logs in a user and generates the corresponding jwt token.
     * @param request the login request info
     * @return the jwt token for the authenticated user
     */
    @Transactional
    public String loginUser(@Valid final LoginUser request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserForLoginDM userData = userDataService.findUserByUsernameForLogin(request.getUsername());
        return jwtService.generateToken(new HashMap<>(), userData);
    }

    /**
     * Updates the user in the repository
     * @param userData the updated user data
     */
    @Transactional
    @LogMethodEntry
    public void updateCurrentUser(final PatchUser userData) {
        final long userId = CurrentUserTools.retrieveCurrentUserId();
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
     */
    @Transactional
    @LogMethodEntry
    public void deleteCurrentUser() {
        final long userId = CurrentUserTools.retrieveCurrentUserId();
        userDataService.deleteUser(userId);
    }

    /**
     * Searches for a user given its username
     * @param request the search request
     * @return the UserDM if found
     */
    public GetUser searchUserByUsername(final SearchUserRequest request) {
        GetUser userData = null;
        try {
            userData = modelMapper.map(userDataService.findUserByUsername(request.getUsername()), GetUser.class);
        } catch (final EntityNotFoundException exp) {
            log.info(exp.getMessage());
        }
        return userData;
    }

    /**
     * Adds a contact to a user's contact list.
     * @param request the contact request
     */
    public void addContact(final ContactRequest request) {
        final long currentUserId = CurrentUserTools.retrieveCurrentUserId();
        userDataService.addContact(currentUserId, Long.parseLong(request.getContactId()));
    }

    public List<GetUser> retrieveUserContacts(final int pageNumber) {
        final long currentUserId = CurrentUserTools.retrieveCurrentUserId();
        List<UserDM> results = userDataService.retrieveUserContacts(currentUserId,
                PageRequest.of(pageNumber, configuration.getUserContactsListPageSize()));
        return MapperUtils.mapList(results, GetUser.class);
    }
}
