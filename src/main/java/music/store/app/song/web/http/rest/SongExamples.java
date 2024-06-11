package music.store.app.song.web.http.rest;

public class SongExamples {

    public static final String SONG_INFORMATION = """
            {
              "timestamp": "2024-06-11T16:34:12.952497200Z",
              "results": {
                "id": 1,
                "title": "Dark Fantasy",
                "duration": "00:04:41",
                "album": {
                  "id": 1,
                  "title": "My Beautiful Dark Twisted Fantasy"
                }
              }
            }""";

    public static final String SONG_NOT_FOUND = """
            {
              "timestamp": "2024-06-11T16:44:38.102482Z",
              "error": {
                "type": "NOT_FOUND",
                "message": "Song with id: 20 not found"
              }
            }""";

    public static final String SONGS_BY_ALBUM = """
            {
              "timestamp": "2024-06-11T16:45:06.271702Z",
              "results": [
                {
                  "id": 1,
                  "title": "Dark Fantasy",
                  "duration": "00:04:41",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 2,
                  "title": "Gorgeous",
                  "duration": "00:05:58",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 3,
                  "title": "POWER",
                  "duration": "00:04:53",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 4,
                  "title": "All Of The Lights",
                  "duration": "00:05",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 5,
                  "title": "Monster",
                  "duration": "00:06:19",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 6,
                  "title": "So Appalled",
                  "duration": "00:06:38",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 7,
                  "title": "Devil In A New Dress",
                  "duration": "00:05:52",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 8,
                  "title": "Runaway",
                  "duration": "00:09:08",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 9,
                  "title": "Hell Of A Life",
                  "duration": "00:05:28",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 10,
                  "title": "Blame Game",
                  "duration": "00:07:50",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 11,
                  "title": "Lost In The World",
                  "duration": "00:04:17",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                },
                {
                  "id": 12,
                  "title": "Who Will Survive In America",
                  "duration": "00:01:39",
                  "album": {
                    "id": 1,
                    "title": "My Beautiful Dark Twisted Fantasy"
                  }
                }
              ]
            }""";

    public static final String CREATE_SONG_REQUEST = """
            {
              "title": "All Of The Lights (Interlude)",
              "duration": "00:01:03",
              "albumId": 1
            }""";

    public static final String CREATE_SONG_RESPONSE = """
            {
              "timestamp": "2024-06-11T16:47:49.895143100Z",
              "results": {
                "id": 13,
                "title": "All Of The Lights (Interlude)",
                "duration": "00:01:03",
                "album": {
                  "id": 1,
                  "title": "My Beautiful Dark Twisted Fantasy"
                }
              }
            }""";

    public static final String CREATE_SONG_BAD_REQUEST = """
            {
              "timestamp": "2024-06-11T16:50:00.118741700Z",
              "error": {
                "type": "BAD_REQUEST",
                "message": "Invalid arguments have been provided",
                "details": [
                  {
                    "property": "title",
                    "error": "Title is required"
                  },
                  {
                    "property": "duration",
                    "error": "must match \\"^\\\\d{2}:\\\\d{2}:\\\\d{2}$\\""
                  }
                ]
              }
            }""";

    public static final String UPDATE_SONG_REQUEST = """
            {
              "duration": "00:01:03"
            }""";

    public static final String UPDAET_SONG_RESPONSE = """
            {
              "timestamp": "2024-06-11T16:59:10.371393100Z",
              "results": {
                "id": 13,
                "title": "All Of The Lights (Interlude)",
                "duration": "00:01:03",
                "album": {
                  "id": 1,
                  "title": "My Beautiful Dark Twisted Fantasy"
                }
              }
            }""";

    public static final String UPDATE_SONG_BAD_REQUEST = """
            {
              "timestamp": "2024-06-11T17:31:30.232958800Z",
              "error": {
                "type": "BAD_REQUEST",
                "message": "Invalid arguments have been provided",
                "details": [
                  {
                    "property": "duration",
                    "error": "must match \\"^\\\\d{2}:\\\\d{2}:\\\\d{2}$\\""
                  },
                  {
                    "property": "title",
                    "error": "Title must not be empty"
                  }
                ]
              }
            }""";
}
