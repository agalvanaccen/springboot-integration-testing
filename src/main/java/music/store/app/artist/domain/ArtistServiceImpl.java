package music.store.app.artist.domain;

import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.web.models.PagedResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public static final String ARTIST_NOT_FOUND_MSG = "Artists with id: %s does not exist";

    public ArtistServiceImpl(
            ArtistRepository artistRepository,
            ArtistMapper artistMapper
    ) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Override
    public Artist create(Artist artist) {
        var entity = new ArtistEntity(artist.name(), artist.lastName());

        return artistMapper.toDTO(artistRepository.save(entity));
    }

    @Override
    public Artist findById(Long id) throws ResourceNotFoundException {
        var entity = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ARTIST_NOT_FOUND_MSG, id)));

        return artistMapper.toDTO(entity);
    }

    @Override
    public Artist update(Long id, String name, String lastName) throws ResourceNotFoundException {
        var entity = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ARTIST_NOT_FOUND_MSG, id)));

        entity.setName(name);
        entity.setLastname(lastName);

        return artistMapper.toDTO(artistRepository.save(entity));
    }

    @Override
    public PagedResult<Artist> getArtists(int pageNo, int pageSize) {
        var sortBy = Sort.by("name").ascending();
        var page = pageNo <= 1 ? 0 : pageNo + 1;
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);
        Page<Artist> artistsPage = artistRepository.findAllBy(pageable);

        return new PagedResult<>(artistsPage);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        var entity = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ARTIST_NOT_FOUND_MSG, id)));

        artistRepository.delete(entity);
    }
}
