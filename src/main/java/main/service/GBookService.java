package main.service;

import main.database.entity.global.*;
import main.database.repository.global.GBookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GBookService {
    private final GBookRepository bookRepository;
    private static final Logger LOG = LogManager.getFormatterLogger("GBookService");

    @Autowired
    public GBookService(GBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<EGlobalBook> findAllByTitleOrOriginalTitle(String title){
        return bookRepository.findAllByTitleContainsOrOriginalTitleContains(title, title);
    }

    public Optional<EGlobalBook> findByIsbn13(String isbn13) {
        if (isbn13 == null || isbn13.isEmpty()) {
            return Optional.empty();
        } else return bookRepository.findByIsbn13(isbn13);
    }

    public Optional<EGlobalBook> findByIsbn10(String isbn10) {
        if (isbn10 == null || isbn10.isEmpty()) {
            return Optional.empty();
        } else return bookRepository.findByIsbn10(isbn10);
    }
}
