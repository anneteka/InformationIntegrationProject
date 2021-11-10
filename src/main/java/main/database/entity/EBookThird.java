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
@Table(name= "books3")
public class EBookThird {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "row_id")
    private Long row;
    @Column(name = "row")
    private Long rowID;
    @Column(name = "book_id")
    private Long bookID;
    @Column(name = "best_book_id")
    private Long bestBookID;
    @Column(name = "work_id")
    private Long workID;
    @Column(name = "books_count")
    private Long booksCount;
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
    private Double averageRating;
    @Column(name = "ratings_count")
    private Long ratingsCount;
    @Column(name = "work_rating_count")
    private Long workRatingsCount;
    @Column(name = "work_text_reviews_count")
    private Long workTextReviewsCount;
    @Column(name = "ratings_1")
    private Long ratings1;
    @Column(name = "ratings_2")
    private Long ratings2;
    @Column(name = "ratings_3")
    private Long ratings3;
    @Column(name = "ratings_4")
    private Long ratings4;
    @Column(name = "ratings_5")
    private Long ratings5;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "small_image_url")
    private String smallImageUrl;
    @Column(name = "goodreads_book_id")
    private Long goodreadsBookID;
    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "description")
    private String description;
    
    public EBookThird(Long row, Long rowID, Long bookID, Long bestBookID, Long workID, Long booksCount,
                        String isbn, String isbn13, String authors, String originalPublicationYear, String originalTitle,
                            String title, String languageCode, Double averageRating, Long ratingsCount, Long workRatingsCount,
                                Long workTextReviewsCount, Long ratings1, Long ratings2, Long ratings3, Long ratings4, Long ratings5,
                                    String imageUrl, String smallImageUrl, Long goodreadsBookID, String tagName, String description){

        this.row = row;
        this.rowID = rowID;
        this.bookID = bookID;
        this.bestBookID = bestBookID;
        this.workID = workID;
        this.booksCount = booksCount;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.authors = authors;
        this.originalPublicationYear = originalPublicationYear;
        this. originalTitle = originalTitle;
        this.title = title;
        this.languageCode = languageCode;
        this. averageRating = averageRating;
        this. ratingsCount = ratingsCount;
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
        this. description = description;
    }

}
