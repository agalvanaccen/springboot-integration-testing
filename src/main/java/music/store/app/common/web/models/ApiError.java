package music.store.app.common.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(String type, String message, List<?> details) {

    public ApiError(String type, String message) {
        this(type, message, null);
    }
}
