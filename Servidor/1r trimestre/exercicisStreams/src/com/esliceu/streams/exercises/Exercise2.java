package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.CountryDao;
import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.domain.City;
import com.esliceu.streams.domain.Country;

import java.util.List;

record Exercise2Result(String continent, String city, int nHab){
}

public class Exercise2 {
	private static final CountryDao countryDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
		// Find the most populated city of each continent
        List<Exercise2Result> results = countryDao.getAllContinents().stream()
                .map(continent ->{
                    City city = countryDao.findCountriesByContinent(continent).stream()
                            .map(country -> {
                                City maxCity = country.getCities().stream()
                                        .max((a, b)-> a.getPopulation() - b.getPopulation())
                                        .orElse(new City());
                                return maxCity;
                            }).max((a, b) -> a.getPopulation() - b.getPopulation())
                            .orElse(new City());
                    return new Exercise2Result(continent, city.getName(), city.getPopulation());
                })
                .filter(r -> r.nHab() != 0).toList();
        System.out.println(results);
	}

}