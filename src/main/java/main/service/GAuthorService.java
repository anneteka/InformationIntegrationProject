package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.repository.global.GAuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GAuthorService {
    private final GAuthorRepository repository;
    private static final Logger LOG = LogManager.getFormatterLogger("GAuthorService");

    @Autowired
    public GAuthorService(GAuthorRepository repository) {
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
