package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalBook;
import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.FirstRepository;
import main.database.repository.GlobalAuthorRepository;
import main.database.repository.GlobalBookRepository;
import main.database.repository.GlobalCharacterRepository;
import main.database.repository.GlobalGenreRepository;
import main.database.repository.SecondRepository;
import main.database.repository.ThirdRepository;
import main.util.CsvParserUtil;
import main.util.cvs.model.BlackwellBook;
import main.util.cvs.model.CsvSecondBook;
import main.util.cvs.model.CsvThirdBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GlobalBookDuplicateService {
    private GlobalAuthorRepository authorRepo;
    private GlobalBookRepository bookRepo;
    private GlobalCharacterRepository characterRepo;
    private GlobalGenreRepository genreRepo;
    private FirstRepository firstRepo;
    private SecondRepository secondRepo;
    private ThirdRepository thirdRepo;

    @Autowired
    public GlobalBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo,
                                        GlobalAuthorRepository authorRepo, GlobalBookRepository bookRepo, 
                                            GlobalCharacterRepository characterRepo, GlobalGenreRepository genreRepo) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.characterRepo = characterRepo;
        this.genreRepo = genreRepo;
    }

    public void setUpGlobalSchema(){
        bookRepo.saveAll( 
            StreamSupport.stream(firstRepo.findAll().spliterator(), false)       
                .map(EGlobalBook::new)
                .collect(Collectors.toList())
        );

    }

}
