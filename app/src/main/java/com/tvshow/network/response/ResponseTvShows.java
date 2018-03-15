package com.tvshow.network.response;

import com.tvshow.model.TVShowInfo;
import com.tvshow.network.Endpoints;
import java.util.List;

/**
 * Created by ismael on 14/03/18.
 */

public class ResponseTvShows {
    public List<TVShowInfo> results;

    public List<TVShowInfo> getResults() {
        for(TVShowInfo item: results) {
            item.setPoster_path(Endpoints.BASE_IMAGE + item.getPoster_path());
        }

        return results;
    }
}
