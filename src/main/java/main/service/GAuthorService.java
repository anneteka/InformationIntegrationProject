package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.repository.global.GAuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.*;

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

    public List<EGlobalAuthor> findAll(){
        return (List<EGlobalAuthor>) repository.findAll();
    }

    public void mergeAuthors(EGlobalAuthor keep, EGlobalAuthor discard){
        String[] names1 = keep.getName().split(" ");
        String[] names2 = discard.getName().split(" ");

        // sort arrays from longest name to shortes to first find full name matches
        Arrays.sort(names1, (str1, str2) -> str2.length() - str1.length());
        Arrays.sort(names2, (str1, str2) -> str2.length() - str1.length());

        int minMatches = Math.min(Math.min(names1.length, names2.length), 2); // at least 2 matching name parts or one if only one name given

        int matches = 0;

        for (String name1: names1){
            for (String name2: names2){
                if (name1.length() > 2 && name2.length() > 2){ // names are full names, not only letter + dot
                    if (name1.equals(name2)) matches++;

                }else if (matches > 0){                         // only compare name with dot if a "real" match was already found
                    if (name1.equals(name2)) matches++;
                }
            }
        }

        if (matches >= minMatches){
            // enough matches, discard second author
            repository.delete(discard);
        }
    }
}
