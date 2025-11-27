package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.CountryDao;
import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.domain.Country;

/**
 * 
 * 
 *
 */
record Ex6Result(Country country, int numCities) {}
public class Exercise6 {
    private static final CountryDao countryDao = InMemoryWorldDao.getInstance();

    public static void main(String[] args) {
        // Sort the countries by number of their cities in descending order
        countryDao.findAllCountries().stream()
                .map(country -> new Ex6Result(country, country.getCities().size()))
                .sorted((a, b) -> Integer.compare(b.numCities(), a.numCities())) // descendente
                .forEach(r -> {
                    System.out.println(
                            r.country().getName() + " -> " + r.numCities() + " ciudades"
                    );
                });
    }
}
