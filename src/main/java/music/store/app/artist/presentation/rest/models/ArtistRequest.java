package music.store.app.artist.presentation.rest.models;

import jakarta.validation.constraints.NotEmpty;

public record ArtistRequest(
        @NotEmpty(message = "Name is required") String name,
        @NotEmpty(message = "Last name is required") String lastName
) { }
