package com.dinhtrongdat.movieapprestapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.dinhtrongdat.movieapprestapi.repositories.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    /**
     *  this class is used for VIEWMODEL
     */

    private MovieRepository movieRepository;

    // Defind LiveData
    private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();

    public MovieViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    /**
     * Call the method in viewModel
     * @param query
     * @param pageNumber
     */
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieAPI(query, pageNumber);
    }
}
