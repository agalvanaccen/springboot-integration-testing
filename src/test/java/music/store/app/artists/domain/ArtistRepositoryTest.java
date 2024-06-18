package music.store.app.artists.domain;

import jakarta.validation.ConstraintViolationException;
import music.store.app.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @DisplayName("Should save a new artist")
    void shouldSaveAnArtist() throws Exception {
        var artist = new ArtistEntity("Juice", "World");

        var expectedArtist = artistRepository.save(artist);

        assertThat(expectedArtist)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest(name = "Given [name={0}, lastname={1}] shoudl throw {2}")
    @MethodSource("saveInvalidArtistProvider")
    <T extends Throwable> void shouldThrowAnExceptionWhenSavingAnInvalidArtist(String name, String lastName, Class<T> expectedException) throws Exception {
        var artist = new ArtistEntity(name, lastName);

        assertThatExceptionOfType(expectedException).isThrownBy(() -> artistRepository.save(artist));
    }

    private static Stream<Arguments> saveInvalidArtistProvider() {
        return Stream.of(
                Arguments.of("", "", ConstraintViolationException.class),
                Arguments.of(null, null, DataIntegrityViolationException.class),
                Arguments.of("Drake", null, DataIntegrityViolationException.class),
                Arguments.of(null, "Jackson", DataIntegrityViolationException.class)
        );
    }

    @Test
    @DisplayName("Should update an existing artist")
    void shouldUpdateAnExistingArtist() throws Exception {
        var id = 2L;
        var name = "Janet";
        var lastname = "Jackson";

        var artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Artist with id: %d not found", id)));

        artist.setName(name);
        artist.setLastname(lastname);

        var expected = artistRepository.saveAndFlush(artist);

        assertThat(expected)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrPropertyWithValue("lastname", lastname);
    }

    @ParameterizedTest(name = "Given [id={0}, name={1}, lastname={2}] should throw {3}")
    @MethodSource("updateInvalidArtistProvider")
    <T extends Throwable> void shouldThrowAnExceptionWhenUpdatingInvalidArtist(Long id, String name, String lastName, Class<T> expectedException) throws Exception {
        var artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Artist with id: %d not found", id)));

        artist.setName(name);
        artist.setLastname(lastName);

        assertThatExceptionOfType(expectedException).isThrownBy(() -> artistRepository.saveAndFlush(artist));
    }

    public static Stream<Arguments> updateInvalidArtistProvider() {
        return Stream.of(
                Arguments.of(1L, "Ye", null, DataIntegrityViolationException.class),
                Arguments.of(1L, "", "", ConstraintViolationException.class),
                Arguments.of(1L, null, "Ye", DataIntegrityViolationException.class)
        );
    }

    @Test
    @DisplayName("Should delete an existing artist")
    void shouldDeleteAnExistingArtist() throws Exception {
        var id = 3L;
        var artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Artist with id: %d not found", id)));

        artistRepository.delete(artist);

        var expected = artistRepository.findById(id);

        assertThat(expected).isNotPresent();
    }

    @Test
    @DisplayName("Should get artists")
    void shouldGetArtistsPage() throws Exception {
        var pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        var expected = artistRepository.findAllBy(pageable);

        assertThat(expected.getTotalElements()).isEqualTo(3);
        assertThat(expected.getContent().getFirst()).hasFieldOrPropertyWithValue("name", "Kanye");
    }
}