package music.store.app.albums.web.http.rest.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NotNull(message = "Request body is required")
public record UpdateAlbumRequest(
        @NotBlank(message = "Title must not be empty") String title,
        String coverUrl,
        Long artistId
) { }
