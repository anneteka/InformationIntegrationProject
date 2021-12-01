package main.util.cvs.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import main.database.entity.EBook;
import main.database.entity.EBookSecond;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvSecondBook extends CsvBook {
    @CsvBindByPosition(position = 5)
    private String isbn;
    @CsvBindByPosition(position = 6)
    private String isbn13;
    @CsvBindByPosition(position = 7)
    private String authors;
    @CsvBindByPosition(position = 8)
    private String originalPublicationYear;
    @CsvBindByPosition(position = 9)
    private String originalTitle;
    @CsvBindByPosition(position = 10)
    private String title;
    @CsvBindByPosition(position = 12)
    private double averageRating;
    @CsvBindByPosition(position = 21)
    private String imageUrl;
    @CsvBindByPosition(position = 22)
    private String smallImageUrl;

    @Override
    public EBookSecond toEBook() {
        return new EBookSecond(
            isbn, isbn13,
            authors, originalPublicationYear,
            originalTitle, title,
            averageRating, imageUrl, smallImageUrl);
    }
}
