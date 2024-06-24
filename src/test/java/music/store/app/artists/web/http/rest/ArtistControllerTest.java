package music.store.app.artists.web.http.rest;

import music.store.app.artists.models.Artist;
import music.store.app.common.web.models.BaseResult;
import music.store.app.common.web.models.PagedResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistControllerTest {

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
}