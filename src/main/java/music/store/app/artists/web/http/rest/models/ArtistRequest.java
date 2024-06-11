package music.store.app.artists.web.http.rest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@NotNull(message = "Request body is required")
public record ArtistRequest(
        @NotEmpty(message = "Name is required") String name,
        @NotEmpty(message = "Last name is required") String lastName
) { }
