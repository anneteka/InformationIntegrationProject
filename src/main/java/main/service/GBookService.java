package main.service;

import main.database.entity.global.*;
import main.database.repository.global.GBookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GBookService {
    private final GBookRepository bookRepository;
    private final GAuthorService authorService;
    private final GGenreService genreService;
    private final GCharacterService characterService;
    private final GPlaceService placeService;
    private static final Logger LOG = LogManager.getFormatterLogger("GBookService");

    @Autowired
    public GBookService(
            GBookRepository bookRepository, GAuthorService authorService,
            GGenreService genreService, GCharacterService characterService,
            GPlaceService placeService
    ) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.characterService = characterService;
        this.placeService = placeService;
    }

    public void addAuthor(EGlobalBook book, EGlobalAuthor author) {
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
            bookRepository.save(book);
        }
    }

    public void addAuthor(EGlobalBook book, String authorName) {
        EGlobalAuthor author = authorService.saveAuthor(authorName);
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
            bookRepository.save(book);
        }
    }

    public void addCharacter(EGlobalBook book, EGlobalCharacter character) {
        if (!book.getCharacters().contains(character)) {
            book.getCharacters().add(character);
            bookRepository.save(book);
        }
    }

    public void addCharacter(EGlobalBook book, String characterName) {
        EGlobalCharacter character = characterService.saveCharacter(characterName);
        if (!book.getCharacters().contains(character)) {
            book.getCharacters().add(character);
            bookRepository.save(book);
        }
    }

    public void addGenre(EGlobalBook book, EGlobalGenre genre) {
        if (!book.getGenres().contains(genre)) {
            book.getGenres().add(genre);
            bookRepository.save(book);
        }
    }

    public void addGenre(EGlobalBook book, String genreName) {
        EGlobalGenre genre = genreService.saveGenre(genreName);
        if (!book.getGenres().contains(genre)) {
            book.getGenres().add(genre);
            bookRepository.save(book);
        }
    }

    public void removeAuthor(EGlobalBook book, EGlobalAuthor author) {
        book.getAuthors().remove(author);
        bookRepository.save(book);
    }

    public void removeCharacter(EGlobalBook book, EGlobalCharacter character) {
        book.getCharacters().remove(character);
        bookRepository.save(book);
    }

    public void removeGenre(EGlobalBook book, EGlobalGenre genre) {
        book.getGenres().remove(genre);
        bookRepository.save(book);
    }

    public void addPlace(EGlobalBook book, String placeName) {
        EGlobalPlace place = placeService.savePlace(placeName);
        if (!book.getPlaces().contains(place)) {
            book.getPlaces().add(place);
            bookRepository.save(book);
        }
    }

    public void addPlace(EGlobalBook book, EGlobalPlace place) {
        if (!book.getPlaces().contains(place)) {
            book.getPlaces().add(place);
            bookRepository.save(book);
        }
    }

    public void removePlace(EGlobalBook book, EGlobalPlace place) {
        book.getPlaces().remove(place);
        bookRepository.save(book);
    }

    public Optional<EGlobalBook> findByIsbn13(String isbn13) {
        if (isbn13 == null||isbn13.isEmpty()) {
            return Optional.empty();
        } else return bookRepository.findByIsbn13(isbn13);
    }

    public Optional<EGlobalBook> findByIsbn10(String isbn10) {
        if (isbn10 == null||isbn10.isEmpty()) {
            return Optional.empty();
        } else return bookRepository.findByIsbn10(isbn10);
    }
}
