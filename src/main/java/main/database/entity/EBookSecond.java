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
@Table(name= "books2")
public class EBookSecond extends EBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "book_id")
    private int bookID;
    @Column(name = "title")
    private String title;
    @Column(name = "authors")
    private String authors;
    @Column(name = "average_rating")
    private double averageRating;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "isbn13")
    private String isbn13;
    @Column(name = "language_code")
    private String languageCode;
    @Column(name = "num_pages")
    private int numPages;
    @Column(name = "rating_count")
    private int ratingCount;
    @Column(name = "text_review_count")
    private int textReviewsCount;
    @Column(name = "publication_date")
    private String publicationDate; 
    @Column(name = "publisher")
    private String publisher;

    public EBookSecond(int bookID, String title, String authors, double averageRating, String isbn,
                         String isbn13, String languageCode, int numPages, int ratingCount,
                            int textReviewsCount, String publicationDate, String publisher){
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.averageRating = averageRating;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.languageCode = languageCode;
        this.numPages = numPages;
        this.ratingCount = ratingCount;
        this.textReviewsCount = textReviewsCount;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
    }

}
