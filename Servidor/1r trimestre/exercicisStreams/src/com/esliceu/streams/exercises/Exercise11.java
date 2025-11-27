package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.dao.WorldDao;
import com.esliceu.streams.domain.Country;

import java.util.List;

/**
 * 
 * 
 *
 */
public class Exercise11 {
	private static final WorldDao worldDao = InMemoryWorldDao.getInstance();

	public static void main(String[] args) {
        // Find the minimum, the maximum and the average population of world countries
        List<Country> res = worldDao.findAllCountries().stream()
                .filter(country -> country.getPopulation() > 0)
                .sorted((a, b) -> a.getPopulation() - b.getPopulation()).toList();
        System.out.println(res.getFirst());
        System.out.println(res.getLast());
    }
}
