package main.util;

import com.opencsv.bean.CsvToBeanBuilder;
import main.util.model.CsvFirstBook;
import main.util.model.CsvSecondBook;
import main.util.model.CsvThirdBook;

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
    public static ArrayList<CsvSecondBook> parseSecondSource(String fileName) throws FileNotFoundException {
        ArrayList<CsvSecondBook> beans = new ArrayList<>(new CsvToBeanBuilder(new FileReader(fileName))
            .withType(CsvSecondBook.class)
            .build()
            .parse());
        return beans;
    }
    public static ArrayList<CsvThirdBook> parseThirdSource(String fileName) throws FileNotFoundException {
        ArrayList<CsvThirdBook> beans = new ArrayList<>(new CsvToBeanBuilder(new FileReader(fileName))
            .withType(CsvThirdBook.class)
            .build()
            .parse());
        return beans;
    }
}
