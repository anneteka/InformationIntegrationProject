package main.service;

import main.database.entity.global.*;
import main.database.entity.source.EBookFirst;
import main.database.entity.source.EBookSecond;
import main.database.entity.source.EBookThird;
import main.database.repository.source.FirstRepository;
import main.database.repository.global.GBookRepository;
import main.database.repository.source.SecondRepository;
import main.database.repository.source.ThirdRepository;
import main.util.MergeHelperUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class GBookDuplicateService {
    private final GBookRepository bookRepo;
    private final FirstRepository firstRepo;
    private final SecondRepository secondRepo;
    private final ThirdRepository thirdRepo;
    private final GAuthorService authorService;
    private final GGenreService genreService;
    private final GCharacterService characterService;
    private final GBookService bookService;
    private final GPlaceService placeService;
    private static final Logger LOG = LogManager.getFormatterLogger("GBookDuplicateService");

    @Autowired
    public GBookDuplicateService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo,
                                 GBookRepository bookRepo, GAuthorService authorService,
                                 GGenreService genreService, GCharacterService characterService,
                                 GBookService bookService, GPlaceService placeService) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.genreService = genreService;
        this.characterService = characterService;
        this.bookService = bookService;
        this.placeService = placeService;
    }

    @Transactional
    public void setUpGlobalSchema() {
        List<EGlobalBook> firstSource =
                firstRepo.findAll().stream()
                        .map(this::firstBookToEGlobalBook).toList();
        List<EGlobalBook> secondSource =
                secondRepo.findAll().stream()
                        .map(this::secondBookToEGlobalBook).toList();
        List<EGlobalBook> thirdSource =
                thirdRepo.findAll().stream()
                        .map(this::thirdBookToEGlobalBook).toList();

        ArrayList<EGlobalBook> allBooks = new ArrayList<>();
        allBooks.addAll(firstSource);
        allBooks.addAll(secondSource);
        allBooks.addAll(thirdSource);
        List<EGlobalBook> uniqueBooks = mergeBookDuplicates(allBooks);

        for (EGlobalBook book: uniqueBooks){
            mergeBookAuthorDuplicates(book);
            mergeBookCharacterDuplicates(book);
            mergeBookGenreDuplicates(book);
            mergeBookPlacesDuplicates(book);
        }

        bookRepo.saveAll(uniqueBooks);
        LOG.info("finished merging duplicates");

        System.out.println("done");
        LOG.info("DONE");

    }

    private void cleanEmptyBooks() {
        bookRepo.deleteAllByTitle("");
        bookRepo.deleteAllByTitle(null);
    }

    private List<String> constructIsbnKeys(EGlobalBook book) {
        ArrayList<String> keys = new ArrayList<>();
        keys.add(book.getIsbn13());
        keys.add(book.getIsbn10());
        keys.remove(null);
        keys.remove("");
        return keys.stream().toList();
    }

    public List<EGlobalBook> mergeBookDuplicates(List<EGlobalBook> allBooks) {
        // keys by title, original title, title+subtitle, original title + subtitle
        // values by book id
        //merge ones with the same key
        Map<String, EGlobalBook> allBookKeys = new HashMap<>();
        ArrayList<EGlobalBook> bookListCopy = new ArrayList<>(allBooks);
        int counter = allBooks.size();
        for (EGlobalBook book : allBooks) {
            counter--;
            List<String> keys = constructKeys(book);
            for (String key : keys) {
                if (isNullOrEmpty(book.getTitle())) {
                    bookListCopy.remove(book);
                    continue;
                }
                if (isNullOrEmpty(key)) {
                    continue;
                }
                if (allBookKeys.containsKey(key)) {
                    EGlobalBook keyBook = allBookKeys.get(key);
                    LOG.info("merging key" + key + "\n" + allBookKeys.get(key) + "\n" + book.toString() + "\n");
                    if (bookListCopy.contains(keyBook)){
                        mergeBooksAlt(keyBook, book);
                    }
                    bookListCopy.remove(book);
                } else {
                    if (bookListCopy.contains(book))
                        allBookKeys.put(key, book);
                }
            }

        }
        return bookListCopy;
    }

//    private void mergeIteration(List<EGlobalBook> bookListCopy,
//                                Map<String, EGlobalBook> allBookKeys, EGlobalBook book, String key) {
//        if (allBookKeys.containsKey(key)) {
//            EGlobalBook keyBook = allBookKeys.get(key);
//            LOG.info("merging key" + key + "\n" + allBookKeys.get(key) + "\n" + book.toString() + "\n");
//            if (bookListCopy.contains(keyBook)) mergeBooksAlt(keyBook, book);
//            bookListCopy.remove(book);
//        } else {
//            if (bookListCopy.contains(book))
//                allBookKeys.put(key, book);
//        }
//    }

    private List<String> constructKeys(EGlobalBook book) {
        HashSet<String> keys = new HashSet<>();
        keys.add(stringToKey(book.getTitle() == null ? "" : book.getTitle()));
        keys.add(stringToKey(book.getOriginalTitle() == null ? "" : book.getOriginalTitle()));
        keys.add(stringToKey(book.getTitle() == null ? "" : book.getTitle() + (book.getSubtitle() == null ? "" : book.getSubtitle())));
        keys.add(stringToKey(book.getOriginalTitle() == null ? "" : book.getOriginalTitle() + (book.getSubtitle() == null ? "" : book.getSubtitle())));
        keys.add(book.getIsbn10());
        keys.add(book.getIsbn13());
        keys.remove("");
        keys.remove(null);
        return keys.stream().toList();
    }

    private String stringToKey(String line) {
        // "Book title 12 subtitle" -> "BKTTL12SBTTL"
        // "1984" -> "1984"
        if (isNullOrEmpty(line) || line.equals("null")) return "";
        return line.toUpperCase().replaceAll("[^QWRTPSDFGHJKLZXCVBNM0-9]", "");
    }

    public void mergeBookAuthorDuplicates(EGlobalBook book) {
        ArrayList<String> copyAuthors = new ArrayList<>(book.getAuthors());
        for (String author1 : copyAuthors) {
            for (String author2 : copyAuthors) {
                if (book.getAuthors().contains(author1) && book.getAuthors().contains(author2)) {
                    if (isPersonDuplicate(author1, author2)) {
                        book.getAuthors().remove(author2);
                    }
                }
            }
        }
    }

    public void mergeBookCharacterDuplicates(EGlobalBook book) {
        ArrayList<String> copyCharacters = new ArrayList<>(book.getCharacters());
        for (String character1 : copyCharacters) {
            for (String character2 : copyCharacters) {
                if (book.getCharacters().contains(character1) && book.getCharacters().contains(character2)) {
                    if (isPersonDuplicate(character1, character2)) {
                        book.getCharacters().remove(character2);
                    }
                }
            }
        }
    }

    public void mergeBookGenreDuplicates(EGlobalBook book) {
        ArrayList<String> copyGenres = new ArrayList<>(book.getGenres());
        for (String genre1 : copyGenres) {
            for (String genre2 : copyGenres) {
                if (book.getGenres().contains(genre1) && book.getGenres().contains(genre2)) {
                    if (isExactDuplicate(genre1, genre2)) {
                        book.getGenres().remove(genre2);
                    }
                }
            }
        }
    }

    public void mergeBookPlacesDuplicates(EGlobalBook book) {
        ArrayList<String> copyPlaces = new ArrayList<>(book.getPlaces());
        for (String place1 : copyPlaces) {
            for (String place2 : copyPlaces) {
                if (book.getPlaces().contains(place1) && book.getPlaces().contains(place2)) {
                    if (isExactDuplicate(place1, place2)) {
                        book.getPlaces().remove(place2);
                    }
                }
            }
        }
    }

    public boolean isPersonDuplicate(String author1, String author2) {
        String[] names1 = author1.toLowerCase().split(" ");
        String[] names2 = author2.toLowerCase().split(" ");
        int minMatches = Math.min(Math.min(names1.length, names2.length), 2); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getPersonNameMatchesCount(names1, names2);

        return matches >= minMatches;
    }

    public boolean isExactDuplicate(String string1, String string2) {
        String[] names1 = string1.toLowerCase().split(" ");
        String[] names2 = string2.toLowerCase().split(" ");
        int minMatches = Math.min(names1.length, names2.length); // every part of the string must have a match

        int matches = MergeHelperUtil.getExactMatchesCount(names1, names2);

        return matches >= minMatches;
    }
    


    public void insertBook(EGlobalBook book) {
        Optional<EGlobalBook> globalBook13 = bookService.findByIsbn13(book.getIsbn13());
        Optional<EGlobalBook> globalBook10 = bookService.findByIsbn10(book.getIsbn13());
        if (!globalBook13.isPresent() && !globalBook10.isPresent()) {
            bookRepo.save(book);
        } else if (!globalBook13.isPresent()) {
            LOG.info("merging by isbn10\n" + globalBook10.get().toString() + "\n" + book.toString() + "\n");
            mergeBooks(globalBook10.get(), book);
        } else if (!globalBook10.isPresent()) {
            LOG.info("merging by isbn13\n" + globalBook13.get().toString() + "\n" + book.toString() + "\n");
            mergeBooks(globalBook13.get(), book);
        } else {
            LOG.info("merging by isbn13\n" + globalBook13.get().toString() + "\n" + book.toString());
            mergeBooks(globalBook13.get(), book);
            LOG.info("merging by isbn10\n" + globalBook13.get().toString() + "\n" + globalBook13.get().toString() + "\n");
            mergeBooks(globalBook13.get(), globalBook10.get());
        }

    }

    private void mergeBooksAlt(EGlobalBook original, EGlobalBook copy) {
        if (!isNullOrEmpty(copy.getIsbn13()) && isNullOrEmpty(original.getIsbn13())) original.setIsbn13(copy.getIsbn13());
        if (!isNullOrEmpty(copy.getIsbn10()) && isNullOrEmpty(original.getIsbn10())) original.setIsbn10(copy.getIsbn10());
        if (!isNullOrEmpty(copy.getEuroPrice()) && isNullOrEmpty(original.getEuroPrice())) original.setEuroPrice(copy.getEuroPrice());
        if (!isNullOrEmpty(copy.getEuroDiscount()) && isNullOrEmpty(original.getEuroDiscount())) original.setEuroDiscount(copy.getEuroDiscount());
        if (!isNullOrEmpty(copy.getType()) && isNullOrEmpty(original.getType())) original.setType(copy.getType());
        if (!isNullOrEmpty(copy.getLinkBookPage()) && isNullOrEmpty(original.getLinkBookPage())) original.setLinkBookPage(copy.getLinkBookPage());
        if (!isNullOrEmpty(copy.getTitle()) && isNullOrEmpty(original.getTitle())) original.setTitle(copy.getTitle());
        if (!isNullOrEmpty(copy.getSubtitle()) && isNullOrEmpty(original.getSubtitle())) original.setSubtitle(copy.getSubtitle());
        if (!isNullOrEmpty(copy.getPublisher()) && isNullOrEmpty(original.getPublisher())) original.setPublisher(copy.getPublisher());
        if (!isNullOrEmpty(copy.getPublishedCountry()) && isNullOrEmpty(original.getPublishedCountry())) original.setPublishedCountry(copy.getPublishedCountry());
        if (!isNullOrEmpty(copy.getLanguage()) && isNullOrEmpty(original.getLanguage())) original.setLanguage(copy.getLanguage());
        if (!isNullOrEmpty(copy.getHeight()) && isNullOrEmpty(original.getHeight())) original.setHeight(copy.getHeight());
        if (!isNullOrEmpty(copy.getWidth()) && isNullOrEmpty(original.getWidth())) original.setWidth(copy.getWidth());
        if (!isNullOrEmpty(copy.getSpine()) && isNullOrEmpty(original.getSpine())) original.setSpine(copy.getSpine());
        if (!isNullOrEmpty(copy.getWeight()) && isNullOrEmpty(original.getWeight())) original.setWeight(copy.getWeight());
        if (!isNullOrEmpty(copy.getShortDescription()) && isNullOrEmpty(original.getShortDescription())) original.setShortDescription(copy.getShortDescription());
        if (!isNullOrEmpty(copy.getLongDescription()) && isNullOrEmpty(original.getLongDescription())) original.setLongDescription(copy.getLongDescription());
        if (!isNullOrEmpty(copy.getReview()) && isNullOrEmpty(original.getReview())) original.setReview(copy.getReview());
        if (!isNullOrEmpty(copy.getPublication_date()) && isNullOrEmpty(original.getPublication_date())) original.setPublication_date(copy.getPublication_date());
        if (!isNullOrEmpty(copy.getOriginalTitle()) && isNullOrEmpty(original.getOriginalTitle())) original.setOriginalTitle(copy.getOriginalTitle());
        if (!isNullOrEmpty(copy.getAverageRating()) && isNullOrEmpty(original.getAverageRating())) original.setAverageRating(copy.getAverageRating());
        if (!isNullOrEmpty(copy.getImageUrl()) && isNullOrEmpty(original.getImageUrl())) original.setImageUrl(copy.getImageUrl());
        if (!isNullOrEmpty(copy.getSmallImageUrl()) && isNullOrEmpty(original.getSmallImageUrl())) original.setSmallImageUrl(copy.getSmallImageUrl());
        if (!isNullOrEmpty(original.getSeries()) && isNullOrEmpty(original.getSeries())) {
            original.setSeries(copy.getSeries());
        }

        original.getPlaces().addAll(copy.getPlaces());
        original.getCharacters().addAll(copy.getCharacters());
        original.getAuthors().addAll(copy.getAuthors());
        original.getGenres().addAll(copy.getGenres());
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
        try {
            bookRepo.save(original);
            bookRepo.delete(copy);
//            for (EGlobalPlace place : copy.getPlaces()) {
//                bookService.addPlace(original, place);
//            }
//            for (EGlobalAuthor author : copy.getAuthors()) {
//                bookService.addAuthor(original, author);
//            }
//            for (EGlobalGenre genre : original.getGenres()) {
//                bookService.addGenre(original, genre);
//            }
//            for (EGlobalCharacter character : original.getCharacters()) {
//                bookService.addCharacter(original, character);
//            }
        } catch (Exception e) {

        }
    }


    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public EGlobalBook firstBookToEGlobalBook(EBookFirst firstBook) {
        HashSet<String> authorSet = new HashSet<>();

        String authorList = firstBook.getAuthor();

        // filter out role inside of parathesese
        String[] authors = authorList.split(",");
        for (String author : authors) {
            if (author.contains("(") && author.contains(")")) {
                int indexOpen = author.indexOf("(");

                author = author.substring(0, indexOpen);
            }

            authorSet.add(author.trim());
        }

        return new EGlobalBook(firstBook.getIsbn(), null, null, firstBook.getEuro_price(), firstBook.getDiscount_euro(),
                firstBook.getType(), firstBook.getLinkBookPage(), firstBook.getName(),
                firstBook.getSubtitle(), null, firstBook.getEdition(), firstBook.getPublisher(),
                firstBook.getPublished_country(), firstBook.getLanguage(), null, firstBook.getHeight(),
                firstBook.getWidth(), firstBook.getSpine(), firstBook.getWeight(), firstBook.getShortBlurb(),
                firstBook.getLongBlurb(),
                firstBook.getBlurbReview(), null, null, null, null, null,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(authorSet), "blackwell");
    }

    public EGlobalBook secondBookToEGlobalBook(EBookSecond secondBook) {
        HashSet<String> authorSet = new HashSet<>();

        String authorList = secondBook.getAuthors();
        String authors[] = authorList.split(",");
        String isbn13 = "";
        if (secondBook.getIsbn13().length() == 13) {
            isbn13 = secondBook.getIsbn13();
        }
        if (secondBook.getIsbn13().length() > 14) {
            isbn13 = secondBook.getIsbn13().charAt(0) + secondBook.getIsbn13().substring(2, 14);
        }
        for (String author : authors) {
            authorSet.add(author.trim());
        }

        return new EGlobalBook(isbn13, secondBook.getIsbn(), secondBook.getOriginalPublicationYear(), null,
                null, null, null, secondBook.getTitle(),
                null, secondBook.getOriginalTitle(), null,
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                secondBook.getAverageRating(), secondBook.getImageUrl(), secondBook.getSmallImageUrl(), null,
                null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(authorSet), "source2");
    }

    public EGlobalBook thirdBookToEGlobalBook(EBookThird thirdBook) {
        // Save author list
        HashSet<String> authorSet = new HashSet<>();
        String authorList = thirdBook.getAuthor();
        String authors[] = authorList.split(",");

        for (String author : authors) {
            authorSet.add(author.trim());
        }

        // Save genre list
        HashSet<String> genreSet = new HashSet<>();
        String genreList = thirdBook.getAuthor();
        String genres[] = genreList.split(",");

        for (String genre : genres) {
            genreSet.add(genre.trim());
        }

        // Save character list
        HashSet<String> characterSet = new HashSet<>();
        String characterList = thirdBook.getAuthor();
        String[] characters = characterList.split(",");

        for (String character : characters) {
            characterSet.add(character.trim());
        }

        HashSet<String> placeSet = new HashSet<>();
        String placeList = thirdBook.getPlaces();
        String[] places = placeList.split(",");
        for (String place : places) {
            placeSet.add(place.trim());
        }

        // last 4 characters of the string are year
        String date = thirdBook.getFirstPublishDate();
        int year = -1;
        try {
            year = Integer.parseInt(date.substring(date.length() - 4));
        } catch (Exception e) {
            // TODO parser for dates like "Sep-96" "Mar-01"
        }

        return new EGlobalBook(thirdBook.getIsbn13(), thirdBook.getIsbn(), "" + year, null,
                null, null, null, thirdBook.getTitle(),
                null, null, null,
                null, null, thirdBook.getLanguage(), null,
                null, null, null, null,
                null, thirdBook.getDescription(), null,
                thirdBook.getAvgRating(), null, null, thirdBook.getSeries(),
                thirdBook.getAwards(), new ArrayList<>(placeSet), new ArrayList<>(characterSet),
                new ArrayList<>(genreSet), new ArrayList<>(authorSet), "source3");
    }

}
