package com.tvshow.model;

/**
 * Created by ismael on 14/03/18.
 */

public class Character {
    private String character;
    private String name;
    private String profile_path;

    public Character(String character, String name, String profile_path) {
        this.character = character;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
