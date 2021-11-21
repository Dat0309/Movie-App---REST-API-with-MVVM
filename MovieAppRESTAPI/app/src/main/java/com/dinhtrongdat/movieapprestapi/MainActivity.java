package com.dinhtrongdat.movieapprestapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.hardware.lights.Light;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.dinhtrongdat.movieapprestapi.request.Service;
import com.dinhtrongdat.movieapprestapi.response.MovieSearchResponse;
import com.dinhtrongdat.movieapprestapi.utils.Credentials;
import com.dinhtrongdat.movieapprestapi.utils.MovieApi;
import com.dinhtrongdat.movieapprestapi.viewmodel.MovieViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnGet;
    /**
     * ViewModel
     * UI -> ViewModel -> Repository -> API Client
     */
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        btnGet = findViewById(R.id.btnGet);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        ObserveeAnyChange();

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovieApi("Fast", 1);
            }
        });
    }

    /**
     * Get movies by key
     */
    private void GetRetrofitResponse() {
        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                        Credentials.API_KEY,
                        "Fast",
                        1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    Log.v("Tag", "the response" + response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for(MovieModel movie : movies){
                        Log.v("Tag", "The Name movie " + movie.getTitle());
                    }
                }
                else{
                    try{
                        Log.v("Tag", "Error" + response.errorBody().string());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    /**
     * Get movie by id
     */
    private void GetMovieByID(){
        MovieApi movieApi = Service.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                550,
                Credentials.API_KEY
        );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200){
                    MovieModel movie = response.body();
                    Log.v("Tag", "The movie " + response.body().getTitle());
                }
                else{
                    try {
                        Log.v("Tag", "Error" + response.errorBody().string());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    /**
     * Observing any data change
     */
    private void ObserveeAnyChange(){

        movieViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    for(MovieModel movieModel : movieModels){
                        // Get the data in the log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    }
                }
            }
        });
    }

    /**
     * Call method in Main
     * @param query
     * @param pageNumber
     */
    private void searchMovieApi(String query, int pageNumber){
        movieViewModel.searchMovieApi(query, pageNumber);
    }
}