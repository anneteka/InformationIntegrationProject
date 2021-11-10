package main.util;

import com.opencsv.bean.CsvToBeanBuilder;
import main.util.model.CsvFirstBook;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class CvsParserUtil {
    public static ArrayList<CsvFirstBook> parseFirstSource(String fileName) throws FileNotFoundException {
        ArrayList<CsvFirstBook> beans = new ArrayList<>(new CsvToBeanBuilder(new FileReader(fileName))
            .withType(CsvFirstBook.class)
            .build()
            .parse());
        return beans;
    }
}
