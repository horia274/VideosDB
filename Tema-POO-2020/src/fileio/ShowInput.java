package fileio;

import common.Constants;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * Count the number of appearances in favorite lists from a given list of users
     */
    private int countFavorite;
    /**
     * Count total number of views from a list of users
     */
    private int countViews;
    /**
     * Form rating of a show
     */
    protected double rating;
    /**
     * Duration of the show
     */
    protected int duration;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.countFavorite = 0;
        this.countViews = 0;
        this.rating = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int getCountFavorite() {
        return countFavorite;
    }

    public final int getCountViews() {
        return countViews;
    }

    public final void setCountViews(final int countViews) {
        this.countViews = countViews;
    }

    public final double getRating() {
        return rating;
    }

    public final void setRating(final double rating) {
        this.rating = rating;
    }

    public final int getDuration() {
        return duration;
    }
}
