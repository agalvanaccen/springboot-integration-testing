package music.store.app.song.web.http.rest.models;

public record SongDTO(Long id, String title, String duration, AlbumDTO album) { }
