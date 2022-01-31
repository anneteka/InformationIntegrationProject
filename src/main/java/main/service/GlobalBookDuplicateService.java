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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


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
    private final GlobalBookService bookService;

    @Autowired
    public GlobalBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo,
                                      GlobalAuthorRepository authorRepo, GlobalBookRepository bookRepo,
                                      GlobalCharacterRepository characterRepo, GlobalGenreRepository genreRepo,
                                      GAuthorService authorService, GGenreService genreService, GCharacterService characterService,
                                      GlobalBookService bookService) {
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
        this.bookService = bookService;
    }

    public void setUpGlobalSchema() {
        List<EGlobalBook> firstSource =
                StreamSupport.stream(firstRepo.findAll().spliterator(), false)
                        .map(this::firstBookToEGlobalBook)
                        .collect(Collectors.toList()
                        );
        List<EGlobalBook> secondSource =
                StreamSupport.stream(secondRepo.findAll().spliterator(), false)
                        .map(this::secondBookToEGlobalBook)
                        .collect(Collectors.toList()
                        );
        List<EGlobalBook> thirdSource =
                StreamSupport.stream(thirdRepo.findAll().spliterator(), false)
                        .map(this::thirdBookToEGlobalBook)
                        .collect(Collectors.toList()
                        );

        insertSource(firstSource);
        insertSource(secondSource);
        insertSource(thirdSource);

        mergeBookDuplicates();
    }

    public void mergeBookDuplicates() {
        // keys by title, original title, title+subtitle, original title + subtitle
        // values by book id
        //merge ones with the same key
        List<EGlobalBook> allBooks = (List<EGlobalBook>) bookRepo.findAll();
        Map<String, EGlobalBook> allBookKeys = new HashMap<>();
        for (EGlobalBook book : allBooks) {
            List<String> keys = constructKeys(book);
            for (String key : keys) {
                if (allBookKeys.containsKey(key)) {
                    mergeBooks(allBookKeys.get(key), book);
                } else {
                    allBookKeys.put(key, book);
                }
            }
        }
    }

    private List<String> constructKeys(EGlobalBook book) {
        ArrayList<String> keys = new ArrayList<>();
        keys.add(stringToKey(book.getTitle()));
        keys.add(stringToKey(book.getOriginalTitle()));
        keys.add(stringToKey(book.getTitle() + book.getSubtitle()));
        keys.add(stringToKey(book.getOriginalTitle() + book.getSubtitle()));
        return keys;
    }

    private String stringToKey(String line) {
        // "Book title 12 subtitle" -> "BKTTL12SBTTL"
        // "1984" -> "1984"
        return line.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    public void mergeAuthorDuplicates() {

    }

    public void mergeCharacterDuplicates() {

    }

    public void mergePlaceDuplicates() {
        // ???
    }

    public void insertSource(List<EGlobalBook> books) {
        for (EGlobalBook book : books) {
            Optional<EGlobalBook> globalBook13 = bookRepo.findByIsbn13(book.getIsbn13());
            EGlobalBook globalBook10 = bookRepo.findByIsbn10(book.getIsbn13()).orElse(null);
            EGlobalBook globalBook = globalBook13.orElse(globalBook10);
            if (globalBook == null) {
                bookRepo.save(book);
            } else {
                mergeBooks(globalBook, book);
            }
        }
    }

    //merges copy into the original
    private void mergeBooks(EGlobalBook original, EGlobalBook copy) {
        if (isNullOrEmpty(copy.getIsbn13())) original.setIsbn13(copy.getIsbn13());
        if (isNullOrEmpty(copy.getIsbn10())) original.setIsbn10(copy.getIsbn10());
        if (copy.getEuroPrice() == null) original.setEuroPrice(copy.getEuroPrice());
        if (copy.getEuroDiscount() == null) original.setEuroDiscount(copy.getEuroDiscount());
        if (isNullOrEmpty(copy.getType())) original.setType(copy.getType());
        if (isNullOrEmpty(copy.getLinkBookPage())) original.setLinkBookPage(copy.getLinkBookPage());
        if (isNullOrEmpty(copy.getTitle())) original.setTitle(copy.getTitle());
        if (isNullOrEmpty(copy.getSubtitle())) original.setSubtitle(copy.getSubtitle());
        if (isNullOrEmpty(copy.getPublisher())) original.setPublisher(copy.getPublisher());
        if (isNullOrEmpty(copy.getPublishedCountry())) original.setPublishedCountry(copy.getPublishedCountry());
        if (isNullOrEmpty(copy.getLanguage())) original.setLanguage(copy.getLanguage());
        if (copy.getHeight() == null) original.setHeight(copy.getHeight());
        if (copy.getWidth() == null) original.setWidth(copy.getWidth());
        if (copy.getSpine() == null) original.setSpine(copy.getSpine());
        if (copy.getWeight() == null) original.setWeight(copy.getWeight());
        if (isNullOrEmpty(copy.getShortDescription())) original.setShortDescription(copy.getShortDescription());
        if (isNullOrEmpty(copy.getLongDescription())) original.setLongDescription(copy.getLongDescription());
        if (isNullOrEmpty(copy.getReview())) original.setReview(copy.getReview());
        if (copy.getPublication_date() == null) original.setPublication_date(copy.getPublication_date());
        if (isNullOrEmpty(copy.getOriginalTitle())) original.setOriginalTitle(copy.getOriginalTitle());
        if (copy.getAverageRating() == null) original.setAverageRating(copy.getAverageRating());
        if (isNullOrEmpty(copy.getImageUrl())) original.setImageUrl(copy.getImageUrl());
        if (isNullOrEmpty(copy.getSmallImageUrl())) original.setSmallImageUrl(copy.getSmallImageUrl());
        if (isNullOrEmpty(original.getSeries())) {
            original.setSeries(copy.getSeries());
        }


        // todo check places for dublicates maybe create a table?
        if (isNullOrEmpty(original.getPlaces())) {
            original.setPlaces(copy.getPlaces());
        } else {
            original.setPlaces(original.getPlaces() + ", " + copy.getPlaces());
        }

        bookRepo.save(original);
        for (EGlobalAuthor author : copy.getAuthors()) {
            bookService.addAuthor(original, author);
        }
        for (EGlobalGenre genre : original.getGenres()) {
            bookService.addGenre(original, genre);
        }
        for (EGlobalCharacter character : original.getCharacters()) {
            bookService.addCharacter(original, character);
        }
    }


    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
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
