package music.store.app.song.web.http.rest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@NotNull(message = "Request body is required")
public record CreateSongRequest(
        @NotEmpty(message = "Title is required") String title,
        @NotEmpty(message = "Duration is required") @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$") String duration,
        @NotNull(message = "Album's id is required") Long albumId
) { }
