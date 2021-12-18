package main.database.repository;

import main.database.entity.global.EGlobalBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalBookRepository extends CrudRepository<EGlobalBook, Long> {
    @Override
    <S extends EGlobalBook> S save(S s);

    @Override
    <S extends EGlobalBook> Iterable<S> saveAll(Iterable<S> iterable);
}
