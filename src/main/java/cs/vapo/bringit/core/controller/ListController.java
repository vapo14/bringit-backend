package cs.vapo.bringit.core.controller;

import cs.vapo.bringit.core.model.lists.CreateList;
import cs.vapo.bringit.core.model.lists.ListDetails;
import cs.vapo.bringit.core.model.lists.ListInformationBasic;
import cs.vapo.bringit.core.model.lists.PatchList;
import cs.vapo.bringit.core.model.lists.item.Item;
import cs.vapo.bringit.core.service.ListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.validation.Valid;
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

    private final ListService listService;

    @Autowired
    public ListController(final ListService listService) {
        this.listService = listService;
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
    public ResponseEntity<Void> createList(@RequestBody @Valid final CreateList listData) throws URISyntaxException {
        listService.createList(listData);
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
    public ResponseEntity<List<ListInformationBasic>> retrieveUsersLists() {
        return ResponseEntity.ok(listService.getUserLists());
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
    public ResponseEntity<ListDetails> retrieveListInformation(@PathVariable("listId") final String listId) {
        return ResponseEntity.ok(new ListDetails());
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

    @Operation(summary = "Adds an item to the given list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item was successfully added"),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "404", description = "The given list was not found", headers = @Header(name =
                    "Error-Message", description = "The given list was not found", schema = @Schema(type = "string"))
                    , content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not logged in"),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
            @Header(name = "Error-Message", description = "Exception occurred while adding item", schema =
            @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/lists/{listId}/items")
    public ResponseEntity<Void> addItem(@PathVariable("listId") final String listId, @RequestBody final Item itemInfo)
            throws URISyntaxException {
        listService.addItem(listId, itemInfo);
        return ResponseEntity.created(new URI(String.format("/v1/lists/%s", listId))).build();
    }

}
