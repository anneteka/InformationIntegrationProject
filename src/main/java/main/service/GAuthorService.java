package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.GlobalAuthorRepository;
import main.database.repository.GlobalGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GAuthorService {
    private final GlobalAuthorRepository repository;

    @Autowired
    public GAuthorService(GlobalAuthorRepository repository) {
        this.repository = repository;
    }

    public EGlobalAuthor saveAuthor(String author){
        Optional<EGlobalAuthor> existing = repository.findByName(author);
        return existing.orElseGet(() -> repository.save(new EGlobalAuthor(author)));
    }

    public EGlobalAuthor findAuthorByName(String name){
        return repository.findByName(name).orElse(null);
    }

    public void mergeAuthors(EGlobalAuthor keep, EGlobalAuthor discard){
        // placeholder
    }
}
