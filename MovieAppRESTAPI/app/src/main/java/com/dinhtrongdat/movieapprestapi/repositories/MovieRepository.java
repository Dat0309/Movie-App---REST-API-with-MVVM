package com.dinhtrongdat.movieapprestapi.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.dinhtrongdat.movieapprestapi.request.MovieApiClient;

import java.util.List;

/**
 * this class is acting as repository
 */
public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    /**
     * Calling the method in repo
     */
    public void searchMovieAPI(String query, int pageNumber){
        movieApiClient.searchMoviesApi(query, pageNumber);
    }
}


