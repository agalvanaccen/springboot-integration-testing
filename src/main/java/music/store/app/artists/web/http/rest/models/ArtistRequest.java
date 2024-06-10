package music.store.app.artists.web.http.rest.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@NotNull
public record ArtistRequest(
        @NotEmpty(message = "Name is required") String name,
        @NotEmpty(message = "Last name is required") String lastName
) { }
