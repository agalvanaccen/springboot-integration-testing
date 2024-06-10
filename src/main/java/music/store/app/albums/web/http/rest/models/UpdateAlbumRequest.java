package music.store.app.albums.web.http.rest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@NotNull(message = "Request body must not be null")
public record UpdateAlbumRequest(
        @NotEmpty(message = "Title is required") String title,
        String coverUrl,
        Long artistId
) { }
