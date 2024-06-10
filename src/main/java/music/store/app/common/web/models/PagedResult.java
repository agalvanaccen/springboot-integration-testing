package music.store.app.common.web.models;

import java.util.List;

public record PagedResult<T>(
        List<T> items,
        long totalItems,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious
) { }
