package com.tvshow.model;

import java.util.List;

/**
 * Created by ismael on 14/03/18.
 */

public class TVShow {
    private TVShowInfo tvShowInfo;
    private List<Character> characterList;

    public TVShow() {
    }

    public TVShowInfo getTvShowInfo() {
        return tvShowInfo;
    }

    public void setTvShowInfo(TVShowInfo tvShowInfo) {
        this.tvShowInfo = tvShowInfo;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }
}
