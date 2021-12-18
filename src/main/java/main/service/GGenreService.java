package main.service;

import main.database.entity.global.EGlobalGenre;
import main.database.repository.GlobalGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GGenreService {
    private GlobalGenreRepository repository;

    @Autowired
    public GGenreService(GlobalGenreRepository repository) {
        this.repository = repository;
    }

    public EGlobalGenre saveGenre(String genre){
        return repository.save(new EGlobalGenre(genre));
    }
    public EGlobalGenre findGenreByName(String genreName){
        return repository.findByName(genreName);
    }
}
