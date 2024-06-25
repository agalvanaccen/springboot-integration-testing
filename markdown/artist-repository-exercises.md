# Testing `ArtistRepository`

Here would be practically the same process you followed in `AlbumRepository`. Create a class called `ArtistRepositoryTest`, add required annotations and inject
required repository.

## Exercises

### Creating a new Artist

1. Write a test to save and validate that a new artist was created, write assertions to validate that:

- The saved artist has an id and is not null
- The provided name and lastname matches with the recently created artist

2. Write a test to validate the method `save` throws an exception when invalid data is provided, write assertions to validate that:

- Given an **empty name** and an **empty lastname**, when method `save` is called should throw `ConstraintViolationException`
- Given a **null name** and a **null lastname**, when method `save` is called should throw `DataIntegrityViolationException`
- Given a **not empty name** and a **null lastname**, when method `save` is called should throw `DataIntegrityViolationException`
- Given a **null name** and a **not empty lastname**, when method `save` is called should throw `DataIntegrityViolationException`

### Updating an existing artist

1. Write a test to validate the method `saveAndFlush` is working properly, write assertions to validate that:

- The updated data matches with the recently updated artist

2. Write a test to validate the method `saveAndFlush` throws an exception when invalid data is provided, write assertions to validate that:

- Given a **not empty name** and a **null lastname**, when method `saveAndFlush` is called should throw `DataIntegrityViolationException`
- Given an **empty name** and an **empty lastname**, when method `saveAndFlush` is called should throw `ConstraintViolationException`
- Given a **null name** and a **not empty lastname**, when method `saveAndFlush` is called should throw `DataIntegrityViolationException`

### Get a page of artists

1. Write a test to validate the method `findAllBy(Pageable pageable)` returns a page containing a  list of artists, you can start the page number at `0`, use a page number of `5`
and sort the list by artist id. Then write assertions to validate that:

- The total number of items is `3` (because those are the records inserted when the application starts)
- the first element of the list is not null and its `name = Kanye`

### Delete an existing artist

1. Write a test to validate the method `delete(artist)` works properly

- Find the album by id
- Delete the album
- Then find the album by id again
- Finally write an assertion to validate the returned album by the method `findById` is empty
