package Controller;

import Model.OMDBMovie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDBService
{
    @GET("/")
    Call<OMDBMovie> searchForMovie(@Query("apikey") String apikey, @Query("t") String movieTitle);
}
