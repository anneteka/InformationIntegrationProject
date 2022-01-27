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
import java.util.List;import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import java.util.ArrayList;


@Service
public class GlobalBookDuplicateService {
    private final GlobalAuthorRepository authorRepo;
    private final GlobalBookRepository bookRepo;
    private final GlobalCharacterRepository characterRepo;
    private final GlobalGenreRepository genreRepo;
    private final FirstRepository firstRepo;
    private final SecondRepository secondRepo;
    private final ThirdRepository thirdRepo;
    private final GAuthorService authorService;
    private final GGenreService genreService;
    private final GCharacterService characterService;

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

    public void setUpGlobalSchema() {
        insertFirstSource();

        insertSecondSource();

        inserThirdSource();

        mergeDuplicates();
    }

    public void mergeDuplicates(){
        //TODO similarity measure between the closet neighbours ?
    }

    public void insertFirstSource(){
        bookRepo.saveAll(
                StreamSupport.stream(firstRepo.findAll().spliterator(), false)
                        .map(this::firstBookToEGlobalBook)
                        .collect(Collectors.toList())
        );
    }
    public void insertSecondSource() {
        List<EGlobalBook> books =
                StreamSupport.stream(secondRepo.findAll().spliterator(), false)
                .map(this::secondBookToEGlobalBook)
                .collect(Collectors.toList()
                );
        for (EGlobalBook book : books) {
            Optional<EGlobalBook> globalBook = bookRepo.findByIsbn13(book.getIsbn13());
            Optional<EGlobalBook> globalBook2 = bookRepo.findByIsbn10(book.getIsbn10());
            if (!globalBook.isPresent() || !globalBook2.isPresent()){
                bookRepo.save(book);
            }
            else {
                globalBook.get().getAuthors().addAll(book.getAuthors());
                if (!book.getPublication_date().equals("")) globalBook.get().setPublication_date(book.getPublication_date());
                if (!book.getOriginalTitle().equals("")) globalBook.get().setOriginalTitle(book.getOriginalTitle());
                if (!book.getTitle().equals("")) globalBook.get().setTitle(book.getTitle());
                if (!book.getAverageRating().equals("")) globalBook.get().setAverageRating(book.getAverageRating());
                if (!book.getImageUrl().equals("")) globalBook.get().setImageUrl(book.getImageUrl());
                if (!book.getSmallImageUrl().equals("")) globalBook.get().setSmallImageUrl(book.getSmallImageUrl());
            }
        }
    }

    public void inserThirdSource(){
//        bookRepo.saveAll(
        List<EGlobalBook> books =
                StreamSupport.stream(thirdRepo.findAll().spliterator(), false)
                        .map(this::thirdBookToEGlobalBook)
                        .collect(Collectors.toList()
        );
        for (EGlobalBook book : books) {
            Optional<EGlobalBook> globalBook = bookRepo.findByIsbn13(book.getIsbn13());
            if (!globalBook.isPresent()){
                bookRepo.save(book);
            }
            else {
                //TODO
            }
        }
    }

    public EGlobalBook firstBookToEGlobalBook(EBookFirst firstBook) {
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<>();

        String authorList = firstBook.getAuthor();

        // filter out role inside of parathesese
        String[] authors = authorList.split(",");
        for (String author : authors) {
            if (author.contains("(") && author.contains(")")) {
                int indexOpen = author.indexOf("(");

                author = author.substring(0, indexOpen);
            }
            author = author.trim();

            authorSet.add(authorService.saveAuthor(author));
        }

        return new EGlobalBook(firstBook.getIsbn(), null, null, firstBook.getEuro_price(), firstBook.getDiscount_euro(),
                firstBook.getType(), firstBook.getLinkBookPage(), firstBook.getName(),
                firstBook.getSubtitle(), null, firstBook.getEdition(), firstBook.getPublisher(),
                firstBook.getPublished_country(), firstBook.getLanguage(), null, firstBook.getHeight(),
                firstBook.getWidth(), firstBook.getSpine(), firstBook.getWeight(), firstBook.getShortBlurb(),
                firstBook.getLongBlurb(),
                firstBook.getBlurbReview(), null, null, null, null, null, null,
                new ArrayList<>(), new ArrayList<>(), authorSet, "blackwell");
    }

    public EGlobalBook secondBookToEGlobalBook(EBookSecond secondBook) {
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<EGlobalAuthor>();

        String authorList = secondBook.getAuthors();
        String authors[] = authorList.split(",");

        for (String author : authors) {
            authorSet.add(authorService.saveAuthor(author.trim()));
        }

        int year = Integer.parseInt(secondBook.getOriginalPublicationYear());

        return new EGlobalBook(secondBook.getIsbn13(), secondBook.getIsbn(), year, null,
                null, null, null, secondBook.getTitle(),
                null, secondBook.getOriginalTitle(), null,
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                secondBook.getAverageRating(), secondBook.getImageUrl(), secondBook.getSmallImageUrl(), null,
                null, null, new ArrayList<>(), new ArrayList<>(), authorSet, "source2");
    }

    public EGlobalBook thirdBookToEGlobalBook(EBookThird thirdBook) {
        // Save author list
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<EGlobalAuthor>();
        String authorList = thirdBook.getAuthor();
        String authors[] = authorList.split(",");

        for (String author : authors) {
            authorSet.add(authorService.saveAuthor(author.trim()));
        }

        // Save genre list
        ArrayList<EGlobalGenre> genreSet = new ArrayList<EGlobalGenre>();
        String genreList = thirdBook.getAuthor();
        String genres[] = genreList.split(",");

        for (String genre : genres) {
            genreSet.add(genreService.saveGenre(genre.trim()));
        }

        // Save character list
        ArrayList<EGlobalCharacter> characterSet = new ArrayList<EGlobalCharacter>();
        String characterList = thirdBook.getAuthor();
        String characters[] = characterList.split(",");

        for (String character : characters) {
            characterSet.add(characterService.saveCharacter(character.trim()));
        }


        // last for characters of the string are year
        String date = thirdBook.getFirstPublishDate();
        int year = -1;
        try {
            year = Integer.parseInt(date.substring(date.length() - 4));
        } catch (Exception e) {
            // TODO parser for dates like "Sep-96" "Mar-01"
        }

        return new EGlobalBook(thirdBook.getIsbn13(), thirdBook.getIsbn(), year, null,
                null, null, null, thirdBook.getTitle(),
                null, null, null,
                null, null, thirdBook.getLanguage(), null,
                null, null, null, null,
                null, thirdBook.getDescription(), null,
                thirdBook.getAvgRating(), null, null, thirdBook.getSeries(),
                thirdBook.getPlaces(), thirdBook.getAwards(), new ArrayList<>(), new ArrayList<>(), authorSet, "source3");
    }

}
