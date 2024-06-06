package music.store.app.artist.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import music.store.app.artist.domain.Artist;
import music.store.app.artist.domain.ArtistService;
import music.store.app.artist.web.rest.models.ArtistRequest;
import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.models.BaseResult;
import music.store.app.common.models.PagedResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static music.store.app.artist.web.rest.ArtistExamples.*;
import static music.store.app.common.RestConstants.APPLICATION_JSON;

@RestController
@RequestMapping("/api/v1/artists")
@Tag(name = "Artists", description = "Artists related resources")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping(produces = APPLICATION_JSON)
    @Operation(
            summary = "Returns a paginated list of artists",
            description = "This API allows to get a list of artists in a paginated way",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of artists",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Artist.class)), examples = {
                                    @ExampleObject(
                                            name = "List of artists",
                                            value = ARTISTS_PAGE
                                    )
                            })
                    )
            }
    )
    public BaseResult<PagedResult<Artist>> getArtists(
            @RequestParam(name = "pageNo", defaultValue = "1") @Min(1) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) int pageSize
    ) {
        return new BaseResult<>(artistService.getArtists(pageNo, pageSize));
    }

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new Artist",
            description = "This API allows to create a new artist",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Artists request data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ArtistRequest.class), examples = {
                            @ExampleObject(
                                    name = "Create user data",
                                    value = ARTIST_REQUEST
                            )
                    })
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created artist",
                            content = @Content(schema = @Schema(implementation = Artist.class), examples = {
                                    @ExampleObject(
                                            name = "Artist successfully created",
                                            value = UPDATED_CREATED_ARTIST
                                    )
                            })
                    )
            }
    )
    public BaseResult<Artist> create(@RequestBody @Valid ArtistRequest artistRequest) {
        var created = artistService.create(new Artist(null, artistRequest.name(), artistRequest.lastName()));

        return new BaseResult<>(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @Operation(
            summary = "Update artist data",
            description = "This API allows to update an artist data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Artist request data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ArtistRequest.class), examples = {
                            @ExampleObject(
                                    name = "Artist data",
                                    value = ARTIST_REQUEST
                            )
                    })
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated artist",
                            content = @Content(schema = @Schema(implementation = Artist.class), examples = {
                                    @ExampleObject(
                                            name = "Artist successfully updated",
                                            value = UPDATED_CREATED_ARTIST
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Artist not found",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            name = "Artist was not found",
                                            value = ARTIST_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Artist> update(@PathVariable @NotNull Long id, @RequestBody @Valid ArtistRequest artistRequest) throws ResourceNotFoundException {
        var updated = artistService.update(id, artistRequest.name(), artistRequest.lastName());

        return new BaseResult<>(updated);
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON)
    @Operation(
            summary = "Find artist by id",
            description = "This API allows to find an artists by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Artist data",
                            content = @Content(schema = @Schema(implementation = Artist.class), examples = {
                                    @ExampleObject(
                                            name = "Artist data",
                                            value = ARTIST
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Artist not found",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            name = "Artist was not found",
                                            value = ARTIST_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Artist> findBydId(@PathVariable @NotNull Long id) throws ResourceNotFoundException {
        return new BaseResult<>(artistService.findById(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(
            summary = "Delete artist data",
            description = "This API allows to delete an artist data",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Artist successfully deleted",
                            content = @Content(schema = @Schema(implementation = BaseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Artist not found",
                            content = @Content(examples = {
                                    @ExampleObject(
                                            name = "Artist was not found",
                                            value = ARTIST_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Void> delete(@NotNull @PathVariable Long id) throws ResourceNotFoundException {
        artistService.delete(id);
        return new BaseResult<>(null);
    }
}
