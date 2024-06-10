package music.store.app.artists.web.http.rest;

public class ArtistExamples {

    public static final String ARTISTS_PAGE = """
    {
      "results": {
        "items": [
          {
            "id": 1,
            "name": "Kanye",
            "lastName": "West"
          },
          {
            "id": 3,
            "name": "Kendrick",
            "lastName": "Lamar"
          },
          {
            "id": 2,
            "name": "Mickael",
            "lastName": "Jackson"
          }
        ],
        "totalItems": 3,
        "pageNumber": 1,
        "totalPages": 1,
        "isFirst": true,
        "isLast": true,
        "hasNext": false,
        "hasPrevious": false
      },
      "timestamp": "2024-06-05T17:01:25.447167400Z"
    }""";

    public static final String ARTIST = """
            {
              "results": {
                "id": 1,
                "name": "Kanye",
                "lastName": "West"
              },
              "timestamp": "2024-06-05T20:44:40.296698500Z"
            }""";

    public static final String ARTIST_NOT_FOUND = """
            {
               "timestamp": "2024-06-07T17:15:00.526451200Z",
               "error": {
                 "type": "NOT_FOUND",
                 "message": "Artists with id: 10 does not exist"
               }
            }""";

    public static final String ARTIST_LIST = """
            {
              "results": [
                {
                  "id": 1,
                  "name": "Kanye",
                  "lastName": "West"
                }
              ],
              "timestamp": "2024-06-05T20:46:28.199354Z"
            }""";

    public static final String ARTIST_REQUEST = """
            {
              "name": "Paris",
              "lastName": "Jackson"
            }""";

    public static final String UPDATED_CREATED_ARTIST = """
            {
              "results": {
                "id": 4,
                "name": "Paris",
                "lastName": "Jackson"
              },
              "timestamp": "2024-06-05T20:47:35.928588Z"
            }""";

    public static final String ARTIST_BAD_REQUEST = """
            {
            	"timestamp": "2024-06-07T17:08:48.802482800Z",
            	"error": {
            		"type": "BAD_REQUEST",
            		"message": "Invalid arguments have been provided",
            		"details": [
            			{
            				"property": "name",
            				"error": "Name is required"
            			},
            			{
            				"property": "lastName",
            				"error": "Last name is required"
            			}
            		]
            	}
            }""";

    private ArtistExamples () {}
}
