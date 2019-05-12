package Controller.Middleware;

import Controller.MovieDBService;
import Model.Movie;
import Model.MovieList;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class LookupMovieByIMDBId implements  IMiddleware<Context> {
    private IMiddleware nextMiddleware;

    @Override
    public void setNextInChain(IMiddleware iMiddleware)
    {
        this.nextMiddleware = iMiddleware;
    }

    @Override
    public void invokeProcess(Context context) {
        if(this.nextMiddleware != null)
        {
            this.nextMiddleware.process(context);
        }
    }

    @Override
    public void process(Context context)
    {
        String imdbId = context.imdbId;
        if(imdbId != null)
        {
            try
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(context.properties.getProperty("MovieDBUrl"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MovieDBService service = retrofit.create(MovieDBService.class);
                String movieDBKey = context.properties.getProperty("MovieDBKey");
                Call<MovieList> call = service.searchForMovieByExternalId(imdbId, movieDBKey, "imdb_id");
                Response<MovieList> response = call.execute();
                if (response.body() != null)
                {
                    List<Movie> movies = response.body().movie_results;
                    if (movies.size() > 0)
                    {
                        context.moviedbID = movies.get(0).getId();
                    }
                }
                System.out.println("movieDBId is " + context.moviedbID);
                invokeProcess(context);
            } catch (IOException exc) {
                //TODO implement error logging
            }
        }
    }
}
