package entertainment;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    private double rating;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }

    /**
     * Compute the rating of a season from a serial using a list of users
     *
     * @param serialTitle title of the serial
     * @param users list of users who rated that season
     * @return a double
     */
    public double computeRating(final String serialTitle, final List<UserInputData> users) {
        double sumRating = 0;
        int countRating = 0;
        for (UserInputData user : users) {
            if (user.getRatings().containsKey(serialTitle + " season " + currentSeason)) {
                sumRating += user.getRatings().get(serialTitle + " season " + currentSeason);
                countRating++;
            }
        }
        if (countRating > 0) {
            rating = sumRating / countRating;
        }
        return rating;
    }
}

