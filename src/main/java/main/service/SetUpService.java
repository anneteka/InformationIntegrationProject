package main.service;

import main.database.repository.source.FirstRepository;
import main.database.repository.source.SecondRepository;
import main.database.repository.source.ThirdRepository;
import main.util.CsvParserUtil;
import main.util.cvs.model.BlackwellBook;
import main.util.cvs.model.CsvSecondBook;
import main.util.cvs.model.CsvThirdBook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetUpService {
    private final FirstRepository firstRepo;
    private final SecondRepository secondRepo;
    private final ThirdRepository thirdRepo;
    private static final Logger LOG = LogManager.getFormatterLogger("SetUpService");

    @Autowired
    public SetUpService(FirstRepository firstRepo, SecondRepository secondRepo, ThirdRepository thirdRepo) {
        this.firstRepo = firstRepo;
        this.secondRepo = secondRepo;
        this.thirdRepo = thirdRepo;
    }

    public void setUpSources(String path1, String path2, String path3) throws FileNotFoundException {
        CsvParserUtil<BlackwellBook> firstSourceParcer = new CsvParserUtil<>();
        CsvParserUtil<CsvSecondBook> secondSourceParcer = new CsvParserUtil<>();
        CsvParserUtil<CsvThirdBook> thirdSourceParcer = new CsvParserUtil<>();

        List<BlackwellBook> parsedFirst = firstSourceParcer.parse(path1, BlackwellBook.class);
        List<CsvSecondBook> parsedSecond = secondSourceParcer.parse(path2, CsvSecondBook.class);
        List<CsvThirdBook> parsedThird = thirdSourceParcer.parse(path3, CsvThirdBook.class);

        firstRepo.saveAll(
            parsedFirst.stream().
                map(BlackwellBook::toEBook)
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
        String firstSource = Paths.get("src", "main", "resources", "data", "blackwell_shop_books.csv").toString();
        String secondSource = Paths.get("src", "main", "resources", "data", "books2.csv").toString();
        String thirdSource = Paths.get("src", "main", "resources", "data", "books3_copy.csv").toString();
        setUpSources(firstSource, secondSource, thirdSource);
    }

    public void setUpTestSources() throws FileNotFoundException {
        String firstSource = Paths.get("src", "test", "data", "test1.csv").toString();
        String secondSource = Paths.get("src", "test", "data", "test2.csv").toString();
        String thirdSource = Paths.get("src", "test", "data", "test.csv").toString();
        setUpSources(firstSource, secondSource, thirdSource);
    }

    public void dropDatabase() {

    }

}
