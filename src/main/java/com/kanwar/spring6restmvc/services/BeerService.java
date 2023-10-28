package com.kanwar.spring6restmvc.services;

import com.kanwar.spring6restmvc.domain.Beer;
import java.util.List;
import java.util.UUID;


public interface BeerService {

    List<Beer> getAllBeers();

    Beer getBeerById(UUID id);

    Beer createNewBeer(Beer beer);

    Beer updateBeer(UUID id, Beer beer);

    void deleteBeer(UUID id);
}
