package Controller;


import Model.Movie;
import Model.MovieList;
import Model.Movies;
import Model.OMDBMovie;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MovieController
{
    Properties properties = new Properties();

    public MovieController() throws IOException
    {
        InputStream input = new FileInputStream("config.properties");
        properties.load(input);
    }
    public List<Movie> searchForSimilarMovies(String movieTitle) throws IOException
    {
        List<Movie> movies = null;
        String imdbId = searchInOMDB(movieTitle);
        if(imdbId != null)
        {
            String movieDBId = searchByIMDBId(imdbId);
            if(movieDBId != null)
            {
                movies = getSimilarMovies(movieDBId);
                movies.stream()
                        .forEach(x -> System.out.println(x.getOriginalTitle()));
            }
        }
        return movies;
    }

    public String searchInOMDB(String movieTitle) throws IOException
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(properties.getProperty("OMDBUrl"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OMDBService service = retrofit.create(OMDBService.class);
        String omdbKey = properties.getProperty("OMDBKey");
        Call<OMDBMovie> call = service.searchForMovie(omdbKey, movieTitle);
        Response<OMDBMovie> response = call.execute();
        String imdbId = response.body().getImdbID();
        return imdbId;
    }

    public String searchByIMDBId(String imdbId) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(properties.getProperty("MovieDBUrl"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDBService service = retrofit.create(MovieDBService.class);
        String movieDBKey = properties.getProperty("MovieDBKey");
        Call<MovieList> call = service.searchForMovieByExternalId(imdbId, movieDBKey, "imdb_id");
        Response<MovieList> response = call.execute();
        String movieDBId = null;
        if(response.body() != null)
        {
            List<Movie> movies = response.body().movie_results;
            movieDBId = movies.get(0).getId();
        }
        return movieDBId;
    }


    public List<Movie> getSimilarMovies(String movieDBId) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(properties.getProperty("MovieDBUrl"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDBService service = retrofit.create(MovieDBService.class);
        String movieDBKey = properties.getProperty("MovieDBKey");
        Call<Movies> call = service.searchForSimilarMovies(movieDBId, movieDBKey);
        Response<Movies> response = call.execute();
        List<Movie> movies = response.body().results;
        return movies;
    }
}
