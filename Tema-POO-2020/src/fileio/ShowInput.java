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

    /**
     * Sort a list of shows by time duration
     *
     * @param shows list which will be sorted
     * @param sortType set the sort order
     */
    public static void sortVideosByDuration(final List<ShowInput> shows, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            shows.sort((o1, o2) -> {
                if (o1.duration != o2.duration) {
                    return o1.duration - o2.duration;
                }
                return o1.title.compareTo(o2.title);
            });
        } else {
            shows.sort((o1, o2) -> {
                if (o1.duration != o2.duration) {
                    return o2.duration - o1.duration;
                }
                return o2.title.compareTo(o1.title);
            });
        }
    }

    /**
     * Count how many times a current show appear in a list of users
     *
     * @param users list of users
     */
    private void computeCountFavorite(final List<UserInputData> users) {
        for (UserInputData user : users) {
            if (user.getFavoriteMovies().contains(this.getTitle())) {
                countFavorite++;
            }
        }
    }

    /**
     * Use the above method for a list of shows
     *
     * @param shows list of shows
     * @param users list of users
     */
    public static void computeCountFavoriteForArray(final List<ShowInput> shows,
                                                    final List<UserInputData> users) {
        for (ShowInput show : shows) {
            show.computeCountFavorite(users);
        }
    }

    /**
     * Sort a list of shows by the number of appearances in the favorite lists of some users
     * second criteria: name of show
     *
     * @param shows list which will be sorted
     * @param sortType set the sort order
     */
    public static void sortVideosByFavorites(final List<ShowInput> shows, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            shows.sort((o1, o2) -> {
                if (o1.countFavorite != o2.countFavorite) {
                    return o1.countFavorite - o2.countFavorite;
                }
                return o1.title.compareTo(o2.title);
            });
        } else {
            shows.sort((o1, o2) -> {
                if (o1.countFavorite != o2.countFavorite) {
                    return o2.countFavorite - o1.countFavorite;
                }
                return o2.title.compareTo(o1.title);
            });
        }
        shows.removeIf((show) -> show.getCountFavorite() == 0);
    }

    /**
     * Compute the number of views from a given show using a list of users
     *
     * @param users list of users
     */
    private void computeCountViews(final List<UserInputData> users) {
        if (countViews == 0) {
            for (UserInputData user : users) {
                if (user.getHistory().containsKey(this.getTitle())) {
                    countViews += user.getHistory().get(this.getTitle());
                }
            }
        }
    }

    /**
     * Use the above method for a list of shows
     *
     * @param shows list of shows
     * @param users list of users
     */
    public static void computeCountViewsForArray(final List<ShowInput> shows,
                                                 final List<UserInputData> users) {
        for (ShowInput show : shows) {
            show.computeCountViews(users);
        }
    }

    /**
     * Sort a list of shows by the number of views from a list of users
     *
     * @param shows list which will be sorted
     * @param sortType set the sort order
     */
    public static void sortVideosByViews(final List<ShowInput> shows, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            shows.sort((o1, o2) -> {
                if (o1.countViews != o2.countViews) {
                    return o1.countViews - o2.countViews;
                }
                return o1.title.compareTo(o2.title);
            });
        } else {
            shows.sort((o1, o2) -> {
                if (o1.countViews != o2.countViews) {
                    return o2.countViews - o1.countViews;
                }
                return o2.title.compareTo(o1.title);
            });
        }
        shows.removeIf((show) -> show.getCountViews() == 0);
    }

    /**
     * Compute the rating of current show using a list of users
     *
     * @param users list of users
     */
    public abstract void computeRating(List<UserInputData> users);

    /**
     * Use the above method for a list of shows
     *
     * @param shows list of shows
     * @param users list of users
     */
    public static void computeRatingForArray(final List<ShowInput> shows,
                                             final List<UserInputData> users) {
        for (ShowInput show : shows) {
            show.computeRating(users);
        }
    }

    /**
     * Sort a list of shows by ratings
     *
     * @param shows list which will be sorted
     * @param sortType set the sort order
     */
    public static void sortVideosByRating(final List<ShowInput> shows, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            shows.sort((o1, o2) -> Double.compare(o1.rating, o2.rating));
        } else {
            shows.sort((o1, o2) -> Double.compare(o2.rating, o1.rating));
        }
        shows.removeIf((show) -> show.getRating() == 0);
    }

    /**
     * Sort a list of shows by the number of appearances in the favorite lists of some users
     * second criteria: place in a given list of shows
     *
     * @param sortedShows which will be sorted
     * @param shows list of shows from database, used for second criteria
     * @param sortType set the sort order
     */
    public static void sortVideosByFavorites(final List<ShowInput> sortedShows,
                                             final List<ShowInput> shows, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            sortedShows.sort((o1, o2) -> {
                if (o1.countFavorite != o2.countFavorite) {
                    return o1.countFavorite - o2.countFavorite;
                } else {
                    return shows.indexOf(o1) - shows.indexOf(o2);
                }
            });
        } else {
            sortedShows.sort((o1, o2) -> {
                if (o1.countFavorite != o2.countFavorite) {
                    return o2.countFavorite - o1.countFavorite;
                } else {
                    return shows.indexOf(o1) - shows.indexOf(o2);
                }
            });
        }
        sortedShows.removeIf((show) -> show.getCountFavorite() == 0);
    }

    /**
     * Sort a list of shows by ratings
     * second criteria: place in a given list of shows
     *
     * @param sortedShows list which will be sorted
     * @param shows list of shows from database, used for second criteria
     * @param sortType set the sort order
     */
    public static void sortVideosByRating(final List<ShowInput> sortedShows,
                                          final List<ShowInput> shows, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            sortedShows.sort((o1, o2) -> {
                if (o1.getRating() != o2.getRating()) {
                    return Double.compare(o1.rating, o2.rating);
                } else {
                    return shows.indexOf(o1) - shows.indexOf(o2);
                }
            });
        } else {
            sortedShows.sort((o1, o2) -> {
                if (o1.getRating() != o2.getRating()) {
                    return Double.compare(o2.rating, o1.rating);
                } else {
                    return shows.indexOf(o1) - shows.indexOf(o2);
                }
            });
        }
        sortedShows.removeIf((show) -> show.getCountFavorite() == 0);
    }

    /**
     * Compute message for a query
     *
     * @param shows list of shows that will form the message
     * @param number max number of shows that will appear in the message
     * @return String Object
     */
    public static String giveMessage(final List<ShowInput> shows, final int number) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.QUERY_RESULT);
        stringBuilder.append("[");
        if (!shows.isEmpty()) {
            stringBuilder.append(shows.get(0).getTitle());
            for (int i = 1; i < Math.min(shows.size(), number); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(shows.get(i).getTitle());
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Compute a list of shows using a list of movies and a list of serials
     *
     * @param movies list of movies
     * @param serials list of serial
     * @return List Object
     */
    public static List<ShowInput> combineShows(final List<MovieInputData> movies,
                                               final List<SerialInputData> serials) {
        List<ShowInput> shows = new ArrayList<>();
        shows.addAll(movies);
        shows.addAll(serials);
        return shows;
    }
}
