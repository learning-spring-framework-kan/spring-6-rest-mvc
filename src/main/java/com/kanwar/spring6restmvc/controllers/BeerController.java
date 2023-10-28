package com.kanwar.spring6restmvc.controllers;

import com.kanwar.spring6restmvc.domain.Beer;
import com.kanwar.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/beer";

    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public ResponseEntity<List<Beer>> getAllBeers(){
        log.info("--BeerController::getAllBeers");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(beerService.getAllBeers());
    }

    @GetMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") UUID beerId){
        log.info("--BeerController::getBeerById");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(beerService.getBeerById(beerId));
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<Beer> createNewBeer(@RequestBody Beer beer){
        log.info("--BeerController::createNewBeer");

        Beer createdBeer = beerService.createNewBeer(beer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("location", createdBeer.getId().toString())
                .body(createdBeer);
    }


    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){
        log.info("--BeerController::updateBeer");

        Beer updatedBeer = beerService.updateBeer(beerId,beer);
        log.info("updatedBeer:{}",updatedBeer);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedBeer);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> deleteBeer(@PathVariable("beerId") UUID beerId){
        log.info("--BeerController::deleteBeer");

        beerService.deleteBeer(beerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

}
