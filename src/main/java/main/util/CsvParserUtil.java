package main.util;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.NoArgsConstructor;
import main.util.cvs.model.CsvBook;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@NoArgsConstructor
public class CsvParserUtil<Book extends CsvBook> {

    public List<Book> parse(String fileName, Class classType) throws FileNotFoundException {
        List<Book> beans = new CsvToBeanBuilder(new FileReader(fileName))
            .withType(classType)
            .build()
            .parse();
        return beans;
    }
}
