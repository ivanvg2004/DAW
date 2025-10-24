package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.dao.WorldDao;

/**
 * 
 * 
 *
 */
record Ex9res(String nom, double densitat){

}
public class Exercise9 {
	private static final WorldDao worldDao = InMemoryWorldDao.getInstance();
	
	public static void main(String[] args) {
		// Sort the countries by their population densities in descending order ignoring
		// zero population countries
        worldDao.findAllCountries().stream()
                .filter(country -> country.getPopulation()>0)
                .map(country -> {
                    double densitat = ((double) country.getPopulation() / country.getSurfaceArea());
                    return new Ex9res(country.getName(), densitat);
                })
                .sorted((a,b) ->Double.compare(a.densitat(), b.densitat()))
                .forEach(r-> System.out.println(r));
	}
}
