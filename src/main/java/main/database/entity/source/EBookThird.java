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
@Table(name = "books3")
public class EBookThird extends EBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title", columnDefinition = "text")
    private String title;
    @Column(name = "author", columnDefinition = "text")
    private String author;
    @Column(name = "series")
    private String series;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "genres", columnDefinition = "text")
    private String genres;
    @Column(name = "awards", columnDefinition = "text")
    private String awards;
    @Column(name = "characters", columnDefinition = "text")
    private String characters;
    @Column(name = "places", columnDefinition = "text")
    private String places;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "isbn13")
    private String isbn13;
    @Column(name = "language")
    private String language;
    @Column(name = "first_publish_date")
    private String firstPublishDate;
    @Column(name = "avg_rating")
    private String avgRating;

    public EBookThird(
        String title, String author, String series,
        String description, String genres, String awards,
        String characters, String places, String isbn, String isbn13,
        String language, String firstPublishDate, String avgRating
    ) {
        this.title = title;
        this.author = author;
        this.series = series;
        this.description = description;
        this.genres = genres;
        this.awards = awards;
        this.characters = characters;
        this.places = places;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.language = language;
        this.firstPublishDate = firstPublishDate;
        this.avgRating = avgRating;
    }
}
