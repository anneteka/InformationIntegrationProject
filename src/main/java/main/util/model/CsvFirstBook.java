package main.util.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvFirstBook {
    @CsvBindByPosition(position = 0)
    public String isbn13;
    @CsvBindByPosition(position = 1)
    public String isbn10;
    @CsvBindByPosition(position = 2)
    public String title;
    @CsvBindByPosition(position = 3)
    public String subtitle;
    @CsvBindByPosition(position = 4)
    public String authors;
    @CsvBindByPosition(position = 5)
    public String categories;
    @CsvBindByPosition(position = 6)
    public String thumbnail;
    @CsvBindByPosition(position = 7)
    public String description;
    @CsvBindByPosition(position = 8)
    public String publishedYear;
    @CsvBindByPosition(position = 9)
    public String averageRating;
    @CsvBindByPosition(position = 10)
    public String numPages;
    @CsvBindByPosition(position = 11)
    public String ratingsCount;
}
