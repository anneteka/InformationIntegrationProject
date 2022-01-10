package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalBook;
import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.entity.source.EBookFirst;
import main.database.entity.source.EBookSecond;
import main.database.entity.source.EBookThird;
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

import java.util.ArrayList;


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
    private GGenreService genreService;
    private GCharacterService characterService;

    @Autowired
    public GlobalBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo,
                                        GlobalAuthorRepository authorRepo, GlobalBookRepository bookRepo, 
                                            GlobalCharacterRepository characterRepo, GlobalGenreRepository genreRepo,
                                                GAuthorService authorService, GGenreService genreService, GCharacterService characterService) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.characterRepo = characterRepo;
        this.genreRepo = genreRepo;
        this.authorService = authorService;
        this.genreService = genreService;
        this.characterService = characterService;
    }

    public void setUpGlobalSchema(){
        bookRepo.saveAll( 
            StreamSupport.stream(firstRepo.findAll().spliterator(), false)       
                .map(this::firstBookToEGlobalBook)
                .collect(Collectors.toList())
        );

        bookRepo.saveAll( 
            StreamSupport.stream(secondRepo.findAll().spliterator(), false)       
                .map(this::secondBookToEGlobalBook)
                .collect(Collectors.toList())
        );

        bookRepo.saveAll( 
            StreamSupport.stream(thirdRepo.findAll().spliterator(), false)       
                .map(this::thirdBookToEGlobalBook)
                .collect(Collectors.toList())
        );

    }

    public EGlobalBook firstBookToEGlobalBook(EBookFirst firstBook){
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<EGlobalAuthor>();
    
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
                                            new ArrayList<>(), new ArrayList<>(), authorSet);
    }

    public EGlobalBook secondBookToEGlobalBook(EBookSecond secondBook){
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<EGlobalAuthor>();

        String authorList = secondBook.getAuthors();
        String authors[] = authorList.split(",");

        for (String author : authors){
            authorSet.add(authorService.saveAuthor(author.trim()));
        }

        // what to do here?
        //authorSet.add(authorService.saveAuthor(author));

        int year = Integer.parseInt(secondBook.getOriginalPublicationYear());

        return new EGlobalBook( secondBook.getIsbn13(), secondBook.getIsbn(), year,-1, 
                                -1, null, null, secondBook.getTitle(), 
                                null, secondBook.getOriginalTitle(), null,
                                null, null, null, -1,
                                -1,-1,-1,-1,
                                null,null,null, 
                                secondBook.getAverageRating(), secondBook.getImageUrl(), secondBook.getSmallImageUrl(), null,
                                null, null, new ArrayList<>(), new ArrayList<>(), authorSet);
    }

    public EGlobalBook thirdBookToEGlobalBook(EBookThird thirdBook){
        // Save author list
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<EGlobalAuthor>();
        String authorList = thirdBook.getAuthor();
        String authors[] = authorList.split(",");

        for (String author : authors){
            authorSet.add(authorService.saveAuthor(author.trim()));
        }

        // what to do here?
        //authorSet.add(authorService.saveAuthor(author));

        // Save genre list
        ArrayList<EGlobalGenre> genreSet = new ArrayList<EGlobalGenre>();
        String genreList = thirdBook.getAuthor();
        String genres[] = genreList.split(",");

        for (String genre : genres){
            genreSet.add(genreService.saveGenre(genre.trim()));
        }

        // Save character list
        ArrayList<EGlobalCharacter> characterSet = new ArrayList<EGlobalCharacter>();
        String characterList = thirdBook.getAuthor();
        String characters[] = characterList.split(",");

        for (String character : characters){
            characterSet.add(characterService.saveCharacter(character.trim()));
        }
        

        // last for characters of the string are year
        String date = thirdBook.getFirstPublishDate();
        int year = Integer.parseInt(date.substring(date.length()-4));

        return new EGlobalBook( thirdBook.getIsbn13(), thirdBook.getIsbn(), year,-1, 
                                -1, null, null, thirdBook.getTitle(), 
                                null, null, null,
                                null, null, thirdBook.getLanguage(), -1,
                                -1,-1,-1,-1,
                                null,thirdBook.getDescription(), null, 
                                thirdBook.getAvgRating(), null, null, thirdBook.getSeries(),
                                thirdBook.getPlaces(),thirdBook.getAwards(), new ArrayList<>(), new ArrayList<>(), authorSet);
    }

}
