package main;

import main.service.GBookDuplicateService;
import main.service.SetUpService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws FileNotFoundException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // use one of the two methods to fill in data to the databases when the app starts running
        // you only need to run it once and only need one of them
//        context.getBean(SetUpService.class).setUpTestSources();
        context.getBean(SetUpService.class).setUpFromSources();
        //context.getBean(GBookDuplicateService.class).setUpGlobalSchema();
    }

}