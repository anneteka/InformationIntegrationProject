package main.database.repository.global;

import main.database.entity.global.EGlobalCharacter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GCharacterRepository extends CrudRepository<EGlobalCharacter, Long> {
    @Override
    <S extends EGlobalCharacter> S save(S s);

    @Override
    <S extends EGlobalCharacter> Iterable<S> saveAll(Iterable<S> iterable);

    Optional<EGlobalCharacter> findByName(String name);
}