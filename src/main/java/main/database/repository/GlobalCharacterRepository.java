package main.database.repository;

import main.database.entity.global.EGlobalCharacter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalCharacterRepository extends CrudRepository<EGlobalCharacter, Long> {
    @Override
    <S extends EGlobalCharacter> S save(S s);

    @Override
    <S extends EGlobalCharacter> Iterable<S> saveAll(Iterable<S> iterable);
}