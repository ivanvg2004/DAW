package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.CityDao;
import com.esliceu.streams.dao.CountryDao;
import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.domain.City;
import com.esliceu.streams.domain.Country;

/**
 * 
 * 
 *
 */
public class Exercise4 {
	private static final CountryDao countryDao = InMemoryWorldDao.getInstance();
	private static final CityDao cityDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
		// Find the highest populated capital city
        City resultat = countryDao.findAllCountries().stream()
                .map(country -> {
                    return cityDao.findCityById(country.getCapital());
                })
                .filter(c -> c != null)
                .max((a, b) -> a.getPopulation() - b.getPopulation())
                .orElse(new City());
        System.out.println(resultat);
	}

}
