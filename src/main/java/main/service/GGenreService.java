package main.service;

import main.database.entity.global.EGlobalGenre;
import main.database.repository.GlobalGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GGenreService {
    private final GlobalGenreRepository repository;

    @Autowired
    public GGenreService(GlobalGenreRepository repository) {
        this.repository = repository;
    }

    public EGlobalGenre saveGenre(String genre){
        Optional<EGlobalGenre> existing = repository.findByName(genre);
        return existing.orElseGet(() -> repository.save(new EGlobalGenre(genre)));
    }
    public EGlobalGenre findGenreByName(String genreName){
        return repository.findByName(genreName).orElse(null);
    }
}
