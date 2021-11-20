package com.dinhtrongdat.movieapprestapi.response;

import com.dinhtrongdat.movieapprestapi.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *  Lớp định nghĩa phương thức lấy nhiều movies (Movies List) - popular movies
 */
public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose()
    private int totalCount;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;


    /**
     * Lấy số lượng phim tìm được
     * @return số lượng phim
     */
    public int getTotalCount(){
        return  totalCount;
    }

    /**
     * Lấy danh sách phim tìm được
     * @return danh sách phim
     */
    public List<MovieModel> getMovies(){
        return movies;
    }

    @Override
    public String
    toString() {
        return "MovieSearchResponse{" +
                "totalCount=" + totalCount +
                ", movies=" + movies +
                '}';
    }
}
