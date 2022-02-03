package main.service;

import main.database.entity.global.*;
import main.database.repository.source.FirstRepository;
import main.database.repository.global.GBookRepository;
import main.database.repository.source.SecondRepository;
import main.database.repository.source.ThirdRepository;
import main.util.BookConverterUtil;
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
    private final GBookService bookService;
    private static final Logger LOG = LogManager.getFormatterLogger("GBookDuplicateService");

    @Autowired
    public GBookDuplicateService(
            FirstRepository firstRepo,
            SecondRepository secondRepo, ThirdRepository thirdRepo,
            GBookRepository bookRepo, GBookService bookService
    ) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
        this.bookRepo = bookRepo;
        this.bookService = bookService;
    }

    @Transactional
    public void setUpGlobalSchema() {
        List<EGlobalBook> firstSource =
                firstRepo.findAll().stream()
                        .map(BookConverterUtil::firstBookToEGlobalBook).toList();
        List<EGlobalBook> secondSource =
                secondRepo.findAll().stream()
                        .map(BookConverterUtil::secondBookToEGlobalBook).toList();
        List<EGlobalBook> thirdSource =
                thirdRepo.findAll().stream()
                        .map(BookConverterUtil::thirdBookToEGlobalBook).toList();

        ArrayList<EGlobalBook> allBooks = new ArrayList<>();
        allBooks.addAll(firstSource);
        allBooks.addAll(secondSource);
        allBooks.addAll(thirdSource);
        List<EGlobalBook> uniqueBooks = mergeBookDuplicates(allBooks);
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
                    if (bookListCopy.contains(keyBook)) {
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

    public void mergeAuthorDuplicates(EGlobalBook book) {
        System.out.println("Merging authors");
        // check all author combinations here?
        List<String> allAuthors = book.getAuthors();
        int l = allAuthors.size();
        for (int i = 0; i < l; i++) {
            var keep = allAuthors.get(i);
            for (int j = i + 1; j < l; j++) {
                var discard = allAuthors.get(j);
                if (!keep.equals(discard)) {
                    //mergeAuthors(book, keep, discard);
                }
            }
        }
    }

    public void mergeBookAuthorDuplicates(EGlobalBook book) {
        ArrayList<String> copyAuthors = new ArrayList<>(book.getAuthors());
        for (String author1 : copyAuthors) {
            for (String author2 : copyAuthors) {
                if (book.getAuthors().contains(author1) && book.getAuthors().contains(author2)) {
                    if (isAuthorDuplicate(author1, author2)) {
                        book.getAuthors().remove(author2);
                    }
                }
            }
        }

    }

    public boolean isAuthorDuplicate(String author1, String author2) {
        String[] names1 = author1.toLowerCase().split(" ");
        String[] names2 = author2.toLowerCase().split(" ");
        int minMatches = Math.min(Math.min(names1.length, names2.length), 2); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getPersonNameMatchesCount(names1, names2);

        return matches >= minMatches;
    }

    public void mergeCharacterDuplicates(EGlobalBook book) {
        System.out.println("Merging characters");
        List<String> allCharacters = book.getCharacters();
        // check all author combinations here?
        int l = allCharacters.size();
        for (int i = 0; i < l; i++) {
            var keep = allCharacters.get(i);
            for (int j = i + 1; j < l; j++) {
                var discard = allCharacters.get(j);
                if (!keep.equals(discard)) {
                    //mergeCharacters(book, keep, discard);
                }
            }
        }
    }

    public void mergeGenreDuplicates(EGlobalBook book) {
        System.out.println("Merging genres");
        List<String> allGenres = book.getGenres();
        // check all author combinations here?
        int l = allGenres.size();
        for (int i = 0; i < l; i++) {
            var keep = allGenres.get(i);
            for (int j = i + 1; j < l; j++) {
                var discard = allGenres.get(j);
                if (!keep.equals(discard)) {
//                    isGenreDuplicate(keep, discard);
                }
            }
        }
    }

    private void mergeBooksAlt(EGlobalBook original, EGlobalBook copy) {
        if (!isNullOrEmpty(copy.getIsbn13()) && isNullOrEmpty(original.getIsbn13()))
            original.setIsbn13(copy.getIsbn13());
        if (!isNullOrEmpty(copy.getIsbn10()) && isNullOrEmpty(original.getIsbn10()))
            original.setIsbn10(copy.getIsbn10());
        if (!isNullOrEmpty(copy.getEuroPrice()) && isNullOrEmpty(original.getEuroPrice()))
            original.setEuroPrice(copy.getEuroPrice());
        if (!isNullOrEmpty(copy.getEuroDiscount()) && isNullOrEmpty(original.getEuroDiscount()))
            original.setEuroDiscount(copy.getEuroDiscount());
        if (!isNullOrEmpty(copy.getType()) && isNullOrEmpty(original.getType())) original.setType(copy.getType());
        if (!isNullOrEmpty(copy.getLinkBookPage()) && isNullOrEmpty(original.getLinkBookPage()))
            original.setLinkBookPage(copy.getLinkBookPage());
        if (!isNullOrEmpty(copy.getTitle()) && isNullOrEmpty(original.getTitle())) original.setTitle(copy.getTitle());
        if (!isNullOrEmpty(copy.getSubtitle()) && isNullOrEmpty(original.getSubtitle()))
            original.setSubtitle(copy.getSubtitle());
        if (!isNullOrEmpty(copy.getPublisher()) && isNullOrEmpty(original.getPublisher()))
            original.setPublisher(copy.getPublisher());
        if (!isNullOrEmpty(copy.getPublishedCountry()) && isNullOrEmpty(original.getPublishedCountry()))
            original.setPublishedCountry(copy.getPublishedCountry());
        if (!isNullOrEmpty(copy.getLanguage()) && isNullOrEmpty(original.getLanguage()))
            original.setLanguage(copy.getLanguage());
        if (!isNullOrEmpty(copy.getHeight()) && isNullOrEmpty(original.getHeight()))
            original.setHeight(copy.getHeight());
        if (!isNullOrEmpty(copy.getWidth()) && isNullOrEmpty(original.getWidth())) original.setWidth(copy.getWidth());
        if (!isNullOrEmpty(copy.getSpine()) && isNullOrEmpty(original.getSpine())) original.setSpine(copy.getSpine());
        if (!isNullOrEmpty(copy.getWeight()) && isNullOrEmpty(original.getWeight()))
            original.setWeight(copy.getWeight());
        if (!isNullOrEmpty(copy.getShortDescription()) && isNullOrEmpty(original.getShortDescription()))
            original.setShortDescription(copy.getShortDescription());
        if (!isNullOrEmpty(copy.getLongDescription()) && isNullOrEmpty(original.getLongDescription()))
            original.setLongDescription(copy.getLongDescription());
        if (!isNullOrEmpty(copy.getReview()) && isNullOrEmpty(original.getReview()))
            original.setReview(copy.getReview());
        if (!isNullOrEmpty(copy.getPublication_date()) && isNullOrEmpty(original.getPublication_date()))
            original.setPublication_date(copy.getPublication_date());
        if (!isNullOrEmpty(copy.getOriginalTitle()) && isNullOrEmpty(original.getOriginalTitle()))
            original.setOriginalTitle(copy.getOriginalTitle());
        if (!isNullOrEmpty(copy.getAverageRating()) && isNullOrEmpty(original.getAverageRating()))
            original.setAverageRating(copy.getAverageRating());
        if (!isNullOrEmpty(copy.getImageUrl()) && isNullOrEmpty(original.getImageUrl()))
            original.setImageUrl(copy.getImageUrl());
        if (!isNullOrEmpty(copy.getSmallImageUrl()) && isNullOrEmpty(original.getSmallImageUrl()))
            original.setSmallImageUrl(copy.getSmallImageUrl());
        if (!isNullOrEmpty(original.getSeries()) && isNullOrEmpty(original.getSeries())) {
            original.setSeries(copy.getSeries());
        }

        original.getPlaces().addAll(copy.getPlaces());
        original.getCharacters().addAll(copy.getCharacters());
        original.getAuthors().addAll(copy.getAuthors());
        original.getGenres().addAll(copy.getGenres());
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    public boolean isCharacterDuplicate(String character1, String character2) {
        String[] names1 = character1.toLowerCase().split(" ");
        String[] names2 = character2.toLowerCase().split(" ");

        int minMatches = Math.min(Math.min(names1.length, names2.length), 2); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getPersonNameMatchesCount(names1, names2);

        return matches >= minMatches;
    }

    public boolean isGenreDuplicate(String genre1, String genre2) {
        String[] names1 = genre1.toLowerCase().split(" ");
        String[] names2 = genre2.toLowerCase().split(" ");

        int minMatches = Math.min(names1.length, names2.length); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getExactMatchesCount(names1, names2);

        return matches >= minMatches;
    }

    public boolean isPlaceDuplicate(String place1, String place2) {
        String[] names1 = place1.toLowerCase().split(" ");
        String[] names2 = place2.toLowerCase().split(" ");

        int minMatches = Math.min(names1.length, names2.length); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getExactMatchesCount(names1, names2);

        return matches >= minMatches;
    }
}

}
