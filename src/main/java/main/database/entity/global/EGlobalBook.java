package main.database.entity.global;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import main.database.entity.EBook;

import java.util.ArrayList;
import java.util.List;

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
    private Integer publication_date;
    @Column(name = "euro_price")
    private Double euroPrice;
    @Column(name = "euro_discount")
    private Double euroDiscount;
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
    private Integer numPages;
    @Column(name = "height")
    private Double height;
    @Column(name = "width")
    private Double width;
    @Column(name = "spine")
    private Double spine;
    @Column(name = "weight")
    private Double weight;
    @Column(name = "short_description", columnDefinition = "text")
    private String shortDescription;
    @Column(name = "long_description", columnDefinition = "text")
    private String longDescription;
    @Column(name = "review", columnDefinition = "text")
    private String review;
    @Column(name = "average_rating")
    private Double averageRating;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "small_image_url")
    private String smallImageUrl;
    @Column(name = "series")
    private String series;
    @Column(name = "awards", columnDefinition = "text")
    private String awards;
    @Column(name = "source")
    private String source;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<EGlobalCharacter> characters;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<EGlobalGenre> genres;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<EGlobalAuthor> authors;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<EGlobalPlace> places;


    public EGlobalBook(
            String isbn13, String isbn10, Integer publication_date, Double euroPrice,
            Double euroDiscount, String type, String linkBookPage, String title,
            String subtitle, String originalTitle, String edition,
            String publisher, String publishedCountry, String language, Integer numPages,
            Double height, Double width, Double spine, Double weight,
            String shortDescription, String longDescription, String review,
            Double averageRating, String imageUrl, String smallImageUrl, String series,
            String awards, ArrayList<EGlobalPlace> places, ArrayList<EGlobalCharacter> characters,
            ArrayList<EGlobalGenre> genres, ArrayList<EGlobalAuthor> authors, String source
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
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.review = review;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.series = series;
        this.genres = genres;
        this.awards = awards;
        this.characters = characters;
        this.places = places;
        this.source = source;
    }

    public EGlobalBook(String isbn13, String isbn10, Integer publication_date, Double euroPrice,
                       Double euroDiscount, String type, String linkBookPage, String title, String subtitle,
                       String originalTitle, String edition, String publisher, String publishedCountry,
                       String language, Integer numPages, Double height, Double width, Double spine,
                       Double weight, String shortDescription, String longDescription,
                       String review, Double averageRating, String imageUrl, String smallImageUrl,
                       String series, String awards, String source) {
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
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.review = review;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.series = series;
        this.awards = awards;
        this.source = source;
        this.characters = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.places = new ArrayList<>();
    }
}
