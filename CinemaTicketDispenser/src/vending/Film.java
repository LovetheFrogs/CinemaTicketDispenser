package vending;

import java.io.Serializable;

public class Film implements Serializable{
    
    private final String name;
    private final String poster;
    private final int duration;
    private final String description;

    public Film(String name, String poster, int duration, String description) {
        this.name = name;
        this.poster = poster;
        this.duration = duration;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }
}
