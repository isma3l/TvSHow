package com.tvshow.model;

/**
 * Created by ismael on 14/03/18.
 */

public class TVShowInfo {
    private String id;
    private String name;
    private String overview;
    private String poster_path;
    private String popularity;

    public TVShowInfo(String id, String name, String overview, String poster_path, String popularity) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}
