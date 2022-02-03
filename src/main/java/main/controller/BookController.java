package main.controller;

import main.database.entity.global.EGlobalBook;
import main.service.GBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final GBookService service;

    @Autowired
    public BookController(GBookService service){
        this.service = service;
    }

    @GetMapping("/title/{title}")
    public List<EGlobalBook> findByTitle(@PathVariable String title){
        return service.findAllByTitleOrOriginalTitle(title);
    }
}
