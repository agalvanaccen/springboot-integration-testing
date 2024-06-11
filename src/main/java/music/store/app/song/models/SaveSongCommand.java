package music.store.app.song.models;

import java.time.LocalTime;

public record SaveSongCommand(Long id, String title, LocalTime duration, Long albumId) { }
