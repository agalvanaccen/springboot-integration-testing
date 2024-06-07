package music.store.app.common.web.models;

import java.time.Instant;

public abstract class AbstractBaseResult {

    private final Instant timestamp;

    protected AbstractBaseResult( ) {
        this.timestamp = Instant.now();
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
