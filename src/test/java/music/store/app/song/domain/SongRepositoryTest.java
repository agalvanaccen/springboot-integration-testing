package music.store.app.song.domain;

import jakarta.validation.ConstraintViolationException;
import music.store.app.albums.domain.AlbumRepository;
import music.store.app.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @DisplayName("Should save a new song")
    void shouldSaveASong() {
        var title = "Feel The Love";
        var duration = LocalTime.parse("00:02:46");
        var artistId = 2L;

        var song = new SongEntity(title, duration, albumRepository.getReferenceById(artistId));

        var expected = songRepository.save(song);

        assertThat(expected)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("duration", duration);

        assertThat(expected.getAlbum())
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "KIDS SEE GHOSTS");
    }

    @ParameterizedTest(name = "Given [title={0}, duration={1}, albumId={2}] should throw {3}")
    @MethodSource("saveInvalidSongProvider")
    <T extends Throwable> void shouldThrowAnExceptionWhenSavingInvalidSong(String title, LocalTime duration, Long albumId, Class<T> expectedException) {
        var album = albumId != null ? albumRepository.getReferenceById(albumId) : null;
        var song = new SongEntity(title, duration, album);

        assertThatExceptionOfType(expectedException).isThrownBy(() -> songRepository.save(song));
    }

    private static Stream<Arguments> saveInvalidSongProvider() {
        var title = "Feel The Love";
        var duration = LocalTime.parse("00:02:46");
        var albumId = 2L;

        return Stream.of(
                Arguments.of("", duration, albumId, ConstraintViolationException.class),
                Arguments.of(null, null, albumId, DataIntegrityViolationException.class),
                Arguments.of(title, null, albumId, DataIntegrityViolationException.class),
                Arguments.of(title,  duration, null, DataIntegrityViolationException.class)
        );
    }

    @Test
    @DisplayName(("Should update an existing song"))
    void shouldUpdateAnExistingSong() throws Exception {
        var id = 1L;
        var title = "All Of The Lights (Interlude)";
        var duration = LocalTime.parse("00:01:03");

        var song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Song with id: %d not found", id)));

        song.setTitle(title);
        song.setDuration(duration);

        var expected = songRepository.saveAndFlush(song);

        assertThat(expected)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("duration", duration);

        assertThat(expected.getAlbum())
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "My Beautiful Dark Twisted Fantasy");
    }

    @ParameterizedTest(name = "Given [id={0}, title={1}, duration={2}, artistId={3}] should throw {4}")
    @MethodSource("updateInvalidSongProvider")
    <T extends Throwable> void shouldThrowAnExceptionWhenUpdatingInvalidSong(Long id, String title, LocalTime duration, Long albumId, Class<T> expectedException) throws Exception {
        var song  = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Song with id: %d not found", id)));

        song.setTitle(title);
        song.setDuration(duration);
        song.setAlbum(albumId != null ? albumRepository.getReferenceById(albumId) : null);

        assertThatExceptionOfType(expectedException).isThrownBy(() -> songRepository.saveAndFlush(song));
    }

    private static Stream<Arguments> updateInvalidSongProvider() {
        var id = 1L;
        var title = "All Of The Lights (Interlude)";
        var duration = LocalTime.parse("00:01:03");
        var albumId = 1L;

        return Stream.of(
                Arguments.of(id, "", duration, albumId, ConstraintViolationException.class),
                Arguments.of(id, null, duration, albumId, DataIntegrityViolationException.class),
                Arguments.of(id, title, null, albumId, DataIntegrityViolationException.class),
                Arguments.of(id, title, duration, null, DataIntegrityViolationException.class)
        );
    }

    @Test
    @DisplayName("Should get songs by album")
    void shouldGetSongsByAlbum() {
        var songs = songRepository.findByAlbum(1L);

        assertThat(songs)
                .isNotNull()
                .hasSize(12);

        assertThat(songs.getFirst())
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "Dark Fantasy");
    }

    @Test
    @DisplayName("Should delete an existing song")
    void shouldDeleteAnExistingSong() throws Exception {
        var id = 1L;

        var song  = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Song with id: %d not found", id)));

        songRepository.delete(song);

        var expected = songRepository.findById(id);

        assertThat(expected).isNotPresent();
    }
}