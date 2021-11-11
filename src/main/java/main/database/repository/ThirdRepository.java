package main.database.repository;

import main.database.entity.EBookThird;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThirdRepository extends CrudRepository<EBookThird, Long> {
    @Override
    <S extends EBookThird> S save(S s);

    @Override
    <S extends EBookThird> Iterable<S> saveAll(Iterable<S> iterable);
}
