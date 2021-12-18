package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalBook;
import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.FirstRepository;
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

@Service
public class GlobalBookDuplicateService {
<<<<<<< HEAD
    private EGlobalAuthor authorRepo;
    private EGlobalBook bookRepo;
    private EGlobalCharacter characterRepo;
    private EGlobalGenre genreRepo;
=======
>>>>>>> 57094ff86a1003943dee9e62a3cb5bf2ed0fb677
    private FirstRepository firstRepo;
    private SecondRepository secondRepo;
    private ThirdRepository thirdRepo;

    @Autowired
<<<<<<< HEAD
    public GlobalBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo,
                                        EGlobalAuthor authorRepo, EGlobalBook bookRepo, EGlobalCharacter characterRepo, 
                                            EGlobalGenre genreRepo) {
=======
    public GlobalBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo) {
>>>>>>> 57094ff86a1003943dee9e62a3cb5bf2ed0fb677
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.characterRepo = characterRepo;
        this.genreRepo = genreRepo;
    }

    public void setUpGlobalSchema(){

    }

}
