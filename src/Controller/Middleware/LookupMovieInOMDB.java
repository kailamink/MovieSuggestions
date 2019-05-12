package Controller.Middleware;

import Controller.OMDBService;
import Model.OMDBMovie;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class LookupMovieInOMDB implements  IMiddleware<Context> {

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
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.properties.getProperty("OMDBUrl"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            OMDBService service = retrofit.create(OMDBService.class);
            String omdbKey = context.properties.getProperty("OMDBKey");
            Call<OMDBMovie> call = service.searchForMovie(omdbKey, context.originalMovieTitle);
            Response<OMDBMovie> response = call.execute();
            context.imdbId = response.body().getImdbID();
            System.out.println("ImdbId is " + context.imdbId);
            invokeProcess(context);
        }
        catch(IOException exc)
        {
            //TODO implement error logging
        }
    }
}
