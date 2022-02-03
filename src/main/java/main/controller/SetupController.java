package main.controller;

import main.service.GBookDuplicateService;
import main.service.SetUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class SetupController {
    private final SetUpService setupService;
    private final GBookDuplicateService duplicateService;

    @Autowired
    public SetupController(SetUpService setupService, GBookDuplicateService duplicateService) {
        this.setupService = setupService;
        this.duplicateService = duplicateService;
    }

    @GetMapping(path = "/setup/sources")
    public void setUpSources() throws FileNotFoundException {
        setupService.setUpTestSources();
    }

    @GetMapping(path = "/setup/test-sources")
    public void setUpTestSources() throws FileNotFoundException {
        setupService.setUpFromSources();
    }

    @GetMapping(path = "/setup/global")
    public void processSources(){
        duplicateService.setUpGlobalSchema();
    }
}
