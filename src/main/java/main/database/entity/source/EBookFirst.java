package main.database.entity.source;

import lombok.*;
import main.database.entity.EBook;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "books1")
public class EBookFirst extends EBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "euro_price")
    private double euro_price;
    @Column(name = "discount_euro")
    private double discount_euro;
    @Column(name = "type")
    private String type;
    @Column(name = "link_book_page")
    private String linkBookPage;
    @Column(name = "name")
    private String name;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "edition")
    private String edition;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "published_country")
    private String published_country;
    @Column(name = "language")
    private String language;
    @Column(name = "height")
    private double height;
    @Column(name = "width")
    private double width;
    @Column(name = "spine")
    private double spine;
    @Column(name = "weight")
    private double weight;
    @Column(name = "short_blurb", columnDefinition = "text")
    private String shortBlurb;
    @Column(name = "long_blurb", columnDefinition = "text")
    private String longBlurb;
    @Column(name = "review_blurb", columnDefinition = "text")
    private String blurbReview;

    public EBookFirst(
        String isbn, double euro_price, double discount_euro, String type,
        String linkBookPage, String name, String subtitle, String edition,
        String author, String publisher, String published_country, String language,
        double height, double width, double spine, double weight, String shortBlurb,
        String longBlurb, String blurbReview
    ) {
        this.isbn = isbn;
        this.euro_price = euro_price;
        this.discount_euro = discount_euro;
        this.type = type;
        this.linkBookPage = linkBookPage;
        this.name = name;
        this.subtitle = subtitle;
        this.edition = edition;
        this.author = author;
        this.publisher = publisher;
        this.published_country = published_country;
        this.language = language;
        this.height = height;
        this.width = width;
        this.spine = spine;
        this.weight = weight;
        this.shortBlurb = shortBlurb;
        this.longBlurb = longBlurb;
        this.blurbReview = blurbReview;
    }
}
