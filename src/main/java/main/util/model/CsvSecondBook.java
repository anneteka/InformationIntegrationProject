package main.util.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvSecondBook {
    @CsvBindByPosition(position = 0)
    public String bookID;
    @CsvBindByPosition(position = 1)
    public String title;
    @CsvBindByPosition(position = 2)
    public String authors;
    @CsvBindByPosition(position = 3)
    public String average_rating;
    @CsvBindByPosition(position = 4)
    public String isbn;
    @CsvBindByPosition(position = 5)
    public String isbn13;
    @CsvBindByPosition(position = 6)
    public String language_code;
    @CsvBindByPosition(position = 7)
    public String num_pages;
    @CsvBindByPosition(position = 8)
    public String ratings_count;
    @CsvBindByPosition(position = 9)
    public String text_reviews_count;
    @CsvBindByPosition(position = 10)
    public String publication_date;
    @CsvBindByPosition(position = 11)
    public String publisher;

}
