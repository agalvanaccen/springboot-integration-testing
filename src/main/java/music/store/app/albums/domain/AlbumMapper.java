package music.store.app.albums.domain;

import music.store.app.albums.models.Album;
import music.store.app.common.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper implements Mapper<AlbumEntity, Album> {

    @Override
    public Album toRecord(AlbumEntity entity) {
        return new Album(
                entity.getId(),
                entity.getTitle(),
                entity.getCoverUrl(),
                entity.getArtist().getId(),
                String.format("%s %s", entity.getArtist().getName(), entity.getArtist().getLastname())
        );
    }

    @Override
    public AlbumEntity toEntity(Album record) {
        return new AlbumEntity(record.id(), record.title(), record.coverUrl());
    }
}
