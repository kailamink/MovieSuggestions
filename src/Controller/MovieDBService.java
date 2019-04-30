package Controller;

import Model.MovieList;
import Model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService
{
    @GET("/3/find/{external_id}")
    Call<MovieList> searchForMovieByExternalId(@Path("external_id") String externalId
                                            , @Query("api_key") String apiKey
                                            , @Query("external_source") String externalSource);


    @GET("/3/movie/{movie_id}/similar")
    Call<Movies> searchForSimilarMovies(@Path("movie_id") String movieId, @Query("api_key") String apiKey);


}
