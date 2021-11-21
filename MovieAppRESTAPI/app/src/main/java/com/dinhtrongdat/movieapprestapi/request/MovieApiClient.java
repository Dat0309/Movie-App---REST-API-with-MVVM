package com.dinhtrongdat.movieapprestapi.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dinhtrongdat.movieapprestapi.AppExecutors;
import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.dinhtrongdat.movieapprestapi.response.MovieSearchResponse;
import com.dinhtrongdat.movieapprestapi.utils.Credentials;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    // LiveData
    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    //making Global runable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    /**
     *
     * @param query
     * @param pageNumber
     */
    public void searchMoviesApi(String query, int pageNumber){
        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandle = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandle.cancel(true);
            }
        }, 3000, TimeUnit.MICROSECONDS);
    }

    /**
     * Retreiving data from RESTAPI by runnable class
     * có 2 loại query là ID và tìm kiếm.
     */
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            // getting the response object
            try{
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        //searching data to live data
                        mMovies.postValue(list);
                    }else{

                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error" + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Canceling search request");
            cancelRequest = true;
        }
    }

}
