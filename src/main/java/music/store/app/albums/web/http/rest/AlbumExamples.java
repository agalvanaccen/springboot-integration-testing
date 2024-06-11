package music.store.app.albums.web.http.rest;

public class AlbumExamples {

    public static final String ALBUM_INFO = """
            {
              "timestamp": "2024-06-10T16:47:04.344240800Z",
              "results": {
                "id": 1,
                "title": "My Beautiful Dark Twisted Fantasy",
                "coverUrl": "https://lh3.googleusercontent.com/tSZcuqpZPhJIK8ItC4tkokRmIA6zEo408LFJnWb-3Nm8fv5adFiE1ArPTd_UqhaC-o8KI8tMNZIuFEo=w544-h544-l90-rj",
                "artist": {
                  "id": 1,
                  "name": "Kanye West"
                }
              }
            }""";

    public static final String ALBUM_NOT_FOUND = """
            {
              "timestamp": "2024-06-10T16:47:45.949237500Z",
              "error": {
                "type": "NOT_FOUND",
                "message": "Album with id: 10 does not exist"
              }
            }""";

    public static final String ALBUMS_BY_ARTIST_ID = """
            {
              "timestamp": "2024-06-10T16:53:25.523797200Z",
              "results": [
                {
                  "id": 1,
                  "title": "My Beautiful Dark Twisted Fantasy",
                  "coverUrl": "https://lh3.googleusercontent.com/tSZcuqpZPhJIK8ItC4tkokRmIA6zEo408LFJnWb-3Nm8fv5adFiE1ArPTd_UqhaC-o8KI8tMNZIuFEo=w544-h544-l90-rj"
                },
                {
                  "id": 2,
                  "title": "KIDS SEE GHOSTS",
                  "coverUrl": "https://lh3.googleusercontent.com/xRhLzotVKyeBwYPHcWDFzD-ndNcPLXIH9ZVNy_C7Lpt66dZ_lfR47boH7ussxcYOkYBwvbo5_KRKUYFi=w544-h544-l90-rj"
                }
              ]
            }""";

    public static final String CREATE_ALBUM_REQUEST = """
            {
              "title": "DAMN. COLLECTORS EDITION.",
              "coverUrl": "https://lh3.googleusercontent.com/B1bVbj-AbBIJxL7RHJ2B7F9fLCv2reqD-9hPQAQf77cbWpf_a0KQM-d2zHoKCXBbjtKYBlc3-18zK4I=w544-h544-l90-rj",
              "artistId": 3
            }""";

    public static final String CREATE_ALBUM_RESPONSE = """
            {
              "timestamp": "2024-06-10T16:58:25.731208200Z",
              "results": {
                "id": 5,
                "title": "DAMN. COLLECTORS EDITION.",
                "coverUrl": "https://lh3.googleusercontent.com/B1bVbj-AbBIJxL7RHJ2B7F9fLCv2reqD-9hPQAQf77cbWpf_a0KQM-d2zHoKCXBbjtKYBlc3-18zK4I=w544-h544-l90-rj",
                "artist": {
                  "id": 3,
                  "name": "Kendrick Lamar"
                }
              }
            }""";

    public static final String UPDATE_ALBUM_REQUEST = """
            {
              "title": "To Pimp A Butterfly",
              "coverUrl": "https://lh3.googleusercontent.com/L1iBW0CcjEQaXLP1coivJbjf7zSUncQ65_GpKHakOaRI81kS5pRV498PSg3VSmQg7LRMB0cJ6d-HzooO=w544-h544-l90-rj"
            }""";

    public static final String UPDATE_ALBUM_RESPONSE = """
            {
              "timestamp": "2024-06-10T17:21:59.135030900Z",
              "results": {
                "id": 3,
                "title": "To Pimp A Butterfly",
                "coverUrl": "https://lh3.googleusercontent.com/L1iBW0CcjEQaXLP1coivJbjf7zSUncQ65_GpKHakOaRI81kS5pRV498PSg3VSmQg7LRMB0cJ6d-HzooO=w544-h544-l90-rj",
                "artist": {
                  "id": 2,
                  "name": "Mickael Jackson"
                }
              }
            }""";

    public static final String CREATE_ALBUM_BAD_REQUEST = """
            {
              "timestamp": "2024-06-10T22:12:18.511863100Z",
              "error": {
                "type": "BAD_REQUEST",
                "message": "Invalid arguments have been provided",
                "details": [
                  {
                    "property": "artistId",
                    "error": "Artist id is required"
                  },
                  {
                    "property": "title",
                    "error": "Title is required"
                  }
                ]
              }
            }""";
}
