package music.store.app.song.web.http.rest.mappers;

import music.store.app.common.mappers.DTOMapper;
import music.store.app.song.models.Album;
import music.store.app.song.models.Song;
import music.store.app.song.web.http.rest.models.AlbumDTO;
import music.store.app.song.web.http.rest.models.SongDTO;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class SongDTOMapper implements DTOMapper<Song, SongDTO> {

    @Override
    public SongDTO toDTO(Song model) {
        return new SongDTO(
                model.id(),
                model.title(),
                model.duration().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                new AlbumDTO(model.album().id(), model.album().title())
        );
    }

    @Override
    public Song toModel(SongDTO dto) {
        return new Song(
                dto.id(),
                dto.title(),
                LocalTime.parse(dto.duration()),
                new Album(dto.album().id(), dto.album().title())
        );
    }
}
