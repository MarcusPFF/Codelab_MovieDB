package app.services;

import app.dtos.MovieDTO;
import app.util.MovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final String API_KEY = System.getenv("MOVIEDB_API_KEY");
    ObjectMapper objectMapper = new ObjectMapper();

    public MovieDTO getMovieById(int movieId) {
        MovieDTO movieDTO = null;
        try {

            String url = BASE_URL + movieId + "?api_key=" + API_KEY;
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                String json = response.body();
                movieDTO = objectMapper.readValue(json, MovieDTO.class);
                //System.out.println(response.body());
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }

    public MovieDTO getByRating(double rating) {
        MovieDTO movieDTO = null;

        try {

            String url = BASE_URL + rating + "?api_key=" + API_KEY;
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                String json = response.body();
                movieDTO = objectMapper.readValue(json, MovieDTO.class);
                //System.out.println(response.body());
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }

    public List<MovieDTO> sortedByReleaseDate(int totalPages) {
        List<MovieDTO> allMovies = new ArrayList<>();

        try {

            HttpClient client = HttpClient.newHttpClient();

            for (int page = 1; page <= totalPages; page++) {
                String url = "https://api.themoviedb.org/3/discover/movie"
                        + "?api_key=" + API_KEY
                        + "&sort_by=primary_release_date.asc"
                        + "&page=" + page;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    MovieResponse searchResponse =
                            objectMapper.readValue(response.body(), MovieResponse.class);

                    if (searchResponse.getResults() != null) {
                        allMovies.addAll(searchResponse.getResults());
                    }
                } else {
                    System.out.println("GET request failed. Status code: " + response.statusCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMovies;
    }
}
