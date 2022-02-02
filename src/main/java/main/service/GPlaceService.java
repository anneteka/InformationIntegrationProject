package main.service;

import main.database.entity.global.EGlobalPlace;
import main.database.repository.global.GPlaceRepository;
import main.util.MergeHelperUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<EGlobalPlace> findAll(){
        return (List<EGlobalPlace>) repository.findAll();
    }

    public void mergePlaces(EGlobalPlace keep, EGlobalPlace discard){
        String[] names1 = keep.getPlace().toLowerCase().split(" ");
        String[] names2 = discard.getPlace().toLowerCase().split(" ");

        int minMatches = Math.min(names1.length, names2.length); // at least 2 matching name parts or one if only one name given

        int matches = MergeHelperUtil.getExactMatchesCount(names1, names2);

        if (matches >= minMatches){
            // enough matches, discard second author
            // TODO
            //repository.delete(discard);
            System.out.println(keep.getPlace()+ " : " +discard.getPlace());
        }
    }

}
