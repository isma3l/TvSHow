package com.tvshow.network.response;


import java.util.List;
import com.tvshow.model.Character;
import com.tvshow.network.Endpoints;

/**
 * Created by ismael on 15/03/18.
 */

public class ResponseCast {
    public List<Character> cast;

    public List<Character> getCast() {
        for(Character character: cast) {
            character.setProfile_path(Endpoints.BASE_IMAGE + character.getProfile_path());
        }
        return cast;
    }
}
