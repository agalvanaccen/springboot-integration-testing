package music.store.app.albums.domain;

import jakarta.validation.ConstraintViolationException;
import music.store.app.artists.domain.ArtistRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @ParameterizedTest(name = "Given [title={0}, cover={1}, artistId={2}], should create a new album")
    @MethodSource("createAlbumProvider")
    void shouldSaveNewAlbumAndFindById(String title, String coverUrl, Long artistId) throws Exception {
        var artist = artistRepository.getReferenceById(artistId);
        var album = new AlbumEntity(title, coverUrl, artist);

        var saved = albumRepository.save(album);

        assertThat(saved)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", saved.getId())
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("coverUrl", coverUrl)
                .hasFieldOrProperty("artist");
    }

    private static Stream<Arguments> createAlbumProvider() {
        return Stream.of(
                Arguments.of(
                        "JESUS IS KING",
                        "https://lh3.googleusercontent.com/J2oSU6vw-2wolksQfk-NrjUzljrwugnl6TecdTvUt6LHsBaeHhv2ywA1HkqYKzfCCr3ObUUt5Ky3iN9W=w544-h544-l90-rj",
                        1L
                ),
                Arguments.of("Donda", null, 1L)
        );
    }

    @ParameterizedTest(name = "Should throw an exception when [title={0}, coverUrl={1}, artistId={2}]")
    @MethodSource("createAlbumInvalidProvider")
    <T extends Throwable> void shouldThrowAnExceptionWhenCreatingWithInvalidData(String title, String coverUrl, Long artistId, Class<T> expectedException) {
        var artist = artistId != null ? artistRepository.getReferenceById(artistId) : null;
        var album = new AlbumEntity(title, coverUrl, artist);

        assertThatExceptionOfType(expectedException).isThrownBy(() -> albumRepository.save(album));
    }

    private static Stream<Arguments> createAlbumInvalidProvider() {
        return Stream.of(
                Arguments.of(null, null, 1L, DataIntegrityViolationException.class),
                Arguments.of("Donda", null, null, DataIntegrityViolationException.class),
                Arguments.of("", null, 1L, ConstraintViolationException.class),
                Arguments.of(null, null, null, DataIntegrityViolationException.class)
        );
    }
}