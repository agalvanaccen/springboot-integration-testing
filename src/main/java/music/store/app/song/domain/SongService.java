package music.store.app.song.domain;

import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.song.models.SaveSongCommand;
import music.store.app.song.models.Song;

import java.util.List;

public interface SongService {

    Song findById(Long id) throws ResourceNotFoundException;

    List<Song> findByAlbum(Long albumId);

    Song create(SaveSongCommand command);

    Song update(SaveSongCommand command) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
