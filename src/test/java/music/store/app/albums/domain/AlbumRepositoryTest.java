package music.store.app.albums.domain;

import jakarta.validation.ConstraintViolationException;
import music.store.app.artists.domain.ArtistRepository;
import music.store.app.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @ParameterizedTest(name = "Given [id={0}, title={1}, cover={2}, artistId={3}] should update an existing album")
    @MethodSource("updateAlbumProvider")
    void shouldUpdateAnExistingAlbum(Long id, String title, String coverUrl, Long artistId) throws Exception {
        var album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Album with id: %d not found", id)));

        album.setTitle(title);
        album.setCoverUrl(coverUrl);

        if (null != artistId) {
            album.setArtist(artistRepository.getReferenceById(artistId));
        }

        var updated = albumRepository.save(album);

        assertThat(updated)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("coverUrl", coverUrl);

        assertThat(updated.getArtist())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", artistId != null ? artistId : album.getArtist().getId());
    }

    private static Stream<Arguments> updateAlbumProvider() {
        return Stream.of(
                Arguments.of(1L, "Graduation", "https://shorturl.at/JkVWV", null),
                Arguments.of(2L, "KIDS SEE GHOSTS", "https://shorturl.at/YPbIV", 3L),
                Arguments.of(3L, "XSCAPE", null, null)
        );
    }

    @ParameterizedTest(name = "Given [title={1}, created_at={2}, artistId={3}] should throw {4}")
    @MethodSource("updateAlbumInvalidProvider")
    <T extends Throwable> void shouldThrowAnExceptionWhenUpdatingWithInvalidData(Long id, String title, Long artistId, Class<T> expectedException) throws Exception {
        var album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Album with id: %d was not found", id)));

        album.setTitle(title);

        if (artistId == null) {
            album.setArtist(null);
        }

        assertThatExceptionOfType(expectedException).isThrownBy(() -> albumRepository.saveAndFlush(album));
    }

    private static Stream<Arguments> updateAlbumInvalidProvider() {
        return Stream.of(
                Arguments.of(1L, "", 1L, ConstraintViolationException.class),
                Arguments.of(1L, null, 1L, DataIntegrityViolationException.class),
                Arguments.of(1L, "Donda", null, DataIntegrityViolationException.class)
        );
    }

    @Test
    @DisplayName("Should delete an existing album")
    void shouldDeleteAnExistingAlbum() throws Exception {
        var id = 1L;
        var album = albumRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Album with id: %d was not found", id)));

        albumRepository.delete(album);

        var expected = albumRepository.findById(id);

        assertThat(expected).isNotPresent();
    }

    @ParameterizedTest(name = "Given artist id = {0} should return {1} albums")
    @MethodSource("getAlbumsByIdProvider")
    void shouldGetAlbumsByArtistId(Long artistId, int expectedSize) throws Exception {
        var albums = albumRepository.findByArtist(artistId);

        assertThat(albums)
                .isNotNull()
                .hasSize(expectedSize);
    }

    private static Stream<Arguments> getAlbumsByIdProvider() {
        return Stream.of(
                Arguments.of(1L, 2),
                Arguments.of(2L, 1),
                Arguments.of(3L, 1)
        );
    }
}