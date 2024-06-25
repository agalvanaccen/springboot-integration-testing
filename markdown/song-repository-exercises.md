# Testing `SongRepository`

Create a class called `SongRepositoryTest`, add required annotations and inject required repositories: `SongRepository` and `AlbumRepository` (required for saving)
a new song.

## Exercises

### Create a new Song

1. Write a test to save and validate a new song was created, write assertions to validate that:

- The recently created song has an id and is not null
- The provided song data matches with the data of the recently created song

2. Write a test the method `save` throws an exception when invalid data is provided, write assertions to validate that:

- Given an **empty title**, **not null duration** and a **not null album id**, when method `saved` is called should throw `ConstraintViolationException`
- Given a **null title**, a **null duration** and a **not null album id**, when method `saved` should throw `DataIntegrityViolationException`
- Given a **not null or empty title**, a **null duration** and a **not null album id**, when method `saved` is called should throw `DataIntegrityViolationException`
- Given a **not null or empty**, a **not null duration** and a **null album id**, when method `save` is called should throw  `DataIntegrityViolationException`

### Update an existing Song

1. Write a test to validate the method `saveAndFlush` is working properly, write assertions to validate:

- The updated data matches the recently updated song data

2. Write a test to validate the method `saveAndFlush` throws an exception when invalid data is provided, write assertions to validate that:

- Given an **empty title**, **not null duration** and **not null album id**, when method `saveAndFlush` is called should throw `ConstraintViolationException`
- Given a **null title**, **not null duration** and **not null album id**, when method `saveAndFlush` is called should throw `DataIntegrityViolationException`
- Given a **not null or empty title**, **null duration**, **not null album id**, when method `saveAndFlush` is called should throw `DataIntegrityViolationException`
- Given a **not null or empty title**, **not null duration**, **null album id**, when method `saveAndFlush` is called should throw `DataIntegrityViolationException`

### Get songs by album

1. Write a test to validate method `findByAlbum` is working properly, write assertions to validate that

- Given album id 1L, the list of albums must not be null or empty
- The first element is not null and title is equal to `Dark Fantasy`

### Delete an existing song

1. Write a test to validate the method `delete(song)` is working properly

- Find the song by id
- Delete the song
- Find the song by id again
- Write an assertion to validate the song returned is empty