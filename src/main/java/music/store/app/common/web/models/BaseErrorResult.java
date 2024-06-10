package music.store.app.common.web.models;

public class BaseErrorResult extends AbstractBaseResult {

    private final ApiError error;

    public BaseErrorResult(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }
}
