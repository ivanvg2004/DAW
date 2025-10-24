package com.esliceu.streams.exercises;

import com.esliceu.streams.domain.Movie;
import com.esliceu.streams.service.InMemoryMovieService;
import com.esliceu.streams.service.MovieService;

import java.util.List;
import java.util.Map;

record Exercise1Result(String name, int nMovies){}

public class Exercise1 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {
		// Find the number of movies of each director
        List <Exercise1Result> result = movieService.findAllDirectors().stream()
                .map(director -> {
                    List<Movie> movies = movieService
                            .findAllMoviesByDirectorId(director.getId())
                            .stream().toList();
                    return new Exercise1Result(director.getName(), movies.size());
                })
                .toList();
        System.out.println(result);
	}

}
