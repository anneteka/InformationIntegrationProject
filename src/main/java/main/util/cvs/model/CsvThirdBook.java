package main.util.cvs.model;


import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import main.database.entity.source.EBookThird;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvThirdBook extends CsvBook {
    @CsvBindByPosition(position = 2)
    private String title;
    @CsvBindByPosition(position = 3)
    private String author;
    @CsvBindByPosition(position = 4)
    private String series;
    @CsvBindByPosition(position = 5)
    private String description;
    @CsvBindByPosition(position = 6)
    private String genres;
    @CsvBindByPosition(position = 7)
    private String awards;
    @CsvBindByPosition(position = 8)
    private String characters;
    @CsvBindByPosition(position = 9)
    private String places;
    @CsvBindByPosition(position = 10)
    private String isbn;
    @CsvBindByPosition(position = 11)
    private String isbn13;
    @CsvBindByPosition(position = 12)
    private String language;
    @CsvBindByPosition(position = 13)
    private String firstPublishDate;
    @CsvBindByPosition(position = 18)
    private double avgRating;


    @Override
    public EBookThird toEBook() {
        return new EBookThird(
            title, author, series,
            description, genres, awards,
            characters, places, isbn,  isbn13,
            language, firstPublishDate, avgRating
    );
    }
}
