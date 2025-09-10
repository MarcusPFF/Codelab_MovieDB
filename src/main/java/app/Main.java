package app;

import app.dtos.MovieDTO;
import app.services.MovieService;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        MovieService movieService = new MovieService();

        MovieDTO movieDTO = movieService.getMovieById(300);
        System.out.println(movieDTO);

        MovieDTO movieDTO2 = movieService.getByRating(8.2);
        System.out.println(movieDTO2);

        List<MovieDTO> movies = movieService.sortedByReleaseDate(10);
        movies.stream().forEach(System.out::println);
    }
}