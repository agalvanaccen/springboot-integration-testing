package music.store.app.common.web.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResult<T> extends AbstractBaseResult {

    private final T results;

    public BaseResult(T results) {
        super();
        this.results = results;
    }

    public T getResults() {
        return results;
    }
}
