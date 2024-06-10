package music.store.app.albums.domain;

import music.store.app.albums.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {

    @Query("""
            SELECT
                new music.store.app.albums.models.Album(a.id, a.title, a.coverUrl)
            FROM
                AlbumEntity a
            JOIN
                a.artist art
            WHERE
                art.id = :artistId""")
    List<Album> findByArtist(@Param("artistId") Long artistId);
}