package main.util;

import main.database.entity.global.EGlobalBook;
import main.database.entity.source.EBookFirst;
import main.database.entity.source.EBookSecond;
import main.database.entity.source.EBookThird;

import java.util.ArrayList;
import java.util.HashSet;

public class BookConverterUtil {
    public static EGlobalBook firstBookToEGlobalBook(EBookFirst firstBook) {
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

    public static EGlobalBook secondBookToEGlobalBook(EBookSecond secondBook) {
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

    public static EGlobalBook thirdBookToEGlobalBook(EBookThird thirdBook) {
        // Save author list
        HashSet<String> authorSet = new HashSet<>();
        String authorList = thirdBook.getAuthor();
        String authors[] = authorList.split(",");

        for (String author : authors) {
            authorSet.add(author.trim());
        }

        // Save genre list
        HashSet<String> genreSet = new HashSet<>();
        String genreList = thirdBook.getGenres();
        String genres[] = genreList.split(",");

        for (String genre : genres) {
            genreSet.add(genre.trim());
        }

        // Save character list
        HashSet<String> characterSet = new HashSet<>();
        String characterList = thirdBook.getCharacters();
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
