package music.store.app.common.mappers;

public interface DTOMapper<T, R> {

    R toDTO(T model);

    T toModel(R dto);
}
