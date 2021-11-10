package main.util.model;


import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CsvThirdBook {
    @CsvBindByPosition(position = 0)
    public String row;
    @CsvBindByPosition(position = 1)
    public String id;
    @CsvBindByPosition(position = 2)
    public String book_id;
    @CsvBindByPosition(position = 3)
    public String best_book_id;
    @CsvBindByPosition(position = 4)
    public String work_id;
    @CsvBindByPosition(position = 5)
    public String books_count;
    @CsvBindByPosition(position = 6)
    public String isbn;
    @CsvBindByPosition(position = 7)
    public String isbn13;
    @CsvBindByPosition(position = 8)
    public String authors;
    @CsvBindByPosition(position = 9)
    public String original_publication_year;
    @CsvBindByPosition(position = 10)
    public String original_title;
    @CsvBindByPosition(position = 11)
    public String title;
    @CsvBindByPosition(position = 12)
    public String language_code;
    @CsvBindByPosition(position = 13)
    public String average_rating;
    @CsvBindByPosition(position = 14)
    public String ratings_count;
    @CsvBindByPosition(position = 15)
    public String work_ratings_count;
    @CsvBindByPosition(position = 16)
    public String work_text_reviews_count;
    @CsvBindByPosition(position = 17)
    public String ratings_1;
    @CsvBindByPosition(position = 18)
    public String ratings_2;
    @CsvBindByPosition(position = 19)
    public String ratings_3;
    @CsvBindByPosition(position = 20)
    public String ratings_4;
    @CsvBindByPosition(position = 21)
    public String ratings_5;
    @CsvBindByPosition(position = 22)
    public String image_url;
    @CsvBindByPosition(position = 23)
    public String small_image_url;
    @CsvBindByPosition(position = 24)
    public String goodreads_book_id;
    @CsvBindByPosition(position = 25)
    public String tag_name;
    @CsvBindByPosition(position = 26)
    public String description;
}
