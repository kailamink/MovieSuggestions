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
    public List<Movie> searchForSimilarMovies(String movieTitle)
    {
        List<Movie> movies = null;
        String imdbId = searchInOMDB(movieTitle);
        if(imdbId != null)
        {
            String movieDBId = searchByIMDBId(imdbId);
            if(movieDBId != null)
            {
                movies = getSimilarMovies(movieDBId);
//                movies.stream().forEach(x -> System.out.println(x.getOriginalTitle()));
            }
        }
        return movies;
    }

    public String searchInOMDB(String movieTitle)
    {
        String imdbId = null;
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(properties.getProperty("OMDBUrl"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            OMDBService service = retrofit.create(OMDBService.class);
            String omdbKey = properties.getProperty("OMDBKey");
            Call<OMDBMovie> call = service.searchForMovie(omdbKey, movieTitle);
            Response<OMDBMovie> response = call.execute();
            imdbId = response.body().getImdbID();
            System.out.println("ImdbId is " + imdbId);
        }
        catch(IOException exc)
        {
            //TODO implement error logging
        }
        return imdbId;
    }

    public String searchByIMDBId(String imdbId)
    {
        String movieDBId = null;
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(properties.getProperty("MovieDBUrl"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MovieDBService service = retrofit.create(MovieDBService.class);
            String movieDBKey = properties.getProperty("MovieDBKey");
            Call<MovieList> call = service.searchForMovieByExternalId(imdbId, movieDBKey, "imdb_id");
            Response<MovieList> response = call.execute();
            if (response.body() != null) {
                List<Movie> movies = response.body().movie_results;
                if(movies.size() > 0)
                {
                    movieDBId = movies.get(0).getId();
                }
            }
            System.out.println("movieDBId is " + movieDBId);
        }
        catch (IOException exc)
        {
            //TODO implement error logging
        }
        return movieDBId;
    }


    public List<Movie> getSimilarMovies(String movieDBId)
    {
        List<Movie> movies = null;
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(properties.getProperty("MovieDBUrl"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MovieDBService service = retrofit.create(MovieDBService.class);
            String movieDBKey = properties.getProperty("MovieDBKey");
            Call<Movies> call = service.searchForSimilarMovies(movieDBId, movieDBKey);
            Response<Movies> response = call.execute();
            movies = response.body().results;
            System.out.println("movies are: ");
            movies.stream().forEach(x -> System.out.println(x.getOriginalTitle()));
        }
        catch(IOException exc)
        {
            //TODO implement error logging
        }
        return movies;
    }
}
