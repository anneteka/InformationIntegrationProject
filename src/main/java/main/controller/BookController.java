package main.controller;

import main.database.entity.global.EGlobalBook;
import main.service.GBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final GBookService service;

    @Autowired
    public BookController(GBookService service){
        this.service = service;
    }

    @GetMapping("/title-contains/{title}")
    public List<EGlobalBook> findByTitleContains(@PathVariable String title){
        return service.findAllByTitleOrOriginalTitleContains(title);
    }

    @GetMapping("/title/{title}")
    public List<EGlobalBook> findByTitle(@PathVariable String title){
        return service.findAllByTitleOrOriginalTitle(title);
    }

    @GetMapping("/all")
    public List<EGlobalBook> findAll(){
        return service.findAll();
    }

    @GetMapping("/query")
    public List<EGlobalBook> findBy(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "character", required = false) String character,
            @RequestParam(name = "max-price", required = false) Double price
    ){
        return service.findByTitleAndCharacterAndPrice(title, character, price);
    }
}
