package main.util;

import com.opencsv.bean.CsvToBeanBuilder;
import main.util.model.CsvFirstBook;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class CvsParserUtil {
    public static void parse(String fileName) throws FileNotFoundException {
        ArrayList<CsvFirstBook> beans = new ArrayList<>(new CsvToBeanBuilder(new FileReader(fileName))
            .withType(CsvFirstBook.class)
            .build()
            .parse());
        System.out.println(beans.get(1).title + "  " + beans.get(1).description);
    }
}
