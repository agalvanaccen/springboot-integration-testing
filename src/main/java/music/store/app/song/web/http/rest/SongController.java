package music.store.app.song.web.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.web.models.BaseErrorResult;
import music.store.app.common.web.models.BaseResult;
import music.store.app.song.domain.SongService;
import music.store.app.song.models.SaveSongCommand;
import music.store.app.song.web.http.rest.mappers.SongDTOMapper;
import music.store.app.song.web.http.rest.models.CreateSongRequest;
import music.store.app.song.web.http.rest.models.SongDTO;
import music.store.app.song.web.http.rest.models.UpdateSongRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

import static music.store.app.common.RestConstants.APPLICATION_JSON;
import static music.store.app.song.web.http.rest.SongExamples.*;

@RestController
@RequestMapping("/api/v1/songs")
@Tag(name = "Songs", description = "Songs-related resources")
public class SongController {

    private final SongService songService;
    private final SongDTOMapper songDTOMapper;

    public SongController(
            SongService songService,
            SongDTOMapper songDTOMapper
    ) {
        this.songService = songService;
        this.songDTOMapper = songDTOMapper;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON)
    @Operation(
            summary = "Find song by id",
            description = "This API allows to find a song by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song information",
                            content = @Content(schema = @Schema(implementation = SongDTO.class), examples = {
                                    @ExampleObject(
                                            name = "Song information",
                                            value = SONG_INFORMATION
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Song not found",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Song not found",
                                            value = SONG_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<SongDTO> findById(@PathVariable @Parameter(description = "Song's unique identifier", example = "1" ) @NotNull Long id) throws ResourceNotFoundException {
        var song = songService.findById(id);
        return new BaseResult<>(songDTOMapper.toDTO(song));
    }

    @GetMapping(produces = APPLICATION_JSON)
    @Operation(
            summary = "Find songs by album",
            description = "This API allows to find songs by album id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of songs",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SongDTO.class)), examples = {
                                    @ExampleObject(
                                            name = "Songs",
                                            value = SONGS_BY_ALBUM
                                    )
                            })
                    )
            }
    )
    public BaseResult<List<SongDTO>> findByAlbum(@RequestParam("albumId") @Parameter(in = ParameterIn.QUERY, description = "Album's unique identifier", example = "1") @NotNull Long albumId) {
        var songs = songService.findByAlbum(albumId)
                .stream()
                .map(songDTOMapper::toDTO)
                .toList();

        return new BaseResult<>(songs);
    }

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new song",
            description = "This API allows to create a new song",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New song data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateSongRequest.class), examples = {
                            @ExampleObject(
                                    name = "Create song request",
                                    value = CREATE_SONG_REQUEST
                            )
                    })
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Song was successfully created",
                            content = @Content(schema = @Schema(implementation = SongDTO.class), examples = {
                                    @ExampleObject(
                                            name = "Created song information",
                                            value = CREATE_SONG_RESPONSE
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Song not found",
                                            value = CREATE_SONG_BAD_REQUEST
                                    )
                            })
                    )
            }
    )
    public BaseResult<SongDTO> create(@RequestBody @Valid CreateSongRequest request) {
        var command = new SaveSongCommand(null, request.title(), LocalTime.parse(request.duration()), request.albumId());
        var createdSong = songService.create(command);

        return new BaseResult<>(songDTOMapper.toDTO(createdSong));
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @Operation(
            summary = "Update existing song",
            description = "This API allows to update an existing song",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Song's data to update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateSongRequest.class), examples = {
                            @ExampleObject(
                                    name = "Update song data",
                                    value = UPDATE_SONG_REQUEST
                            )
                    })
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Song was successfully updated",
                            content = @Content(schema = @Schema(implementation = SongDTO.class), examples = {
                                    @ExampleObject(
                                            name = "Updated song information",
                                            value = UPDAET_SONG_RESPONSE
                                    )
                            })
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Song not found",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Song not found",
                                            value = SONG_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<SongDTO> update(@PathVariable @NotNull Long id, @RequestBody @Validated UpdateSongRequest request) throws ResourceNotFoundException {
        var command = new SaveSongCommand(id, request.title(), LocalTime.parse(request.duration()), request.albumId());
        var updatedSong = songService.update(command);

        return new BaseResult<>(songDTOMapper.toDTO(updatedSong));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(
            summary = "Delete an existing song",
            description = "This API allows to delete a song by its id",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Song was successfully deleted",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Song not found",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class), examples = {
                                    @ExampleObject(
                                            name = "Song not found",
                                            value = SONG_NOT_FOUND
                                    )
                            })
                    )
            }
    )
    public BaseResult<Void> delete(@PathVariable @Parameter(description = "Song's unique identifier", example = "1") @NotNull Long id) throws ResourceNotFoundException {
        songService.delete(id);
        return new BaseResult<>();
    }
}
