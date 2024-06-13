package music.store.app.albums.domain;

import music.store.app.artists.domain.ArtistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @DisplayName("Given a new Album, it should be saved and the found by id")
    void shouldSaveNewAlbumAndFindById() throws Exception {
        var artist = artistRepository.getReferenceById(1L);
        var album = new AlbumEntity("Donda", "", artist);

        var saved = albumRepository.save(album);

        assertThat(saved)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", saved.getId())
                .hasFieldOrPropertyWithValue("title", "Donda")
                .hasFieldOrProperty("artist");
    }
}