package main.service;

import main.database.entity.global.EGlobalGenre;
import main.database.entity.global.EGlobalPlace;
import main.database.repository.global.GGenreRepository;
import main.util.MergeHelperUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<EGlobalGenre> findAll(){
        return (List<EGlobalGenre>) repository.findAll();
    }

    public void mergeGenres(EGlobalGenre keep, EGlobalGenre discard){
        String[] names1 = keep.getName().toLowerCase().split(" ");
        String[] names2 = discard.getName().toLowerCase().split(" ");

        int minMatches = Math.min(names1.length, names2.length); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getExactMatchesCount(names1, names2);

        if (matches >= minMatches){
            // enough matches, discard second author
            // TODO
            //repository.delete(discard);
            System.out.println(keep.getName()+ " : " +discard.getName());
        }
    }
}
