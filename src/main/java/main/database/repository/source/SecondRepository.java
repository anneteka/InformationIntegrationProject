package main.database.repository.source;

import main.database.entity.source.EBookSecond;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecondRepository extends CrudRepository<EBookSecond, Long> {
    @Override
    <S extends EBookSecond> S save(S s);

    @Override
    <S extends EBookSecond> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    List<EBookSecond> findAll();
}
