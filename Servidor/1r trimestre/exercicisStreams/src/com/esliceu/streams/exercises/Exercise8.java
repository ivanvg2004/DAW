package com.esliceu.streams.exercises;

import com.esliceu.streams.service.InMemoryMovieService;
import com.esliceu.streams.service.MovieService;

/**
 * 
 * 
 *
 */
public class Exercise8 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {
		// Group the movies by the year and list them
        movieService.findAllMovies().stream()
                .map(movie -> movie.getYear())
                .sorted((a, b) -> a-b)
                .distinct()
                .forEach(y->{
                    System.out.println(y);
                    movieService.findAllMoviesByYearRange(y,y).stream()
                            .forEach(m-> System.out.println(m));
                });
	}

}
