package main.database.repository.source;

import main.database.entity.source.EBookThird;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThirdRepository extends CrudRepository<EBookThird, Long> {
    @Override
    <S extends EBookThird> S save(S s);

    @Override
    <S extends EBookThird> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    List<EBookThird> findAll();
}
