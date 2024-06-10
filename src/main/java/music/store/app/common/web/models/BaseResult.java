package music.store.app.common.web.models;

public class BaseResult<T> extends AbstractBaseResult {

    private T results;

    public BaseResult() {
        super();
    }

    public BaseResult(T results) {
        super();
        this.results = results;
    }

    public T getResults() {
        return results;
    }
}
