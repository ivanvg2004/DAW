package com.esliceu.streams.exercises;

import com.esliceu.streams.dao.InMemoryWorldDao;
import com.esliceu.streams.dao.WorldDao;
import com.esliceu.streams.domain.Country;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.BiConsumer;

/**
 * 
 * 
 *
 */
public class Exercise12 {
	private static final WorldDao worldDao = InMemoryWorldDao.getInstance();

	private static final BiConsumer<String, LongSummaryStatistics> printEntry = (continent, statistics) -> System.out.printf("%s: %s\n", continent, statistics);

	public static void main(String[] args) {
		// Find the minimum, the maximum and the average population of each continent.
        List<Country> res = worldDao.getAllContinents().stream()
                        .map(continent->{
                            worldDao.findAllCountries().stream()
                                    .
                        })
        System.out.println(res.getFirst());
        System.out.println(res.getLast());
	}

}
