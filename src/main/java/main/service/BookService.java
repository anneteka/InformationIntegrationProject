package main.service;

import main.database.repository.FirstRepository;
import main.database.repository.SecondRepository;
import main.database.repository.ThirdRepository;
import main.util.CsvParserUtil;
import main.util.cvs.model.CsvFirstBook;
import main.util.cvs.model.CsvSecondBook;
import main.util.cvs.model.CsvThirdBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private FirstRepository firstRepo;
    private SecondRepository secondRepo;
    private ThirdRepository thirdRepo;

    @Autowired
    public BookService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
    }

    public void setUp(String path1, String path2, String path3) throws FileNotFoundException {
        CsvParserUtil<CsvFirstBook> firstSourceParcer = new CsvParserUtil<CsvFirstBook>();
        CsvParserUtil<CsvSecondBook> secondSourceParcer = new CsvParserUtil<CsvSecondBook>();
        CsvParserUtil<CsvThirdBook> thirdSourceParcer = new CsvParserUtil<CsvThirdBook>();

        List<CsvFirstBook> parsedFirst = firstSourceParcer.parse(path1, CsvFirstBook.class);
        List<CsvSecondBook> parsedSecond = secondSourceParcer.parse(path2, CsvSecondBook.class);
        List<CsvThirdBook> parsedThird = thirdSourceParcer.parse(path3, CsvThirdBook.class);

        firstRepo.saveAll(
            parsedFirst.stream().
                map(CsvFirstBook::toEBook)
                .collect(Collectors.toList())
        );
        secondRepo.saveAll(
            parsedSecond.stream().
                map(CsvSecondBook::toEBook)
                .collect(Collectors.toList())
        );
        thirdRepo.saveAll(
            parsedThird.stream().
                map(CsvThirdBook::toEBook)
                .collect(Collectors.toList())
        );
    }

    public void setUpFromSources() throws FileNotFoundException {
        String firstSource = Paths.get("src", "main", "resources", "data", "books1.csv").toString();
        String secondSource = Paths.get("src", "main", "resources", "data", "books2.csv").toString();
        String thirdSource = Paths.get("src", "main", "resources", "data", "books3.csv").toString();
        setUp(firstSource, secondSource, thirdSource);
    }

    public void setUpTestSources() throws FileNotFoundException {
        String firstSource = Paths.get("src", "test", "data", "books1.csv").toString();
        String secondSource = Paths.get("src", "test", "data", "books2.csv").toString();
        String thirdSource = Paths.get("src", "test", "data", "books3.csv").toString();
        setUp(firstSource, secondSource, thirdSource);
    }

    public void dropDatabase() {

    }

}
