package com.dinhtrongdat.movieapprestapi.response;

import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Lớp định nghĩa phương thức gọi một movie
 */
public class MovieResponse {

    /**
     * 1 - Finding the movie object
     */
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
