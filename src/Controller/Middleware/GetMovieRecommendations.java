package Controller.Middleware;

import Controller.MovieDBService;
import Model.Movies;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class GetMovieRecommendations implements  IMiddleware<Context>
{
    private IMiddleware nextMiddleware;
    private IMiddleware middleware;

    @Override
    public void setNextInChain(IMiddleware iMiddleware) {
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
    public void process(Context context) {
        String movieDBId = context.moviedbID;
        if(movieDBId != null)
        {
            try
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(context.properties.getProperty("MovieDBUrl"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MovieDBService service = retrofit.create(MovieDBService.class);
                String movieDBKey = context.properties.getProperty("MovieDBKey");
                Call<Movies> call = service.searchForRecommendations(movieDBId, movieDBKey);
                Response<Movies> response = call.execute();
                context.movies = response.body().results;
                System.out.println("movies are: ");
                context.movies.stream().forEach(x -> System.out.println(x.getOriginalTitle()));
                invokeProcess(context);
            }
            catch(IOException exc)
            {
                //TODO implement error logging
            }
        }
    }
}
