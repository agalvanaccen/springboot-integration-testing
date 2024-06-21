package music.store.app.common.web.models;

public class BaseErrorResult extends AbstractBaseResult {

    private ApiError error;

    public BaseErrorResult() { }

    public BaseErrorResult(ApiError error) {
        this.error = error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }
}
