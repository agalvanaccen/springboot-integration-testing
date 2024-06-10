package music.store.app.albums.models;

public record SaveAlbumCommand(Long id, String title, String coverUrl, Long artistId) { }
