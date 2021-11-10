package main.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name= "books1")
public class EBookFirst {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "isbn13")
    private String isbn13;
    @Column(name = "isbn10")
    private String isbn10;
    @Column(name = "title")
    private String title;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "authors")
    private String authors;
    @Column(name = "categories")
    private String categories;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "description")
    private String description;
    @Column(name = "published_year")
    private String publishedYear;
    @Column(name = "average_rating")
    private float averageRating;
    @Column(name = "num_pages")
    private int numPages;
    @Column(name = "rating_count")
    private int ratingCount;

    public EBookFirst(String isbn13, String isbn10, String title, String subtitle, 
                        String authors, String categories, String thumbnail, String description,
                            String publishedYear, float averageRating, int numPages, int ratingCount){
        this.isbn13 = isbn13;
        this.isbn10 = isbn10;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.categories = categories;
        this.thumbnail = thumbnail;
        this.description = description;
        this.publishedYear = publishedYear;
        this.averageRating = averageRating;
        this.numPages = numPages;
        this.ratingCount = ratingCount;
    }
}
