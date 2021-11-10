package main.util;




import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

class CvsParserUtilTest {

    @Test
    void parse() throws FileNotFoundException {
        Path testFile = Paths.get("src","test","data","books1.csv");
        CvsParserUtil.parse(testFile.toString());
    }
}