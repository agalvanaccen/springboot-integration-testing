package music.store.app.albums.models;

public record Album(Long id, String title, String coverUrl, Artist artist) {

    public Album(Long id, String title, String coverUrl) {
        this(id, title, coverUrl, null);
    }

    public Album(Long id, String title, String coverUrl, Long artistId, String artistName) {
        this(id, title, coverUrl, new Artist(artistId, artistName));
    }
}
