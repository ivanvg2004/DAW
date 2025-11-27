package com.esliceu.streams.exercises;

import com.esliceu.streams.service.InMemoryMovieService;
import com.esliceu.streams.service.MovieService;

/**
 * 
 * 
 *
 */
public class Exercise7 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {
		// Find the list of movies having the genres "Drama" and "Comedy" only
        movieService.findAllMovies().stream()
                .filter(m-> m.getGenres().size() == 2)
                .filter(m -> m.getGenres()
                        .stream()
                        .filter(g -> g.getName().equals("Drama"))
                        .count()>0)
                .filter(m -> m.getGenres()
                        .stream()
                        .filter(g -> g.getName().equals("Comedy"))
                        .count()>0)
                .forEach(System.out::println);
	}

}
