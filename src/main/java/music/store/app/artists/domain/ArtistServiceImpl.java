package music.store.app.artists.domain;

import music.store.app.artists.models.Artist;
import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.web.models.PagedResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public static final String ARTIST_NOT_FOUND_MSG = "Artist with id: %s does not exist";

    ArtistServiceImpl(
            ArtistRepository artistRepository,
            ArtistMapper artistMapper
    ) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Override
    public Artist create(Artist artist) {
        var entity = new ArtistEntity(artist.name(), artist.lastName());

        return artistMapper.toRecord(artistRepository.save(entity));
    }

    @Override
    public Artist findById(Long id) throws ResourceNotFoundException {
        var entity = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ARTIST_NOT_FOUND_MSG, id)));

        return artistMapper.toRecord(entity);
    }

    @Override
    public Artist update(Long id, String name, String lastName) throws ResourceNotFoundException {
        var entity = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ARTIST_NOT_FOUND_MSG, id)));

        entity.setName(name);
        entity.setLastname(lastName);

        return artistMapper.toRecord(artistRepository.saveAndFlush(entity));
    }

    @Override
    public PagedResult<Artist> getArtists(int pageNo, int pageSize) {
        var sortBy = Sort.by("id").ascending();
        var page = pageNo <= 1 ? 0 : pageNo + 1;
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        var artistsPage = artistRepository.findAllBy(pageable);

        return new PagedResult<>(
                artistsPage.getContent().stream().map(artistMapper::toRecord).toList(),
                artistsPage.getTotalElements(),
                artistsPage.getNumber() + 1,
                artistsPage.getTotalPages(),
                artistsPage.isFirst(),
                artistsPage.isLast(),
                artistsPage.hasNext(),
                artistsPage.hasPrevious()
        );
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        var entity = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ARTIST_NOT_FOUND_MSG, id)));

        artistRepository.delete(entity);
    }
}
