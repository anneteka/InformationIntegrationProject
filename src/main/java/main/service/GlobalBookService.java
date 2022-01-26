package main.service;

import main.database.entity.global.EGlobalAuthor;
import main.database.entity.global.EGlobalBook;
import main.database.entity.global.EGlobalCharacter;
import main.database.entity.global.EGlobalGenre;
import main.database.repository.GlobalAuthorRepository;
import main.database.repository.GlobalBookRepository;
import main.database.repository.GlobalCharacterRepository;
import main.database.repository.GlobalGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
        book.getAuthors().add(author);
        bookRepository.save(book);
    }

    public void addAuthor(EGlobalBook book, String authorName) {
        EGlobalAuthor author = authorService.saveAuthor(authorName);
        book.getAuthors().add(author);
        bookRepository.save(book);
    }

    public void addCharacter(EGlobalBook book, EGlobalCharacter character) {
        book.getCharacters().add(character);
        bookRepository.save(book);
    }

    public void addCharacter(EGlobalBook book, String characterName) {
        EGlobalCharacter character = characterService.saveCharacter(characterName);
        book.getCharacters().add(character);
        bookRepository.save(book);
    }

    public void addGenre(EGlobalBook book, EGlobalGenre genre) {
        book.getGenres().add(genre);
        bookRepository.save(book);
    }

    public void addGenre(EGlobalBook book, String genreName) {
        EGlobalGenre genre = genreService.saveGenre(genreName);
        book.getGenres().add(genre);
        bookRepository.save(book);
    }

    public void removeAuthor(EGlobalBook book, EGlobalAuthor author){
        book.getAuthors().remove(author);
        bookRepository.save(book);
    }

    public void removeCharacter(EGlobalBook book, EGlobalCharacter character){
        book.getAuthors().remove(character);
        bookRepository.save(book);
    }

    public void removeGenre(EGlobalBook book, EGlobalGenre genre){
        book.getAuthors().remove(genre);
        bookRepository.save(book);
    }
}
