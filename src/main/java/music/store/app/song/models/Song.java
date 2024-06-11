package music.store.app.song.models;

import java.time.LocalTime;

public record Song(Long id, String title, LocalTime duration, Album album) {

    public Song(Long id, String title, LocalTime duration) {
        this(id, title, duration, null);
    }
}
