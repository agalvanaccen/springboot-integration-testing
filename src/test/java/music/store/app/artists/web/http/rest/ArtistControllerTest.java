package music.store.app.artists.web.http.rest;

import music.store.app.artists.models.Artist;
import music.store.app.artists.web.http.rest.models.ArtistRequest;
import music.store.app.common.web.models.BaseErrorResult;
import music.store.app.common.web.models.BaseResult;
import music.store.app.common.web.models.PagedResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistControllerTest {

    private static final String INVALID_ARGUMENTS = "Invalid arguments have been provided";
    private static final String ARTIS_NOT_FOUND = "Artist with id: %d does not exist";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/music-store/api/v1/artists";
    }

    @Test
    @DisplayName("Should get a page of artists")
    void shouldGetArtistsPage() {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .queryParam("pageNo", 1)
                .queryParam("pageSize", 5)
                .build()
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResult<PagedResult<Artist>>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResults()).isNotNull();
        assertThat(response.getBody().getResults().items()).isNotEmpty();

        assertThat(response.getBody().getResults().items().getFirst())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Kanye")
                .hasFieldOrPropertyWithValue("lastName", "West");
    }

    @Test
    @DisplayName("Should get an artist by id")
    void shouldGetAnArtistById() {
        var artistId = 1L;

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .pathSegment("{id}")
                .buildAndExpand(artistId)
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseResult<Artist>>() {}
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResults())
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Kanye")
                .hasFieldOrPropertyWithValue("lastName", "West");
    }

    @Test
    @DisplayName("Should return 404 for a non-existing artist id")
    void shouldReturnNotFoundWhenFindingById() {
        var artistId = 10L;

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .pathSegment("{id}")
                .buildAndExpand(artistId)
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<BaseErrorResult>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError())
                .isNotNull()
                .hasFieldOrPropertyWithValue("type", HttpStatus.NOT_FOUND.name())
                .hasFieldOrPropertyWithValue("message", String.format(ARTIS_NOT_FOUND, artistId));
    }

    @Test
    @DisplayName("Should create a new Artist and return 201")
    void shouldAddNewArtist() {
        var name = "Kid";
        var lastName = "Cudi";

        var requestBody = new HttpEntity<ArtistRequest>(new ArtistRequest(name, lastName));

        var response = restTemplate.exchange(
                this.baseUrl(),
                HttpMethod.POST,
                requestBody,
                new ParameterizedTypeReference<BaseResult<Artist>>() {}
        );

        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getResults())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("name" ,name)
                .hasFieldOrPropertyWithValue("lastName" ,lastName);
    }

    @ParameterizedTest(name = "Given [name={0}, lastName={1}] should return 400")
    @MethodSource("createNewArtistInvalidProvider")
    void shouldReturn400WhenCreateNewArtistWithInvalidData(String name, String lastName) {
        var requestBody = new HttpEntity<ArtistRequest>(new ArtistRequest(name, lastName));

        var response = restTemplate.exchange(
                this.baseUrl(),
                HttpMethod.POST,
                requestBody,
                new ParameterizedTypeReference<BaseErrorResult>() {}
        );

        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getError())
                .isNotNull()
                .hasFieldOrPropertyWithValue("type", HttpStatus.BAD_REQUEST.name())
                .hasFieldOrPropertyWithValue("message", INVALID_ARGUMENTS);

        assertThat(responseBody.getError().details()).isNotEmpty();
    }

    private static Stream<Arguments> createNewArtistInvalidProvider() {
        var name = "Kid";
        var lastName = "Cudi";

        return Stream.of(
                Arguments.of(name, ""),
                Arguments.of("", lastName),
                Arguments.of("", ""),
                Arguments.of(null, null)
        );
    }

    @Test
    @DisplayName("Should update an existing Artist")
    void shouldUpdateAnExistingArtist() {
        var artistId = 3L;
        var name = "Kid";
        var lastName = "Cudi";

        var requestBody = new HttpEntity<ArtistRequest>(new ArtistRequest(name, lastName));

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .pathSegment("{id}")
                .buildAndExpand(artistId)
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                requestBody,
                new ParameterizedTypeReference<BaseResult<Artist>>() {}
        );

        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getResults())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("name" ,name)
                .hasFieldOrPropertyWithValue("lastName" ,lastName);
    }

    @ParameterizedTest(name = "Given [artistId={0}, name={1}, title={2}] should return http={3} with message={4}")
    @MethodSource("invalidArtistUpdateDataProvider")
    void shouldReturn404Or400WithInvalidIdOrArtistData(Long artistId, String name, String lastName, HttpStatus expectedHttpStatus, String expectedMessage) {
        var requestBody = new HttpEntity<>(new ArtistRequest(name, lastName));

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .pathSegment("{id}")
                .buildAndExpand(artistId)
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                requestBody,
                new ParameterizedTypeReference<BaseErrorResult>() {}
        );

        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getError())
                .isNotNull()
                .hasFieldOrPropertyWithValue("type", expectedHttpStatus.name())
                .hasFieldOrPropertyWithValue("message", expectedMessage);
    }

    private static Stream<Arguments> invalidArtistUpdateDataProvider() {
        var artistId = 3L;
        var name = "Kid";
        var lastName = "Cudi";

        return Stream.of(
                Arguments.of(10L, name, lastName, HttpStatus.NOT_FOUND, String.format(ARTIS_NOT_FOUND, 10L)),
                Arguments.of(artistId, "", lastName, HttpStatus.BAD_REQUEST, INVALID_ARGUMENTS),
                Arguments.of(artistId, name, "", HttpStatus.BAD_REQUEST, INVALID_ARGUMENTS),
                Arguments.of(artistId, "", "", HttpStatus.BAD_REQUEST, INVALID_ARGUMENTS),
                Arguments.of(artistId, null, null, HttpStatus.BAD_REQUEST, INVALID_ARGUMENTS)
        );
    }

    @Test
    @DisplayName("Should delete an existing Artist")
    void shouldCreateAndDeleteAnExistingArtist() {
        var name = "Kid";
        var lastName = "Cudi";

        var requestBody = new HttpEntity<ArtistRequest>(new ArtistRequest(name, lastName));

        var created = restTemplate.exchange(
                this.baseUrl(),
                HttpMethod.POST,
                requestBody,
                new ParameterizedTypeReference<BaseResult<Artist>>() {}
        );

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .pathSegment("{id}")
                .buildAndExpand(created.getBody().getResults().id())
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<BaseResult<Void>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody())
                .isNotNull()
                .hasFieldOrProperty("timestamp");
    }

    @Test
    @DisplayName("Given a non-exsiting artisId should return 404")
    void shouldReturn404WhenNonExistingArtistIdIsProvided() {
        var artistId = 10L;

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl())
                .pathSegment("{id}")
                .buildAndExpand(artistId)
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<BaseErrorResult>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getError())
                .isNotNull()
                .hasFieldOrPropertyWithValue("type", HttpStatus.NOT_FOUND.name())
                .hasFieldOrPropertyWithValue("message", String.format(ARTIS_NOT_FOUND, artistId));
    }
}