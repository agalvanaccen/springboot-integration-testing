package music.store.app.song.web.http.rest.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@NotNull(message = "Request body is required")
public record UpdateSongRequest(
        @NotBlank(message = "Title must not be empty") String title,
        @NotBlank(message = "Duration must not be empty") @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$") String duration,
        Long albumId
) { }
