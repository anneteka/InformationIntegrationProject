package main.database.entity.source;

import lombok.*;

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
    private String euro_price;
    @Column(name = "discount_euro")
    private String discount_euro;
    @Column(name = "type")
    private String type;
    @Column(name = "link_book_page", columnDefinition = "text")
    private String linkBookPage;
    @Column(name = "name")
    private String name;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "edition")
    private String edition;
    @Column(name = "author", columnDefinition = "text")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "published_country")
    private String published_country;
    @Column(name = "language")
    private String language;
    @Column(name = "height")
    private String height;
    @Column(name = "width")
    private String width;
    @Column(name = "spine")
    private String spine;
    @Column(name = "weight")
    private String weight;
    @Column(name = "short_blurb", columnDefinition = "text")
    private String shortBlurb;
    @Column(name = "long_blurb", columnDefinition = "text")
    private String longBlurb;
    @Column(name = "review_blurb", columnDefinition = "text")
    private String blurbReview;

    public EBookFirst(
        String isbn, String euro_price, String discount_euro, String type,
        String linkBookPage, String name, String subtitle, String edition,
        String author, String publisher, String published_country, String language,
        String height, String width, String spine, String weight, String shortBlurb,
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
