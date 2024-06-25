# Testing `AlbumRepository`

## Before starting

If you go to `src/test/java/music/store/app/albums/domain` you'll find a class called `AlbumRepositoryTest`. Before proceeding 
is important to mention some key aspects.

1. The class is annotated with [`@DataJpaTest` annotation](https://www.baeldung.com/junit-datajpatest-repository), this annotation
is used to load a limited application context, which means some beans will be available to be injected using `@Autowired`, in this way
we can load part of our application along the database.
2. We're injecting two repositories: `AlbumRepository` and `ArtistRepository`, we won't test `ArtistRepository` but is necessary to get the data of an artist
before creating a new album.
3. If you take a look you can see that there are two tests, one tests verifies that we can save a new artist and the other test validates that an exception occurs
when invalid data is provided.
4. Also note that the tests are annotated with `@ParameterizedTest` annotation, [parameterized tests](https://www.baeldung.com/parameterized-tests-junit-5) are a kind
of tests that can be reused when the body of the tests is the same but the provided data or parameters are different.
This is really useful to avoid writing multiple tests that do the same but the data is different.
5. You will also notice that there is another annotation `@MethodSource("...")`, this annotation is used to
indicate the source of data to use in the tests, in this way we can provide test data to be used by the tests, if you do
a research you will find multiple ways to provide this data, we prefer this approach but you're free to choose.

Now it's time to execute these tests, for that, you can use your editor/IDE built-in functionality, or you can run the command:
`.\gradlew test --tests AlbumRepositoryTest`, this will execute all the tests present in
the indicated class. If you want to run a specific test method, again you can use your editor/IDE
or you can execute the command : `.\gradlew test --tests AlbumRepositoryTest.shouldSaveNewAlbumAndFindById`. If everything went well, you should the the results
in console or your editor/IDE.

## Exercises

### Updating an existing Album

1. Write a test to validate the method `saveAndFlush` is working properly, then write assertions to validate that:

- The updated data matches with the recently updated album

2. Write a test to validate the method `saveAndFlush` throws an exception when invalid data is provided, write assertions to validate that:

- Given an **empty title** and a **not null artist id**, method should throw `ConstraintViolationException`
- Given a **null title** and a **not null artist id**, method should throw `DataIntegrityViolationException`
- Given a **not empty title** and a **null artist id**, method should throw `DataIntegrityViolationException`

### Get albums by artist id

1. Write a test to validate the method `findByArtist` is returning a list of albums, then write assertions to validate that:

- The returned list is not null
- The returned list is not empty
- Get the first element of the list and validate is not null

### Delete an existing album

1. Write a test to validate the method `delete(album)` works properly

- Find the album by id
- Delete the album
- Then find the album by id again
- Finally write an assertion to validate the returned album by the method `findById` is empty