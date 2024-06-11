package music.store.app.albums.web.http.rest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@NotNull(message = "Request body is required")
public record CreateAlbumRequest(
        @NotEmpty(message = "Title is required") String title,
        String coverUrl,
        @NotNull(message = "Artist id is required") Long artistId
) { }
