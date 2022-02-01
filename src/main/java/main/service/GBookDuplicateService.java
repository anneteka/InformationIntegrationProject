package main.service;

import main.database.entity.global.*;
import main.database.entity.source.EBookFirst;
import main.database.entity.source.EBookSecond;
import main.database.entity.source.EBookThird;
import main.database.repository.source.FirstRepository;
import main.database.repository.global.GBookRepository;
import main.database.repository.source.SecondRepository;
import main.database.repository.source.ThirdRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


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

        
        //mergeBookDuplicates();
        //cleanEmptyBooks();
        mergeAuthorDuplicates();
        mergeCharacterDuplicates();
        mergeGenreDuplicates();
        mergePlaceDuplicates();
        System.out.println("done");
    }

    private void cleanEmptyBooks() {
        bookRepo.deleteAllByTitle("");
        bookRepo.deleteAllByTitle(null);
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
                    //LOG.info("merging key" + key.toString()+"\n"+allBookKeys.get(key)+"\n"+book.toString()+"\n");
                    mergeBooks(allBookKeys.get(key), book);
                } else {
                    allBookKeys.put(key, book);
                }
            }
        }
    }

    private List<String> constructKeys(EGlobalBook book) {
        HashSet<String> keys = new HashSet<>();
        keys.add(stringToKey(book.getTitle()==null?"":book.getTitle()));
        keys.add(stringToKey(book.getOriginalTitle()==null?"":book.getOriginalTitle()));
        keys.add(stringToKey(book.getTitle()==null?"":book.getTitle() + (book.getSubtitle()==null?"":book.getSubtitle())));
        keys.add(stringToKey(book.getOriginalTitle()==null?"":book.getOriginalTitle() + (book.getSubtitle()==null?"":book.getSubtitle())));
        keys.remove("");
        return keys.stream().toList();
    }

    private String stringToKey(String line) {
        // "Book title 12 subtitle" -> "BKTTL12SBTTL"
        // "1984" -> "1984"
        if (isNullOrEmpty(line)||line.equals("null")) return "";
        return line.toUpperCase().replaceAll("[^QWRTPSDFGHJKLZXCVBNM0-9]", "");
    }

    public void mergeAuthorDuplicates() {
        System.out.println("Merging authors");
        List<EGlobalAuthor> allAuthors = authorService.findAll();
        // check all author combinations here?
        int l = allAuthors.size();
        for (int i = 0; i < l; i++){
            var keep = allAuthors.get(i);
            for (int j = i+1; j<l; j++){
                var discard = allAuthors.get(j);
                if (!keep.equals(discard)){
                    authorService.mergeAuthors(keep, discard);
                }
            }
        }
    }

    public void mergeCharacterDuplicates() {
        System.out.println("Merging characters");
        List<EGlobalCharacter> allCharacters = characterService.findAll();
        // check all author combinations here?
        int l = allCharacters.size();
        for (int i = 0; i < l; i++){
            var keep = allCharacters.get(i);
            for (int j = i+1; j<l; j++){
                var discard = allCharacters.get(j);
                if (!keep.equals(discard)){
                    characterService.mergeCharacters(keep, discard);
                }
            }
        }
    }

    public void mergeGenreDuplicates() {
        System.out.println("Merging genres");
        List<EGlobalGenre> allGenres = genreService.findAll();
        // check all author combinations here?
        int l = allGenres.size();
        for (int i = 0; i < l; i++){
            var keep = allGenres.get(i);
            for (int j = i+1; j<l; j++){
                var discard = allGenres.get(j);
                if (!keep.equals(discard)){
                    genreService.mergeGenres(keep, discard);
                }
            }
        }
    }

    public void mergePlaceDuplicates() {
        System.out.println("Merging places");
        List<EGlobalPlace> allPlaces = placeService.findAll();
        // check all author combinations here?
        int l = allPlaces.size();
        for (int i = 0; i < l; i++){
            var keep = allPlaces.get(i);
            for (int j = i+1; j<l; j++){
                var discard = allPlaces.get(j);
                if (!keep.equals(discard)){
                    placeService.mergePlaces(keep, discard);
                }
            }
        }
    }

    public void insertSource(List<EGlobalBook> books) {
        for (EGlobalBook book : books) {
            Optional<EGlobalBook> globalBook13 = bookService.findByIsbn13(book.getIsbn13());
            Optional<EGlobalBook> globalBook10 = bookService.findByIsbn10(book.getIsbn13());
            if (!globalBook13.isPresent() && !globalBook10.isPresent()) {
                bookRepo.save(book);
            } else if (!globalBook13.isPresent()){
                //LOG.info("merging by isbn10\n" + globalBook10.get().toString()+"\n"+book.toString()+"\n");
                mergeBooks(globalBook10.get(), book);
            } else if (!globalBook10.isPresent()){
                //LOG.info("merging by isbn13\n" + globalBook13.get().toString()+"\n"+book.toString()+"\n");
                mergeBooks(globalBook13.get(), book);
            } else {
                //LOG.info("merging by isbn13\n" + globalBook13.get().toString()+"\n"+book.toString());
                mergeBooks(globalBook13.get(),book);
                //LOG.info("merging by isbn10\n" + globalBook13.get().toString()+"\n"+globalBook13.get().toString()+"\n");
                mergeBooks(globalBook13.get(), globalBook10.get());
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
        try {
            bookRepo.save(original);
            bookRepo.delete(copy);
            for (EGlobalPlace place : copy.getPlaces()) {
                bookService.addPlace(original, place);
            }
            for (EGlobalAuthor author : copy.getAuthors()) {
                bookService.addAuthor(original, author);
            }
            for (EGlobalGenre genre : original.getGenres()) {
                bookService.addGenre(original, genre);
            }
            for (EGlobalCharacter character : original.getCharacters()) {
                bookService.addCharacter(original, character);
            }
        } catch (Exception e){

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
                firstBook.getBlurbReview(), null, null, null, null, null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), authorSet, "blackwell");
    }

    public EGlobalBook secondBookToEGlobalBook(EBookSecond secondBook) {
        ArrayList<EGlobalAuthor> authorSet = new ArrayList<>();

        String authorList = secondBook.getAuthors();
        String authors[] = authorList.split(",");

        for (String author : authors) {
            authorSet.add(authorService.saveAuthor(author.trim()));
        }

        return new EGlobalBook(secondBook.getIsbn13(), secondBook.getIsbn(), secondBook.getOriginalPublicationYear(), null,
                null, null, null, secondBook.getTitle(),
                null, secondBook.getOriginalTitle(), null,
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                secondBook.getAverageRating(), secondBook.getImageUrl(), secondBook.getSmallImageUrl(), null,
                null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), authorSet, "source2");
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
        ArrayList<EGlobalGenre> genreSet = new ArrayList<>();
        String genreList = thirdBook.getAuthor();
        String genres[] = genreList.split(",");

        for (String genre : genres) {
            genreSet.add(genreService.saveGenre(genre.trim()));
        }

        // Save character list
        ArrayList<EGlobalCharacter> characterSet = new ArrayList<>();
        String characterList = thirdBook.getAuthor();
        String[] characters = characterList.split(",");

        for (String character : characters) {
            characterSet.add(characterService.saveCharacter(character.trim()));
        }

        ArrayList<EGlobalPlace> placeSet = new ArrayList<>();
        String placeList = thirdBook.getPlaces();
        String[] places = placeList.split(",");
        for (String place: places){
            placeSet.add(placeService.savePlace(place.trim()));
        }

        // last 4 characters of the string are year
        String date = thirdBook.getFirstPublishDate();
        int year = -1;
        try {
            year = Integer.parseInt(date.substring(date.length() - 4));
        } catch (Exception e) {
            // TODO parser for dates like "Sep-96" "Mar-01"
        }

        return new EGlobalBook(thirdBook.getIsbn13(), thirdBook.getIsbn(), ""+year, null,
                null, null, null, thirdBook.getTitle(),
                null, null, null,
                null, null, thirdBook.getLanguage(), null,
                null, null, null, null,
                null, thirdBook.getDescription(), null,
                thirdBook.getAvgRating(), null, null, thirdBook.getSeries(),
                thirdBook.getAwards(), new ArrayList<>(), characterSet, genreSet, authorSet, "source3");
    }

}
