package main.service;

import main.controller.dto.YearStatsDTO;
import main.database.entity.global.*;
import main.database.repository.global.GBookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<EGlobalBook> findAllByTitleOrOriginalTitleContains(String title){
        return bookRepository.findAllByTitleContainsOrOriginalTitleContains(title, title);
    }

    public List<EGlobalBook> findAllByTitleOrOriginalTitle(String title){
        return bookRepository.findAllByTitleOrOriginalTitle(title, title);
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

    public List<EGlobalBook> findAll() {
        return bookRepository.findAll();
    }

    public List<EGlobalBook> findByTitleAndCharacterAndPrice(String title, String character, Double price) {
        if (title != null && character != null && price!=null)
        return bookRepository.query1(title, character, price);
        else if (title!=null && character!=null){
            return bookRepository.findAllByTitleAndCharacter(title, character);
        } else if (title!=null && price!=null){
            return bookRepository.findAllByTitleAndPrice(title, price);
        } else if (price!=null && character!=null){
            return bookRepository.findAllByCharacterAndPrice(character, price);
        } else if (title!=null){
            return  bookRepository.findAllByTitleContainsOrOriginalTitleContains(title, title);
        }else if (character!=null){
            return bookRepository.findAllByCharacter(character);
        }else if (price!=null){
            return bookRepository.findAllByEuroPriceLessThanEqual(price);
        } else {
            return bookRepository.findAll();
        }
    }

    public List<EGlobalBook> findAllByYearAndAuthor(Integer year, String author) {
        if (year!=null && author!=null)
            return bookRepository.findAll();
        else if (year!=null)
            return bookRepository.findAllByYear(year);
        else if (author!=null)
            return bookRepository.findAllByAuthor(author);
        else return bookRepository.findAll();
    }

    public List<YearStatsDTO> getStatsByYears(Integer firstYear, Integer lastYear) {
        List<YearStatsDTO> res = new ArrayList<>();
        for (int i = firstYear; i <= lastYear ; i++) {
            res.add(new YearStatsDTO(i, bookRepository.countByYear(i)));
        }
        return res;
    }
}
