package music.store.app.albums.domain;

import music.store.app.albums.models.Album;
import music.store.app.albums.models.SaveAlbumCommand;
import music.store.app.common.exceptions.ResourceNotFoundException;

import java.util.List;

public interface AlbumService {

    Album create(SaveAlbumCommand command);

    Album findById(Long id) throws ResourceNotFoundException;

    Album update(SaveAlbumCommand command) throws ResourceNotFoundException;

    List<Album> getByArtist(Long artistId);

    void delete(Long id) throws ResourceNotFoundException;
}
