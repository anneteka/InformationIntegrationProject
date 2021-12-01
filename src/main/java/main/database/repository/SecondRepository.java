package main.database.repository;

import main.database.entity.source.EBookSecond;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondRepository extends CrudRepository<EBookSecond, Long> {
    @Override
    <S extends EBookSecond> S save(S s);

    @Override
    <S extends EBookSecond> Iterable<S> saveAll(Iterable<S> iterable);
}
