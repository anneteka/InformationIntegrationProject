package main.service;

import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.global.GCharacterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void mergeCharacters(EGlobalCharacter keep, EGlobalCharacter discard){

    }
}
