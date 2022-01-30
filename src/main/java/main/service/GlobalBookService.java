package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalBook;
import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.GlobalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalBookService {
    private final GlobalBookRepository bookRepository;
    private final GAuthorService authorService;
    private final GGenreService genreService;
    private final GCharacterService characterService;

    @Autowired
    public GlobalBookService(
            GlobalBookRepository bookRepository, GAuthorService authorService,
            GGenreService genreService, GCharacterService characterService
    ) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.characterService = characterService;
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
}
