package music.store.app.artist.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;

@Entity
@Table(name = "artists")
public class ArtistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Last name is required")
    @Column(nullable = false)
    private String lastname;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public ArtistEntity() { }

    public ArtistEntity(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public ArtistEntity(Long id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Last name is required") String getLastname() {
        return lastname;
    }

    public void setLastname(@NotEmpty(message = "Last name is required") String lastname) {
        this.lastname = lastname;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
