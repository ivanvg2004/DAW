package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.dao.WorldDao;

/**
 * 
 * 
 *
 */
public class Exercise10 {
	private static final WorldDao worldDao = InMemoryWorldDao.getInstance();
	public static void main(String[] args) {
		// Find the richest country of each continent with respect to their GNP (Gross National Product) values.
        worldDao.getAllContinents().stream()
                .map(continent->{
                    return worldDao.findCountriesByContinent(continent).stream()
                            .max((a,b)->Double.compare(a.getGnp(), b.getGnp()));
                }).forEach(c-> System.out.println(c));
	}

}
