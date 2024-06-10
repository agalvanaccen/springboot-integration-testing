package music.store.app.artists.domain;

import music.store.app.artists.models.Artist;
import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.web.models.PagedResult;

public interface ArtistService {

    Artist create(Artist artist);

    Artist findById(Long id) throws ResourceNotFoundException;

    Artist update(Long id, String name, String lastName) throws ResourceNotFoundException;

    PagedResult<Artist> getArtists(int pageNo, int pageSize);

    void delete(Long id) throws ResourceNotFoundException;
}
