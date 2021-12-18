package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalBook;
import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.entity.source.EBookFirst;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import java.util.HashSet;

@Service
public class GlobalBookDuplicateService {
    private GlobalAuthorRepository authorRepo;
    private GlobalBookRepository bookRepo;
    private GlobalCharacterRepository characterRepo;
    private GlobalGenreRepository genreRepo;
    private FirstRepository firstRepo;
    private SecondRepository secondRepo;
    private ThirdRepository thirdRepo;
    private GAuthorService authorService;

    @Autowired
    public GlobalBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo,
                                        GlobalAuthorRepository authorRepo, GlobalBookRepository bookRepo, 
                                            GlobalCharacterRepository characterRepo, GlobalGenreRepository genreRepo,
                                                GAuthorService authorService) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.characterRepo = characterRepo;
        this.genreRepo = genreRepo;
        this.authorService = authorService;
    }

    public void setUpGlobalSchema(){
        bookRepo.saveAll( 
            StreamSupport.stream(firstRepo.findAll().spliterator(), false)       
                .map(this::firstBookToEGlobalBook)
                .collect(Collectors.toList())
        );

    }

    public EGlobalBook firstBookToEGlobalBook(EBookFirst firstBook){
        HashSet<EGlobalAuthor> authorSet = Collections.emptySet();
    
        String authorList = firstBook.getAuthor();

        // filter out role inside of parathesese
        String authors[] = authorList.split(",");
        for (String author : authors){
            if (author.contains("(") && author.contains(")")){
                int indexOpen = author.indexOf("(");
                int indexClose = author.indexOf(")");
                author = author.substring(0, indexOpen) + author.substring(indexClose+1, authorList.length());
            }
            author = author.trim();

            authorSet.add(authorService.saveAuthor(author));
        }

        return new EGlobalBook(firstBook.getIsbn(), null, -1, firstBook.getEuro_price(), firstBook.getDiscount_euro(),
                                            firstBook.getType(), firstBook.getLinkBookPage(), firstBook.getName(), 
                                            firstBook.getSubtitle(), null, firstBook.getEdition(), firstBook.getPublisher(),
                                            firstBook.getPublished_country(), firstBook.getLanguage(), -1, firstBook.getHeight(),
                                            firstBook.getWidth(), firstBook.getSpine(), firstBook.getWeight(), firstBook.getShortBlurb(),
                                            firstBook.getLongBlurb(),
                                            firstBook.getBlurbReview(), -1, null, null, null, null, null,
                                            Collections.emptySet(), Collections.emptySet(), authorSet); 
    }

}
