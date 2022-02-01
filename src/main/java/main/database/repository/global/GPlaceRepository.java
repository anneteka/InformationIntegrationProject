package main.database.repository.global;

import main.database.entity.global.EGlobalPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GPlaceRepository extends CrudRepository<EGlobalPlace, EGlobalPlace> {
    public Optional<EGlobalPlace> findByPlace(String place);

    @Override
    <S extends EGlobalPlace> S save(S s);

    @Override
    <S extends EGlobalPlace> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<EGlobalPlace> findById(EGlobalPlace place);

    @Override
    Iterable<EGlobalPlace> findAll();

    @Override
    void deleteById(EGlobalPlace place);

    @Override
    void delete(EGlobalPlace place);
}
