package cs.vapo.bringit.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "List Controller", description = "CRUD operations for Lists")
@RequestMapping("/v1/lists")
@Validated
public class ListController {


    @Operation(summary = "Creates a new list")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "List was successfully created", headers = @Header(
                    name = "Resource-Id", description = "The location of the created resource", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", headers = @Header(
                    name = "Error-Message", description = "Validation failed for request field", schema =
            @Schema(type = "string")), content = @Content),
            @ApiResponse(responseCode = "500", description = "The server encountered an unexpected error", headers =
                @Header(name = "Error-Message", description = "Exception occurred while creating list", schema =
                @Schema(type = "string")), content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createList() throws URISyntaxException {
        return ResponseEntity.created(new URI("")).build();
    }
}
