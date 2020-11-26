package fileio;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }

    /**
     * Find movies which have year and genre from two given lists
     * @param movies list of movies
     * @param years list of years
     * @param genres list of genres
     * @return a List object
     */
    public static List<ShowInput> computeShowsByYearAndGenres(final List<MovieInputData> movies,
                                                              final List<String> years,
                                                              final List<String> genres) {
        List<ShowInput> filteredShows = new ArrayList<>();
        for (MovieInputData show : movies) {
            if ((years.contains(null) || years.contains(String.valueOf(show.getYear())))
                    && (genres.contains(null) || show.getGenres().containsAll(genres))) {
                filteredShows.add(show);
            }
        }
        return filteredShows;
    }

    @Override
    public void computeRating(final List<UserInputData> users) {
        double sumRatings = 0;
        int countRatings = 0;
        for (UserInputData user : users) {
            Map<String, Double> ratings = user.getRatings();
            if (ratings.containsKey(this.getTitle() + " season 0")) {
                sumRatings += ratings.get(this.getTitle() + " season 0");
                countRatings++;
            }
        }
        if (countRatings > 0) {
            rating = sumRatings / countRatings;
        }
    }
}
