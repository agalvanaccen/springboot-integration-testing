package music.store.app.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResult<T>(
        T results,
        Instant timestamp
) {
    public BaseResult(T results) {
        this(results, Instant.now());
    }
}
