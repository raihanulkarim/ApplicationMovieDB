
package com.example.myapplication.raihanapplicationmovie;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MovieFeedback implements Serializable
{

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("total_pages")
    @Expose
    private long totalPages;
    @SerializedName("total_results")
    @Expose
    private long totalResults;
    private final static long serialVersionUID = 397576994417583325L;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public MovieFeedback withPage(long page) {
        this.page = page;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public MovieFeedback withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public MovieFeedback withTotalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public MovieFeedback withTotalResults(long totalResults) {
        this.totalResults = totalResults;
        return this;
    }

}
