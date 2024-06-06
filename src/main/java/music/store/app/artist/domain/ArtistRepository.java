package music.store.app.artist.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {

    @Query("""
            SELECT new music.store.app.artist.domain.Artist(a.id, a.name, a.lastname)
            FROM ArtistEntity a WHERE a.name LIKE CONCAT('%',:name,'%')""")
    List<Artist> findByName(@Param("name") String name);

    @Query("""
            SELECT new music.store.app.artist.domain.Artist(a.id, a.name, a.lastname)
            FROM ArtistEntity a""")
    Page<Artist> findAllBy(Pageable pageable);
}
