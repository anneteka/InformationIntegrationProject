package main.database.repository.source;

import main.database.entity.source.EBookFirst;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstRepository extends CrudRepository<EBookFirst, Long> {
    @Override
    <S extends EBookFirst> S save(S s);

    @Override
    <S extends EBookFirst> Iterable<S> saveAll(Iterable<S> iterable);
}
