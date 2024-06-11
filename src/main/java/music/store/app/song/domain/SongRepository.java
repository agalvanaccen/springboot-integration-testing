package music.store.app.song.domain;

import music.store.app.song.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SongRepository extends JpaRepository<SongEntity, Long> {

    @Query("""
            SELECT
                new music.store.app.song.models.Song(s.id, s.title, s.duration, new music.store.app.song.models.Album(a.id, a.title))
            FROM
                SongEntity s
            JOIN
                s.album a
            WHERE
                a.id = :albumId""")
    public List<Song> findByAlbum(@Param("albumId") Long albumId);
}
