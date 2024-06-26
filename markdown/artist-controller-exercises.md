# Testing `ArtistController`

## Before starting

It is time to test the presentation layer, if you go to `src/test/java/music/store/app/artists/web/http/rest` you'll find a class called `ArtistControllerTest` and
as you may imagine this class will contain tests for the `ArtistController` class. If you take a look at this class you'll notice that this class is annotated with
`@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`, let's decompose this annotation:

- [`@SpringBootTest`](https://howtodoinjava.com/spring-boot2/testing/springboottest-annotation/) is a specialized and convenient annotation used to indicate to Spring
that the application context needs to be loaded, allowing us to inject components using `@Autowired` annotation.
- `webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT` indicates that the application will run in a random port when starts.

Then you will also notice that there are two components injected:

```java
@LocalServerPort
private int port;

@Autowired
private TestRestTemplate restTemplate;
```

- `@LocalServerPort` injects at run time, the port allocated at the moment the application starts, thanks to this we can have access to that port that was created randomly.
- [`TestRestTemplate`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/test/web/client/TestRestTemplate.html) is a component that allows to test REST APIs and its suitable for integration tests.
You may also find integration test examples using [MockMvc](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html), the provided examples use `TestRestTemplate`
but you're free to choose.

Finally, you'll find a test method that validates the `GET /music-store/api/v1/artists?pageNo=1&pageSize=5` API which returns a paginated list of artists, this method:

- Prepare the URL
- Executes an HTTP GET call using `RestTestTemplate`'s method `exchange`
- Finally execute some assertions to validate that:
  - The expected HTTP status code of the response is `200`
  - The request body is not null
  - The results object is not null
  - The list of artists is not null
  - The first element of the list is not null and has a name and lastname with certain values

This is how we're going to be testing presentation layer, it's time to practice.

## Exercises

### Test finding an Artist by id

1. Write a test to validate `GET /music-store/api/v1/artists/{id}` API is working properly, then write assertions to validate that:

- The response's HTTP status code is `200`
- The response body is not null
- The `results` property inside body is not null

2. Write a test to validate `GET /music-store/api/v1/artists/{id}` API returns a `404` HTTP status code when a non-existing artist id
is provided, write assertions to validate that:

- The response's HTTP status code is `404` 
- The response body is not null
- The `error` object inside body is not null, has a property `type` with value `NOT_FOUND` and a property `message` with value `Artist with id: %d does not exist`

### Test creating a new Artist

1. Write a test to validate that `POST /music-store/api/v1/artists` is working properly, write assertions to validate that:

- The response's HTTP status code is `201`
- The response body is not null
- The `results` property inside body is not null, has a property `id` that is not null, and the provided `name` and `lastname` in the
request body match with the recently created artist data.

2. Write a test to validate that `POST /music-store/api/v1/artists` API returns a  `400` HTTP status code when invalid artist data
is provided in the request body, write assertions to validate that:

- The response's HTTP status code is `400`
- The response body is not null
- The `error` object inside body is not null, has a property `type` with value `BAD_REQUEST`, a property `message` with value `Invalid arguments have been provided`
and a property `details` that is not null and is not empty

### Test updating an Artist

1. Write a test to validate `PUT /music-store/api/v1/artists/{id}` API is working properly, write assertions to validate that:

- The response's HTTP status code is `200`
- The response body is not null
- The `results` property inside body is not null and the data matches the provided data in the request body

2. Write a test to validate `PUT /music-store/api/v1/artists/{id}` API returns a `404` HTTP status code when a non-existing artist id
is provided, write assertions to validate that:

- The response's HTTP status code is `404`
- The response body is not null
- The `error` object inside body is not null, has a property `type` with value `NOT_FOUND` and a property `message` with value `Artist with id: {id} does not exist`

3. Write a test to validate `PUT /music-store/api/v1/artists/{id}` API returns a `400` HTTP status code when invalid data is provided
in the request body, write assertions to validate that:

- The response's HTTP status code is `400`
- The response body is not null
- The `error` object inside body is not null, has a property `type` with value `BAD_REQUEST`, a property `message` with value `Invalid arguments have been provided`
  and a property `details` that is not null and is not empty

> 💡 **NOTE**
>
> To test both `404` and `400` HTTP status code using one test you can use parameterized tests

### Test deleting an Artist

1. Write a test to validate `DELETE /music-store/api/v1/artists/{id}` API is working properly, write assertions to validate that:

- The response's HTTP status code is `202`
- The response body is not null
- The `results` property has a property `timestamp` and is not null (Optional)

2. Write a test to validate `DELETE /music-store/api/v1/artists/{id}` API returns a `404` HTTP status code when a non-existing artist id
is provided, write assertions to validate that:

- The response's HTTP status code is `404`
- The response body is not null
- The `error` object inside body is not null, has a property `type` with value `NOT_FOUND` and a property `message` with value `Artist with id: {id} does not exist`