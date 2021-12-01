package main.util.cvs.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import main.database.entity.EBook;
import main.database.entity.EBookFirst;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BlackwellBook extends CsvBook {
    @CsvBindByPosition(position = 0)
    private String isbn;
    @CsvBindByPosition(position = 2)
    private double euro_price;
    @CsvBindByPosition(position = 3)
    private double discount_euro;
    @CsvBindByPosition(position = 4)
    private String type;
    @CsvBindByPosition(position = 6)
    private String linkBookPage;
    @CsvBindByPosition(position = 7)
    private String name;
    @CsvBindByPosition(position = 8)
    private String subtitle;
    @CsvBindByPosition(position = 9)
    private String edition;
    @CsvBindByPosition(position = 10)
    private String author;
    @CsvBindByPosition(position = 14)
    private String publisher;
    @CsvBindByPosition(position = 15)
    private String published_country;
    @CsvBindByPosition(position = 17)
    private String language;
    @CsvBindByPosition(position = 19)
    private double height;
    @CsvBindByPosition(position = 20)
    private double width;
    @CsvBindByPosition(position = 21)
    private double spine;
    @CsvBindByPosition(position = 22)
    private double weight;
    @CsvBindByPosition(position = 24)
    private String shortBlurb;
    @CsvBindByPosition(position = 25)
    private String longBlurb;
    @CsvBindByPosition(position = 26)
    private String blurbReview;

    @Override
    public EBookFirst toEBook() {
        return new EBookFirst(
            isbn, euro_price, discount_euro, type,
            linkBookPage, name, subtitle, edition,
            author, publisher, published_country, language,
            height, width, spine, weight, shortBlurb,
            longBlurb, blurbReview
        );
    }
}
