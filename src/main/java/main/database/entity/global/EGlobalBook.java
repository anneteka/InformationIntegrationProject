package main.database.entity.global;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "global_book")
public class EGlobalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "isbn13")
    private String isbn13;
    @Column(name = "isbn10")
    private String isbn10;
    @Column(name = "publication_date")
    private String publication_date;
    @Column(name = "euro_price")
    private Double euroPrice;
    @Column(name = "euro_discount")
    private String euroDiscount;
    @Column(name = "type")
    private String type;
    @Column(name = "link_book_page", columnDefinition = "text")
    private String linkBookPage;
    @Column(name = "title", columnDefinition = "text")
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
    private String numPages;
    @Column(name = "height")
    private String height;
    @Column(name = "width")
    private String width;
    @Column(name = "spine")
    private String spine;
    @Column(name = "weight")
    private String weight;
    @Column(name = "short_description", columnDefinition = "text")
    private String shortDescription;
    @Column(name = "long_description", columnDefinition = "text")
    private String longDescription;
    @Column(name = "review", columnDefinition = "text")
    private String review;
    @Column(name = "average_rating")
    private String averageRating;
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

    @ElementCollection
    private List<String> characters;
    @ElementCollection
    private List<String> genres;
    @ElementCollection
    private List<String> authors;
    @ElementCollection
    private List<String> places;


    public EGlobalBook(
            String isbn13, String isbn10, String publication_date, Double euroPrice,
            String euroDiscount, String type, String linkBookPage, String title,
            String subtitle, String originalTitle, String edition,
            String publisher, String publishedCountry, String language, String numPages,
            String height, String width, String spine, String weight,
            String shortDescription, String longDescription, String review,
            String averageRating, String imageUrl, String smallImageUrl, String series,
            String awards, List<String> places, List<String> characters,
            List<String> genres, List<String> authors, String source
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

    public EGlobalBook(String isbn13, String isbn10, String publication_date, Double euroPrice,
                       String euroDiscount, String type, String linkBookPage, String title, String subtitle,
                       String originalTitle, String edition, String publisher, String publishedCountry,
                       String language, String numPages, String height, String width, String spine,
                       String weight, String shortDescription, String longDescription,
                       String review, String averageRating, String imageUrl, String smallImageUrl,
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
