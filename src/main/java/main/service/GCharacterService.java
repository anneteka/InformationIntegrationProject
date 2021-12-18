package main.service;

import main.database.entity.global.EGlobalCharacter;
import main.database.repository.GlobalCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCharacterService {
    private final GlobalCharacterRepository repository;

    @Autowired
    public GCharacterService(GlobalCharacterRepository repository) {
        this.repository = repository;
    }

    public EGlobalCharacter saveCharacter(String name){
        return this.repository.save(new EGlobalCharacter(name));
    }

    public EGlobalCharacter findCharacterByName(String name){
        return this.repository.findByName(name).orElse(null);
    }

    public void mergeCharacters(EGlobalCharacter keep, EGlobalCharacter discard){

    }
}
