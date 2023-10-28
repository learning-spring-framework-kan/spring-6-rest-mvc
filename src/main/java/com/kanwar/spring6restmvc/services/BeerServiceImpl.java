package com.kanwar.spring6restmvc.services;

import com.kanwar.spring6restmvc.domain.Beer;
import com.kanwar.spring6restmvc.domain.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    public Map<UUID,Beer> beerMap;

    public BeerServiceImpl() {

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("23.26"))
                .quantityOnHand(3028)
                .upc("1234")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .price(new BigDecimal("69.11"))
                .quantityOnHand(870)
                .upc("9122089364369")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("No Hammers On The Bar")
                .beerStyle(BeerStyle.WHEAT)
                .price(new BigDecimal("51.37"))
                .quantityOnHand(778)
                .upc("0083783375213")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.beerMap = new HashMap<>();
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> getAllBeers() {
        log.info("  --BeerServiceImpl::getAllBeers--");
        return new ArrayList<> (beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.info("  --BeerServiceImpl::getBeerById--");
        return beerMap.get(id);
    }

    @Override
    public Beer createNewBeer(Beer beer){
        log.info("  --BeerServiceImpl::createNewBeer--");
        beer.setId(UUID.randomUUID());
        beerMap.put(beer.getId(),beer);
        return beer;
    }

    @Override
    public Beer updateBeer(UUID id, Beer beer){
        log.info("  --BeerServiceImpl::updateBeer--");
        if(beerMap.containsKey(id)){
            Beer beerUpdated = beerMap.get(id);
            beerUpdated.setBeerName(beer.getBeerName());
            beerUpdated.setBeerStyle(beer.getBeerStyle());
            beerUpdated.setPrice(beer.getPrice());
            beerMap.put(beerUpdated.getId(),beerUpdated);
            return beerUpdated;
        }
        return null;
    }

    @Override
    public void deleteBeer(UUID id){
        log.info("  --BeerServiceImpl::deleteBeer--");
        if(beerMap.containsKey(id))  beerMap.remove(id);
    }
}
