package main.database.repository;

import main.database.entity.global.EGlobalGenre;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalGenreRepository extends CrudRepository<EGlobalGenre, Long> {
    @Override
    <S extends EGlobalGenre> S save(S s);

    @Override
    <S extends EGlobalGenre> Iterable<S> saveAll(Iterable<S> iterable);
}
