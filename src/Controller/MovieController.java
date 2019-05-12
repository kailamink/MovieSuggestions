package Controller;


import Controller.Middleware.Context;
import Controller.Middleware.GetMovieRecommendations;
import Controller.Middleware.LookupMovieByIMDBId;
import Controller.Middleware.LookupMovieInOMDB;
import Model.Movie;
import Model.MovieList;
import Model.Movies;
import Model.OMDBMovie;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileInputStream;
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
        LookupMovieInOMDB lookupMovieInOMDB = new LookupMovieInOMDB();
        LookupMovieByIMDBId lookupMovieByIMDBId = new LookupMovieByIMDBId();
        GetMovieRecommendations getMovieRecommendations = new GetMovieRecommendations();

        lookupMovieByIMDBId.setNextInChain(getMovieRecommendations);
        lookupMovieInOMDB.setNextInChain(lookupMovieByIMDBId);

        Context context = new Context(movieTitle, properties);
        lookupMovieInOMDB.process(context);
        return context.movies;
    }
}
