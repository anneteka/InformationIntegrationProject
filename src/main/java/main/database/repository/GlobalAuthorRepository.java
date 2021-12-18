package main.database.repository;

import main.database.entity.global.EGlobalAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalAuthorRepository extends CrudRepository<EGlobalAuthor, Long> {
    @Override
    <S extends EGlobalAuthor> S save(S s);

    @Override
    <S extends EGlobalAuthor> Iterable<S> saveAll(Iterable<S> iterable);
}
