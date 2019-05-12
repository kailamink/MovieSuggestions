package Controller.Middleware;

import Model.Movie;
import java.util.List;
import java.util.Properties;

public class Context
{
    public String originalMovieTitle;
    public String imdbId;
    public String moviedbID;
    public List<Movie> movies;
    public Properties properties;


    public Context(String originalMovieTitle, Properties properties)
    {
        this.originalMovieTitle = originalMovieTitle;
        this.properties = properties;
    }
}
