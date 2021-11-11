package main.util.cvs.model;


import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import main.database.entity.EBookThird;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvThirdBook extends CsvBook {
    @CsvBindByPosition(position = 0)
    public int row;
    @CsvBindByPosition(position = 1)
    public int id;
    @CsvBindByPosition(position = 2)
    public int bookID;
    @CsvBindByPosition(position = 3)
    public int bestBookID;
    @CsvBindByPosition(position = 4)
    public int workID;
    @CsvBindByPosition(position = 5)
    public int booksCount;
    @CsvBindByPosition(position = 6)
    public String isbn;
    @CsvBindByPosition(position = 7)
    public String isbn13;
    @CsvBindByPosition(position = 8)
    public String authors;
    @CsvBindByPosition(position = 9)
    public String originalPublicationYear;
    @CsvBindByPosition(position = 10)
    public String originalTitle;
    @CsvBindByPosition(position = 11)
    public String title;
    @CsvBindByPosition(position = 12)
    public String languageCode;
    @CsvBindByPosition(position = 13)
    public double averageRating;
    @CsvBindByPosition(position = 14)
    public int ratingsCount;
    @CsvBindByPosition(position = 15)
    public int workRatingsCount;
    @CsvBindByPosition(position = 16)
    public int workTextReviewsCount;
    @CsvBindByPosition(position = 17)
    public int ratings1;
    @CsvBindByPosition(position = 18)
    public int ratings2;
    @CsvBindByPosition(position = 19)
    public int ratings3;
    @CsvBindByPosition(position = 20)
    public int ratings4;
    @CsvBindByPosition(position = 21)
    public int ratings5;
    @CsvBindByPosition(position = 22)
    public String imageUrl;
    @CsvBindByPosition(position = 23)
    public String smallImageUrl;
    @CsvBindByPosition(position = 24)
    public int goodreadsBookID;
    @CsvBindByPosition(position = 25)
    public String tagName;
    @CsvBindByPosition(position = 26)
    public String description;

    @Override
    public EBookThird toEBook(){
        return new EBookThird(
        bookID, bestBookID, workID, booksCount,
        isbn, isbn13, authors, originalPublicationYear, originalTitle,
        title, languageCode, averageRating, ratingsCount, workRatingsCount,
        workTextReviewsCount, ratings1, ratings2, ratings3, ratings4, ratings5,
        imageUrl, smallImageUrl, goodreadsBookID, tagName, description
        );
    }
}
