package music.store.app.common.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseErrorResult extends AbstractBaseResult {

    private final ApiError error;

    public BaseErrorResult(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }
}
