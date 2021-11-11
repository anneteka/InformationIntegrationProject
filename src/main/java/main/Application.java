package main;

import main.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws FileNotFoundException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        //context.getBean(BookService.class).setUpFromSources();
        context.getBean(BookService.class).setUpTestSources();
    }

}