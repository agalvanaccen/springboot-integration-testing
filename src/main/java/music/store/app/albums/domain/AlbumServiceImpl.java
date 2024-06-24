package music.store.app.albums.domain;

import music.store.app.albums.models.Album;
import music.store.app.albums.models.SaveAlbumCommand;
import music.store.app.artists.domain.ArtistRepository;
import music.store.app.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final AlbumMapper albumMapper;

    public static final String ALBUM_NOT_FOUND_MSG = "Album with id: %d does not exist";

    AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.albumMapper = albumMapper;
    }


    @Override
    public Album create(SaveAlbumCommand command) {
        var artist = artistRepository.getReferenceById(command.artistId());
        var album = new AlbumEntity(command.title(), command.coverUrl(), artist);

        return albumMapper.toRecord(albumRepository.save(album));
    }

    @Override
    public Album findById(Long id) throws ResourceNotFoundException {
        var album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ALBUM_NOT_FOUND_MSG, id)));
        
        return albumMapper.toRecord(album);
    }

    @Override
    public Album update(SaveAlbumCommand command) throws ResourceNotFoundException {
        var album = albumRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ALBUM_NOT_FOUND_MSG, command.id())));

        album.setTitle(Objects.requireNonNullElse(command.title(), album.getTitle()));
        album.setCoverUrl(Objects.requireNonNullElse(command.coverUrl(), album.getCoverUrl()));

        if (command.artistId() != null ) {
            album.setArtist(artistRepository.getReferenceById(command.artistId()));
        }

        return albumMapper.toRecord(albumRepository.saveAndFlush(album));
    }

    @Override
    public List<Album> getByArtist(Long artistId) {
        return albumRepository.findByArtist(artistId);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        var album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ALBUM_NOT_FOUND_MSG, id)));

        albumRepository.delete(album);
    }
}
