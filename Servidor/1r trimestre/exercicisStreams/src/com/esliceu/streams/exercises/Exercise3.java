package com.esliceu.streams.exercises;

import com.esliceu.streams.domain.Genre;
import com.esliceu.streams.domain.Movie;
import com.esliceu.streams.service.InMemoryMovieService;
import com.esliceu.streams.service.MovieService;

import java.util.List;

record Exercisi3Result(String name, int ngenres){}
public class Exercise3 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {
		// Find the number of genres of each director's movies
        List<Exercisi3Result> l1 = movieService.findAllDirectors().stream()
                .map(director -> {
                    List<Genre> list = movieService.findAllMoviesByDirectorId(director.getId())
                            .stream().map(movie -> movie.getGenres())
                            .flatMap(List::stream).distinct().toList();
                    return new Exercisi3Result(director.getName(), list.size());
                }).toList();
        System.out.println(l1);
	}

}