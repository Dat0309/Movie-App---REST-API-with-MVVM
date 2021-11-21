package com.dinhtrongdat.movieapprestapi.utils;

import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.dinhtrongdat.movieapprestapi.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API interface
 */
public interface MovieApi {

    /**
     *  Tìm kiếm phim
     *  https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
     * @param key api key
     * @param query Tên phim
     * @param page số trang
     * @return list movies
     */
    @GET("/3/search/movie")
    public Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    /**
     * Tìm phim theo id
     * https://api.themoviedb.org/3/movie/550?api_key=fdf1aa06f4acd57d8a35bb8ec5973c0b
     * @param movie_id movie id
     * @param api_key api key
     * @return movie
     */
    @GET("/3/movie/{movie_id}")
    public Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
