package music.store.app.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseErrorResult<T>(
        String errorMessage,
        Map<String, String> details,
        Instant timestamp
) {
    public BaseErrorResult(String errorMessage, Map<String, String> details) {
        this(errorMessage, details, Instant.now());
    }

    public BaseErrorResult(String errorMessage) {
        this(errorMessage, null, Instant.now());
    }
}
