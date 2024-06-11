package music.store.app.song.domain;

import music.store.app.common.mappers.Mapper;
import music.store.app.song.models.Album;
import music.store.app.song.models.Song;
import org.springframework.stereotype.Component;

@Component
class SongMapper implements Mapper<SongEntity, Song> {
    @Override
    public Song toRecord(SongEntity entity) {
        var album = new Album(entity.getAlbum().getId(), entity.getAlbum().getTitle());
        return new Song(
                entity.getId(),
                entity.getTitle(),
                entity.getDuration(),
                album
        );
    }

    @Override
    public SongEntity toEntity(Song record) {
        return new SongEntity(record.id(), record.title(), record.duration());
    }
}
