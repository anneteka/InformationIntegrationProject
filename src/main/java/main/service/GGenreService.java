package main.service;

import main.database.entity.global.EGlobalGenre;
import main.database.entity.global.EGlobalPlace;
import main.database.repository.global.GGenreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GGenreService {
    private final GGenreRepository repository;
    private static final Logger LOG = LogManager.getFormatterLogger("GGenreService");


    @Autowired
    public GGenreService(GGenreRepository repository) {
        this.repository = repository;
    }

    public EGlobalGenre saveGenre(String genreName){
        Optional<EGlobalGenre> genre = repository.findByName(genreName);
        return genre.orElseGet(() -> this.repository.save(new EGlobalGenre(genreName)));
    }

    public EGlobalGenre findGenreByName(String genreName){
        return repository.findByName(genreName).orElse(null);
    }
}
