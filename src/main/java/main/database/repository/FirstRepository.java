package main.database.repository;

import main.database.entity.EBookFirst;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirstRepository extends CrudRepository<EBookFirst, Long> {
    @Override
    <S extends EBookFirst> S save(S s);

    @Override
    <S extends EBookFirst> Iterable<S> saveAll(Iterable<S> iterable);
}
