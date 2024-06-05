package music.store.app.artist.domain;

import music.store.app.common.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper implements Mapper<ArtistEntity, Artist> {

    @Override
    public Artist toDTO(ArtistEntity entity) {
        return new Artist(entity.getId(), entity.getName(), entity.getLastname());
    }

    @Override
    public ArtistEntity toEntity(Artist dto) {
        return new ArtistEntity(dto.id(), dto.name(), dto.lastName());
    }
}
