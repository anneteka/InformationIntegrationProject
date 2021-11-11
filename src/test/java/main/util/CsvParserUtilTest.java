package main.util;




import main.util.cvs.model.CsvFirstBook;
import main.util.cvs.model.CsvSecondBook;
import main.util.cvs.model.CsvThirdBook;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class CsvParserUtilTest {

    @Test
    void parse() throws FileNotFoundException {
        CsvParserUtil<CsvFirstBook> firstSourceParser = new CsvParserUtil<CsvFirstBook>();
        CsvParserUtil<CsvSecondBook> secondSourceParser = new CsvParserUtil<CsvSecondBook>();
        CsvParserUtil<CsvThirdBook> thirdSourceParser = new CsvParserUtil<CsvThirdBook>();

        String fisrtSource = Paths.get("src", "test", "data","books1.csv").toString();
        String secondSource = Paths.get("src","test", "data","books2.csv").toString();
        String thirdSource = Paths.get("src", "test", "data","books3.csv").toString();

        List<CsvFirstBook> parsedFirst = firstSourceParser.parse(fisrtSource, CsvFirstBook.class);
        List<CsvSecondBook> parsedSecond = secondSourceParser.parse(secondSource, CsvSecondBook.class);
        List<CsvThirdBook> parsedThird = thirdSourceParser.parse(thirdSource, CsvThirdBook.class);

        System.out.println("done");
    }
}