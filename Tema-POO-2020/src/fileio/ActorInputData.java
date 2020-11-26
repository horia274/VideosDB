package fileio;

import actor.ActorsAwards;
import common.Constants;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;


/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;
    /**
     * rating actor
     */
    private double rating;
    /**
     * number of awards
     */
    private int countAwards;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = 0;
        this.countAwards = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
