package music.store.app.artists.domain;

import music.store.app.artists.models.Artist;
import music.store.app.common.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
class ArtistMapper implements Mapper<ArtistEntity, Artist> {

    @Override
    public Artist toRecord(ArtistEntity entity) {
        return new Artist(entity.getId(), entity.getName(), entity.getLastname());
    }

    @Override
    public ArtistEntity toEntity(Artist record) {
        return new ArtistEntity(record.id(), record.name(), record.lastName());
    }
}
