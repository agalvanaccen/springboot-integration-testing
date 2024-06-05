package music.store.app.common;

public interface Mapper<E, T> {

    T toDTO(E entity);

    E toEntity(T dto);
}
