package cs.vapo.bringit.core.controller;

import cs.vapo.bringit.core.controller.http.CustomHeaders;
import cs.vapo.bringit.core.dao.model.UserDM;
import cs.vapo.bringit.core.model.user.SearchUserRequest;
import cs.vapo.bringit.core.model.user.*;
import cs.vapo.bringit.core.model.user.contact.ContactRequest;
import cs.vapo.bringit.core.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User Controller", description = "CRUD operations for Users")
@RequestMapping("/bringit-core")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Authenticates a user given valid credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was successfully authenticated", headers = @Header(
                    name = "JWT", description = "The JSON Web Token for the current session",
                    schema = @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request input",
                    schema = @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", headers = @Header(
                    name = "Error-Message", description = "We don't recognize the username/password combination",
                    schema = @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error", headers = @Header(
                    name = "Error-Message", description = "Exception occurred while authenticating user",
                    schema = @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/v1/users/login")
    public ResponseEntity<Void> loginUser(@RequestBody @Valid final LoginUser loginUserRequest) {
        final String jwt = userService.loginUser(loginUserRequest);
        return ResponseEntity.noContent().header(CustomHeaders.JWT, jwt).build();
    }

    @Operation(summary = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User was successfully created", headers = @Header(
                    name = "Resource-Id", description = "The location of the created user",
                    schema = @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid final CreateUser createUserRequest) throws URISyntaxException {
        final CreateUserResponse createUserData = userService.createUser(createUserRequest);
        return ResponseEntity.created(new URI(String.format("/v1/users/%s", createUserData.getUserId())))
                .header(CustomHeaders.JWT, createUserData.getJwt()).build();
    }

    @Operation(summary = "Gets the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "The user was not found", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while getting user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping( value = "/v1/users")
    public ResponseEntity<GetUser> getCurrentUser() {
        return ResponseEntity.ok(userService.retrieveCurrentUser());
    }

    @Operation(summary = "Updates the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was successfully updated"),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "The user was not found", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while updating user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/v1/users")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid final PatchUser updateUserRequest) {
        userService.updateCurrentUser(updateUserRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Searches for a user given a username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found"),
            @ApiResponse(responseCode = "403", description = "User is not logged in", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while searching for user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/v1/users/search")
    public ResponseEntity<GetUser> search(@RequestBody final SearchUserRequest username) {
        // consider how to secure this endpoint to prevent malicious use
        return ResponseEntity.ok(userService.searchUserByUsername(username));
    }

    @Operation(summary = "Deletes the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was successfully deleted"),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "The user was not found", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while deleting user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/v1/users")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adds a contact to the user's contact list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact was successfully added"),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "The user was not found", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating contact", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/users/contacts")
    public ResponseEntity<Void> addContact(@RequestBody final ContactRequest request) throws URISyntaxException {
        userService.addContact(request);
        return ResponseEntity.created(new URI("/v1/users/contacts")).build();
    }

    @Operation(summary = "Retrieves the user's contact list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts were successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "User is not logged in", content =  @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "The user was not found", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating contact", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/users/contacts")
    public ResponseEntity<List<GetUser>> retrieveContacts(@RequestParam final int pageNumber) {
        return ResponseEntity.ok(userService.retrieveUserContacts(pageNumber));
    }
}
