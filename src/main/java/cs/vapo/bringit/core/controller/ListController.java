package cs.vapo.bringit.core.controller;

import cs.vapo.bringit.core.model.lists.CreateList;
import cs.vapo.bringit.core.model.lists.ListInformationBasic;
import cs.vapo.bringit.core.model.lists.PatchList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "List Controller", description = "CRUD operations for Lists")
@RequestMapping("/bringit-core")
@Validated
public class ListController {

    @Autowired
    public ListController() {
        // no dependencies added yet
    }

    @Operation(summary = "Creates a new list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List was successfully created", headers = @Header(
                    name = "Resource-Id", description = "The location of the created resource", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
                @Header(name = "Error-Message", description = "Exception occurred while creating list", schema =
                @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/lists")
    public ResponseEntity<Void> createList(@RequestBody final CreateList listData) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).build();
    }

    @Operation(summary = "Gets a user's lists and their basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lists were successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "404", description = "The given user was not found", headers = @Header(name =
                    "Error-Message", description = "The given user was not found", schema = @Schema(type = "string"))
                    , content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating list", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/lists")
    public ResponseEntity<ListInformationBasic> retrieveUsersLists(@RequestParam final String userId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Gets a list's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List details were successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "The given list was not found", headers = @Header(name =
                    "Error-Message", description = "The given list was not found", schema = @Schema(type = "string"))
                    , content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while creating list", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/lists/{listId}")
    public ResponseEntity<Void> retrieveListInformation(@PathVariable("listId") final String listId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Updates a list's attributes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "List attributes were updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "The given list was not found", headers = @Header(name =
                    "Error-Message", description = "The given list was not found", schema = @Schema(type = "string"))
                    , content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while updating list", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/v1/lists/{listId}")
    public ResponseEntity<Void> updateList(@PathVariable("listId") final String listId,
                                           @RequestBody final PatchList updateRequest) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes a given list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "List was successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "The given list was not found", headers = @Header(name =
                    "Error-Message", description = "The given list was not found", schema = @Schema(type = "string"))
                    , content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while updating list", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/v1/lists/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable("listId") final String listId) {
        return ResponseEntity.ok().build();
    }

}
