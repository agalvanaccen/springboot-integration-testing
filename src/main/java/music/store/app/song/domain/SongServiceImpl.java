package music.store.app.song.domain;

import music.store.app.albums.domain.AlbumRepository;
import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.song.models.SaveSongCommand;
import music.store.app.song.models.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final SongMapper songMapper;

    public static final String SONG_NOT_FOUND_MSG = "Song with id: %d not found";

    SongServiceImpl(
            SongRepository songRepository,
            AlbumRepository albumRepository, SongMapper songMapper
    ) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.songMapper = songMapper;
    }

    @Override
    public Song findById(Long id) throws ResourceNotFoundException {
        var song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(SONG_NOT_FOUND_MSG, id)));

        return songMapper.toRecord(song);
    }

    @Override
    public List<Song> findByAlbum(Long albumId) {
        return songRepository.findByAlbum(albumId);
    }

    @Override
    public Song create(SaveSongCommand command) {
        var song = new SongEntity(command.title(), command.duration(), albumRepository.getReferenceById(command.albumId()));

        return songMapper.toRecord(songRepository.save(song));
    }

    @Override
    public Song update(SaveSongCommand command) throws ResourceNotFoundException {
        var song = songRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(SONG_NOT_FOUND_MSG, command.id())));

        song.setTitle(Objects.requireNonNullElse(command.title(), song.getTitle()));
        song.setDuration(Objects.requireNonNullElse(command.duration(), song.getDuration()));

        if (command.albumId() != null) {
            song.setAlbum(albumRepository.getReferenceById(command.albumId()));
        }

        return songMapper.toRecord(songRepository.save(song));
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        var song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(SONG_NOT_FOUND_MSG, id)));

        songRepository.delete(song);
    }
}
