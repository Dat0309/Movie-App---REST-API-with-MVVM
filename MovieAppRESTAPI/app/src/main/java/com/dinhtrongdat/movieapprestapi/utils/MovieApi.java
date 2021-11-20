package com.dinhtrongdat.movieapprestapi.utils;

import com.dinhtrongdat.movieapprestapi.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
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
     * @return
     */
    @GET
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );
}
