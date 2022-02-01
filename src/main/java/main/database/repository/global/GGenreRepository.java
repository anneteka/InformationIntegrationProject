package main.database.repository.global;

import main.database.entity.global.EGlobalGenre;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GGenreRepository extends CrudRepository<EGlobalGenre, Long> {
    @Override
    <S extends EGlobalGenre> S save(S s);

    @Override
    <S extends EGlobalGenre> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<EGlobalGenre> findByName(String genreName);
}
