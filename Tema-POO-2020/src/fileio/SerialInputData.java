package fileio;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        duration = 0;
        for (Season season : seasons) {
            duration += season.getDuration();
        }
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " duration= "
                + duration + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + " rating= "
                + rating + "\n\n" + '}';
    }

    /**
     * Find serials which have year and genre from two given lists
     * @param serials list of serials
     * @param years list of years
     * @param genres list of genres
     * @return a List object
     */
    public static List<ShowInput> computeShowsByYearAndGenres(final List<SerialInputData> serials,
                                                              final List<String> years,
                                                              final List<String> genres) {
        List<ShowInput> filteredShows = new ArrayList<>();
        for (SerialInputData show : serials) {
            if ((years.contains(null) || years.contains(String.valueOf(show.getYear())))
                    && (genres.contains(null) || show.getGenres().containsAll(genres))) {
                filteredShows.add(show);
            }
        }
        return filteredShows;
    }

    @Override
    public void computeRating(final List<UserInputData> users) {
        double sumRating = 0;
        for (Season season : seasons) {
            sumRating += season.computeRating(this.getTitle(), users);
        }
        rating = sumRating / numberOfSeasons;
    }
}
