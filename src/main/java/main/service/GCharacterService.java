package main.service;

import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.global.GCharacterRepository;
import main.util.MergeHelperUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GCharacterService {
    private final GCharacterRepository repository;
    private static final Logger LOG = LogManager.getFormatterLogger("GCharacterService");


    @Autowired
    public GCharacterService(GCharacterRepository repository) {
        this.repository = repository;
    }

    public EGlobalCharacter saveCharacter(String name){
        Optional<EGlobalCharacter> character = repository.findByName(name);
        return character.orElseGet(() -> this.repository.save(new EGlobalCharacter(name)));
    }

    public EGlobalCharacter findCharacterByName(String name){
        return this.repository.findByName(name).orElse(null);
    }

    public List<EGlobalCharacter> findAll(){
        return (List<EGlobalCharacter>) repository.findAll();
    }

    public void mergeCharacters(EGlobalCharacter keep, EGlobalCharacter discard){
        String[] names1 = keep.getName().toLowerCase().split(" ");
        String[] names2 = discard.getName().toLowerCase().split(" ");

        int minMatches = Math.min(Math.min(names1.length, names2.length), 2); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getPersonNameMatchesCount(names1, names2);

        if (matches >= minMatches){
            // enough matches, discard second author
            // TODO
            //repository.delete(discard);
            System.out.println(keep.getName()+ " : " +discard.getName());
        }
    }
}
