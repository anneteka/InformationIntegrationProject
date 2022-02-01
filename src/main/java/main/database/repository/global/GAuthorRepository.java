package main.database.repository.global;

import main.database.entity.global.EGlobalAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GAuthorRepository extends CrudRepository<EGlobalAuthor, Long> {
    @Override
    <S extends EGlobalAuthor> S save(S s);

    @Override
    <S extends EGlobalAuthor> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<EGlobalAuthor> findByName(String name);
}
