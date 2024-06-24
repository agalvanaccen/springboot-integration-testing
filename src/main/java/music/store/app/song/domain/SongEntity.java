package music.store.app.song.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import music.store.app.albums.domain.AlbumEntity;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "songs")
class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Duration is required")
    @Column(nullable = false)
    private LocalTime duration;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

    public SongEntity() { }

    public SongEntity(Long id, String title, LocalTime duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public SongEntity(String title, LocalTime duration, AlbumEntity album) {
        this.title = title;
        this.duration = duration;
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Title is required") String title) {
        this.title = title;
    }

    public @NotNull(message = "Duration is required") LocalTime getDuration() {
        return duration;
    }

    public void setDuration(@NotNull(message = "Duration is required") LocalTime duration) {
        this.duration = duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }
}
