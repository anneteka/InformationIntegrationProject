package main.database.repository.global;

import main.database.entity.global.EGlobalBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GBookRepository extends CrudRepository<EGlobalBook, Long> {
    @Override
    <S extends EGlobalBook> S save(S s);

    @Override
    <S extends EGlobalBook> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<EGlobalBook> findByIsbn10(String isbn10);

    List<EGlobalBook> findAllByIsbn10(String isbn10);

    Optional<EGlobalBook> findByIsbn13(String isbn13);

    void deleteAllByTitle(String title);

    List<EGlobalBook> findAllByTitleContainsOrOriginalTitleContains(String title, String originalTitle);

    List<EGlobalBook> findAllByTitleOrOriginalTitle(String title, String originalTitle);

    List<EGlobalBook> findAll();

    @Query("select b from EGlobalBook b join b.characters c where (b.title like concat('%', :title, '%') or b.originalTitle like concat('%', :title, '%')) and c = :character and b.euroPrice < :price")
    List<EGlobalBook> query1 (String title, String character, Double price);

    List<EGlobalBook> findAllByEuroPriceLessThanEqual(Double price);

    @Query("select b from EGlobalBook b join b.characters c where c = :character")
    List<EGlobalBook> findAllByCharacter(String character);

    @Query("select b from EGlobalBook b join b.characters c where c = :character and (b.title like concat('%', :title, '%') or b.originalTitle like concat('%', :title, '%'))")
    List<EGlobalBook> findAllByTitleAndCharacter(String title, String character);

    @Query("select b from EGlobalBook b join b.characters c where c = :character and b.euroPrice <= :price")
    List<EGlobalBook> findAllByCharacterAndPrice(String character, Double price);

    @Query("select b from EGlobalBook b where (b.title like concat('%', :title, '%') or b.originalTitle like concat('%', :title, '%')) and b.euroPrice<=:price")
    List<EGlobalBook> findAllByTitleAndPrice(String title, Double price);

    List<EGlobalBook> findAllByYear(Integer year);

    @Query("select b from EGlobalBook b join b.authors a where a like concat('%', :author, '%')")
    List<EGlobalBook> findAllByAuthor(String author);

    @Query("select b from EGlobalBook b join b.authors a where a like concat('%', :author, '%') and b.year = :year")
    List<EGlobalBook> findAllByAuthorAndYear(String author, Integer year);
}
