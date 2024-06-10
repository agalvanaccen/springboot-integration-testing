package music.store.app.common.mappers;

public interface Mapper<E, R> {

    R toRecord(E entity);

    E toEntity(R record);
}
