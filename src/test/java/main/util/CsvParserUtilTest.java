package main.util;




import main.util.cvs.model.BlackwellBook;
import main.util.cvs.model.CsvSecondBook;
import main.util.cvs.model.CsvThirdBook;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

class CsvParserUtilTest {

    @Test
    void parse() throws FileNotFoundException {
        CsvParserUtil<BlackwellBook> firstSourceParser = new CsvParserUtil<BlackwellBook>();
        CsvParserUtil<CsvSecondBook> secondSourceParser = new CsvParserUtil<CsvSecondBook>();
        CsvParserUtil<CsvThirdBook> thirdSourceParser = new CsvParserUtil<CsvThirdBook>();

        String fisrtSource = Paths.get("src", "test", "data","test1.csv").toString();
        String secondSource = Paths.get("src","test", "data","test2.csv").toString();
        String thirdSource = Paths.get("src", "test", "data","test.csv").toString();

        List<BlackwellBook> parsedFirst = firstSourceParser.parse(fisrtSource, BlackwellBook.class);
        List<CsvSecondBook> parsedSecond = secondSourceParser.parse(secondSource, CsvSecondBook.class);
        List<CsvThirdBook> parsedThird = thirdSourceParser.parse(thirdSource, CsvThirdBook.class);

        System.out.println("done");
    }
}