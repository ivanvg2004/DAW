package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.CityDao;
import com.esliceu.streams.dao.CountryDao;
import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.domain.City;
import com.esliceu.streams.domain.Country;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

record Exercise5res (String continent, String capital, int nPopulation){

}

public class Exercise5 {
	private static final CountryDao countryDao = InMemoryWorldDao.getInstance();
	private static final CityDao cityDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
		// Find the highest populated capital city of each continent
        List<Exercise5res> result = countryDao.getAllContinents().stream()
                .map(continent ->
                        countryDao.findCountriesByContinent(continent).stream()
                                .map(country -> cityDao.findCityById(country.getCapital()))
                                .filter(c -> c != null)
                                .max((a, b) -> a.getPopulation() - b.getPopulation())
                                .map(city -> new Exercise5res(continent, city.getName(), city.getPopulation()))
                )
                .flatMap(Optional::stream)
                .toList();

        System.out.println(result);
	}


}