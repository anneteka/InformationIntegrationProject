package main.database.entity;

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
    @Column(name = "book_id")
    private int bookID;
    @Column(name = "best_book_id")
    private int bestBookID;
    @Column(name = "work_id")
    private int workID;
    @Column(name = "books_count")
    private int booksCount;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "isbn13")
    private String isbn13;
    @Column(name = "authors")
    private String authors;
    @Column(name = "original_publication_year")
    private String originalPublicationYear;
    @Column(name = "original_title")
    private String originalTitle;
    @Column(name = "title")
    private String title;
    @Column(name = "language_code")
    private String languageCode;
    @Column(name = "average_rating")
    private double averageRating;
    @Column(name = "ratings_count")
    private int ratingsCount;
    @Column(name = "work_rating_count")
    private int workRatingsCount;
    @Column(name = "work_text_reviews_count")
    private int workTextReviewsCount;
    @Column(name = "ratings_1")
    private int ratings1;
    @Column(name = "ratings_2")
    private int ratings2;
    @Column(name = "ratings_3")
    private int ratings3;
    @Column(name = "ratings_4")
    private int ratings4;
    @Column(name = "ratings_5")
    private int ratings5;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "small_image_url")
    private String smallImageUrl;
    @Column(name = "goodreads_book_id")
    private int goodreadsBookID;
    @Column(name = "tag_names", columnDefinition = "text")
    private String tagName;
    @Column(name = "description", columnDefinition = "text")
    private String description;

    public EBookThird(
        int bookID, int bestBookID, int workID, int booksCount,
        String isbn, String isbn13, String authors, String originalPublicationYear, String originalTitle,
        String title, String languageCode, Double averageRating, int ratingsCount, int workRatingsCount,
        int workTextReviewsCount, int ratings1, int ratings2, int ratings3, int ratings4, int ratings5,
        String imageUrl, String smallImageUrl, int goodreadsBookID, String tagName, String description) {

        this.bookID = bookID;
        this.bestBookID = bestBookID;
        this.workID = workID;
        this.booksCount = booksCount;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.authors = authors;
        this.originalPublicationYear = originalPublicationYear;
        this.originalTitle = originalTitle;
        this.title = title;
        this.languageCode = languageCode;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.workRatingsCount = workRatingsCount;
        this.workTextReviewsCount = workTextReviewsCount;
        this.ratings1 = ratings1;
        this.ratings2 = ratings2;
        this.ratings3 = ratings3;
        this.ratings4 = ratings4;
        this.ratings5 = ratings5;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.goodreadsBookID = goodreadsBookID;
        this.tagName = tagName;
        this.description = description;
    }

}
