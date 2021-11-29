package main.util.cvs.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import main.database.entity.EBookSecond;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvSecondBook extends CsvBook {
    @CsvBindByPosition(position = 0)
    public int bookID;
    @CsvBindByPosition(position = 1)
    public String title;
    @CsvBindByPosition(position = 2)
    public String authors;
    @CsvBindByPosition(position = 3)
    public double averageRating;
    @CsvBindByPosition(position = 4)
    public String isbn;
    @CsvBindByPosition(position = 5)
    public String isbn13;
    @CsvBindByPosition(position = 6)
    public String languageCode;
    @CsvBindByPosition(position = 7)
    public int numPages;
    @CsvBindByPosition(position = 8)
    public int ratingsCount;
    @CsvBindByPosition(position = 9)
    public int textReviewsCount;
    @CsvBindByPosition(position = 10)
    public String publicationDate;
    @CsvBindByPosition(position = 11)
    public String publisher;

}
