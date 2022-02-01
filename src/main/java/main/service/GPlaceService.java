package main.service;

import main.database.entity.global.EGlobalPlace;
import main.database.repository.global.GPlaceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GPlaceService {
    private final GPlaceRepository repository;
    private static final Logger LOG = LogManager.getFormatterLogger("GPlaceService");


    @Autowired
    public GPlaceService(GPlaceRepository repository){
        this.repository = repository;
    }

    public EGlobalPlace savePlace(String placeName){
        Optional<EGlobalPlace> place = repository.findByPlace(placeName);
        return place.orElseGet(() -> this.repository.save(new EGlobalPlace(placeName)));
    }

    public EGlobalPlace findPlace(String place){
        return this.repository.findByPlace(place).orElse(null);
    }

}
