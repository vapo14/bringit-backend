package cs.vapo.bringit.core.controller;

import cs.vapo.bringit.core.model.user.CreateUser;
import cs.vapo.bringit.core.model.user.PatchUser;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User Controller", description = "CRUD operations for Users")
@RequestMapping("/v1/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Creates a new user")
    @ApiResponses({
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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody @Valid final CreateUser createUserRequest) throws URISyntaxException {
        userService.createUser(createUserRequest);
        return ResponseEntity.created(new URI("")).build();
    }

    @Operation(summary = "Gets the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User was successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "User is not logged in", headers = @Header(
                    name = "Error-Message", description = "User is not logged in", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public ResponseEntity<Void> getCurrentUser(@PathVariable("userId") final String userId) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).build();
    }

    @Operation(summary = "Updates the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User was successfully updated"),
            @ApiResponse(responseCode = "401", description = "User is not logged in", headers = @Header(
                    name = "Error-Message", description = "User is not logged in", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PATCH, value = "/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") final String userId,
                                           @RequestBody final PatchUser updateUserRequest) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).build();
    }

    @Operation(summary = "Deletes the current user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User was successfully deleted"),
            @ApiResponse(responseCode = "401", description = "User is not logged in", headers = @Header(
                    name = "Error-Message", description = "User is not logged in", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating user", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") final String userId) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).build();
    }
}
