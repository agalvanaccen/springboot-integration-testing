package music.store.app.albums.web.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import music.store.app.albums.domain.AlbumMapper;
import music.store.app.albums.domain.AlbumService;
import music.store.app.albums.models.Album;
import music.store.app.albums.models.SaveAlbumCommand;
import music.store.app.albums.web.http.rest.models.CreateAlbumRequest;
import music.store.app.albums.web.http.rest.models.UpdateAlbumRequest;
import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.web.models.BaseErrorResult;
import music.store.app.common.web.models.BaseResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static music.store.app.albums.web.http.rest.AlbumExamples.ALBUMS_BY_ARTIST_ID;
import static music.store.app.albums.web.http.rest.AlbumExamples.ALBUM_INFO;
import static music.store.app.albums.web.http.rest.AlbumExamples.ALBUM_NOT_FOUND;
import static music.store.app.albums.web.http.rest.AlbumExamples.CREATE_ALBUM_BAD_REQUEST;
import static music.store.app.albums.web.http.rest.AlbumExamples.CREATE_ALBUM_REQUEST;
import static music.store.app.albums.web.http.rest.AlbumExamples.CREATE_ALBUM_RESPONSE;
import static music.store.app.albums.web.http.rest.AlbumExamples.UPDATE_ALBUM_REQUEST;
import static music.store.app.albums.web.http.rest.AlbumExamples.UPDATE_ALBUM_RESPONSE;
import static music.store.app.common.RestConstants.APPLICATION_JSON;

@RestController
@RequestMapping("/api/v1/albums")
@Tag(name = "Albums", description = "Albums-related resources")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON)
    @Operation(
            summary = "Find album by its id",
            description = "This API allows to find an album information by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Album information",
                            content = @Content(schema = @Schema(implementation = Album.class), examples = {
                                    @ExampleObject(
                                            name = "Album information",
                                            value = ALBUM_INFO
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Album not found",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Album not found",
                                            value = ALBUM_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Album> findById(@PathVariable(name = "id") @NotNull @Parameter(description = "Album's unique identifier", example = "1") Long id) throws ResourceNotFoundException {
        return new BaseResult<>(albumService.findById(id));
    }

    @GetMapping(produces = APPLICATION_JSON)
    @Operation(
            summary = "Find albums by artist",
            description = "This API allows to find albums byt artist id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of albums",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Album.class)), examples = {
                                    @ExampleObject(
                                            name = "List fo albums",
                                            value = ALBUMS_BY_ARTIST_ID
                                    )
                            })
                    )
            }
    )
    public BaseResult<List<Album>> findByArtist(@RequestParam(value = "artistId", required = true) @Parameter(in = ParameterIn.QUERY, description = "Artist's unique identifier", example = "1") @NotNull Long artistId) {
        return new BaseResult<>(albumService.getByArtist(artistId));
    }

    @PostMapping(produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new album",
            description = "This API allows to create a new album",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create Album request data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateAlbumRequest.class), examples = {
                            @ExampleObject(
                                    name = "create Album request",
                                    value = CREATE_ALBUM_REQUEST
                            )
                    })
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Album was successfully created",
                            content = @Content(schema = @Schema(implementation = AlbumMapper.class), examples = {
                                    @ExampleObject(
                                            name = "New Album data",
                                            value = CREATE_ALBUM_RESPONSE
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Bad request",
                                            value = CREATE_ALBUM_BAD_REQUEST
                                    )
                            })
                    )
            }
    )
    public BaseResult<Album> create(@RequestBody @Validated CreateAlbumRequest request) {
        var command = new SaveAlbumCommand(null, request.title(), request.coverUrl(), request.artistId());

        return new BaseResult<>(albumService.create(command));
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    @Operation(
            summary = "Update existing album",
            description = "This API allows to update an already exiting album data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update album request data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateAlbumRequest.class), examples = {
                            @ExampleObject(
                                    name = "Update album data",
                                    value = UPDATE_ALBUM_REQUEST
                            )
                    })
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Album was successfully updated",
                            content = @Content(schema = @Schema(implementation = Album.class), examples = {
                                    @ExampleObject(
                                            name = "Updated Album data",
                                            value = UPDATE_ALBUM_RESPONSE
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Album not found",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Album not found",
                                            value = ALBUM_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Album> update(@PathVariable(name = "id") @Parameter(description = "Album's unique identifier", example = "1") @NotNull Long id, @RequestBody @Validated UpdateAlbumRequest request) throws ResourceNotFoundException {
        var command = new SaveAlbumCommand(id, request.title(), request.coverUrl(), request.artistId());

        return new BaseResult<>(albumService.update(command));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(
            summary = "Delete album by id",
            description = "This API allows to delete an exiting album by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Album was successfully deleted",
                            content = @Content(schema = @Schema(implementation = BaseResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Album not found",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Album not found",
                                            value = ALBUM_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Void> delete(@PathVariable(name = "id") @Parameter(description = "Album's unique identifier", example = "1") @NotNull Long id) throws ResourceNotFoundException {
        albumService.delete(id);
        return new BaseResult<>();
    }
}
