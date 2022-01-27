package main.database.repository;

import main.database.entity.global.EGlobalBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalBookRepository extends CrudRepository<EGlobalBook, Long> {
    @Override
    <S extends EGlobalBook> S save(S s);

    @Override
    <S extends EGlobalBook> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<EGlobalBook> findByIsbn10(String isbn10);

    Optional<EGlobalBook> findByIsbn13(String isbn13);
    
}
