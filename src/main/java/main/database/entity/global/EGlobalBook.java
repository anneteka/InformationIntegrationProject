package main.database.entity.global;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import main.database.entity.EBook;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name= "global_book")
public class EGlobalBook extends EBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "isbn13")
    private String isbn13;
    @Column(name = "isbn10")
    private String isbn10;
    @Column(name = "publication_date")
    private int publication_date;
    @Column(name = "euro_price")
    private double euroPrice;
    @Column(name = "euro_discount")
    private double euroDiscount;
    @Column(name = "type")
    private String type;
    @Column(name = "link_book_page")
    private String linkBookPage;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "original_title")
    private String originalTitle;
    @Column(name = "edition")
    private String edition;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "published_country")
    private String publishedCountry;
    @Column(name = "language")
    private String language;
    @Column(name = "num_pages")
    private int numPages;
    @Column(name = "height")
    private double height;
    @Column(name = "width")
    private double width;
    @Column(name = "spine")
    private double spine;
    @Column(name = "weight")
    private double weight;
    @Column(name = "short_description", columnDefinition = "text")
    private String short_description;
    @Column(name = "long_description", columnDefinition = "text")
    private String long_description;
    @Column(name = "review", columnDefinition = "text")
    private String review;
    @Column(name = "average_rating")
    private double averageRating;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "small_image_url")
    private String smallImageUrl;
    @Column(name = "series")
    private String series;
    @Column(name = "places")
    private String places;
    @Column(name = "awards", columnDefinition = "text")
    private String awards;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<EGlobalCharacter> characters;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<EGlobalGenre> genres;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<EGlobalAuthor> authors;


    public EGlobalBook(
        String isbn13, String isbn10, int publication_date, double euroPrice,
        double euroDiscount, String type, String linkBookPage, String title,
        String subtitle, String originalTitle, String edition,
        String publisher, String publishedCountry, String language, int numPages,
        double height, double width, double spine, double weight,
        String short_description, String long_description, String review,
        double averageRating, String imageUrl, String smallImageUrl, String series,
        String places, String awards, Set<EGlobalCharacter> characters, Set<EGlobalGenre> genres, Set<EGlobalAuthor> authors
    ) {
        this.isbn13 = isbn13;
        this.isbn10 = isbn10;
        this.publication_date = publication_date;
        this.euroPrice = euroPrice;
        this.euroDiscount = euroDiscount;
        this.type = type;
        this.linkBookPage = linkBookPage;
        this.title = title;
        this.subtitle = subtitle;
        this.originalTitle = originalTitle;
        this.edition = edition;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedCountry = publishedCountry;
        this.language = language;
        this.numPages = numPages;
        this.height = height;
        this.width = width;
        this.spine = spine;
        this.weight = weight;
        this.short_description = short_description;
        this.long_description = long_description;
        this.review = review;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.series = series;
        this.genres = genres;
        this.awards = awards;
        this.characters = characters;
        this.places = places;
    }

    public EGlobalBook(String isbn13, String isbn10, int publication_date, double euroPrice,
                       double euroDiscount, String type, String linkBookPage, String title, String subtitle,
                       String originalTitle, String edition, String publisher, String publishedCountry,
                       String language, int numPages, double height, double width, double spine, double weight, String short_description, String long_description,
                       String review, double averageRating, String imageUrl, String smallImageUrl, String series, String places, String awards) {
        this.isbn13 = isbn13;
        this.isbn10 = isbn10;
        this.publication_date = publication_date;
        this.euroPrice = euroPrice;
        this.euroDiscount = euroDiscount;
        this.type = type;
        this.linkBookPage = linkBookPage;
        this.title = title;
        this.subtitle = subtitle;
        this.originalTitle = originalTitle;
        this.edition = edition;
        this.publisher = publisher;
        this.publishedCountry = publishedCountry;
        this.language = language;
        this.numPages = numPages;
        this.height = height;
        this.width = width;
        this.spine = spine;
        this.weight = weight;
        this.short_description = short_description;
        this.long_description = long_description;
        this.review = review;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.series = series;
        this.places = places;
        this.awards = awards;

        this.characters = Collections.emptySet();
        this.authors = Collections.emptySet();
        this.genres = Collections.emptySet();
    }
}
