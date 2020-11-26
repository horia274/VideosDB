package fileio;

import common.Constants;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * rating for each show
     */
    private Map<String, Double> ratings;
    /**
     * number of given ratings
     */
    private int countRatings;

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratings = new HashMap<>();
        countRatings = 0;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    public int getCountRatings() {
        return countRatings;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + " ratings="
                + ratings + '}';
    }

    /**
     * Check if a list of users contains a given username
     *
     * @param users list of users
     * @param username name of user
     * @return UserInputData object found, or null if not found
     */
    public static UserInputData containsUsername(final List<UserInputData> users,
                                                 final String username) {
        for (UserInputData user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Add a video in favorite list of current user
     *
     * @param videoTitle video which will be added in favorite list
     * @return String message
     */
    public String addVideoToFavorite(final String videoTitle) {
        if (history.containsKey(videoTitle)) {
            if (favoriteMovies.contains(videoTitle)) {
                return Constants.ERROR + videoTitle + " is already in favourite list";
            }
            favoriteMovies.add(videoTitle);
            return Constants.SUCCESS + videoTitle + " was added as favourite";
        } else {
            return Constants.ERROR + videoTitle + " is not seen";
        }

    }

    /**
     * Add a video to history of current user
     *
     * @param videoTitle title of video
     * @return String message
     */
    public String viewVideo(final String videoTitle) {
        if (history.containsKey(videoTitle)) {
            history.put(videoTitle, history.get(videoTitle) + 1);
        } else {
            history.put(videoTitle, 1);
        }
        return Constants.SUCCESS + videoTitle
                + " was viewed with total views of "
                + history.get(videoTitle);
    }

    /**
     * Add in ratings map new pair of video and rating if it was seen an not already rated
     *
     * @param videoTitle title of video
     * @param seasonNumber season of video (if it is movie, than season will be 0)
     * @param rating given rating for video
     * @return String message
     */
    public String giveRatingToVideo(final String videoTitle,
                                    final int seasonNumber,
                                    final Double rating) {
        if (history.containsKey(videoTitle)) {
            if (ratings.containsKey(videoTitle + " season " + seasonNumber)) {
                return Constants.ERROR + videoTitle + " has been already rated";
            }
            countRatings++;
            ratings.put(videoTitle + " season " + seasonNumber, rating);
            return Constants.SUCCESS + videoTitle + " was rated with " + rating + " by " + username;
        } else {
            return Constants.ERROR + videoTitle + " is not seen";
        }
    }

    /**
     * Sort a list of users by the number of ratings that has been given
     *
     * @param users list which will be sorted
     * @param sortType set the sort order
     */
    public static void sortUsersByRatings(final List<UserInputData> users, final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            users.sort((o1, o2) -> {
                if (o1.countRatings != o2.countRatings) {
                    return o1.countRatings - o2.countRatings;
                }
                return o1.getUsername().compareTo(o2.getUsername());
            });
        } else {
            users.sort((o1, o2) -> {
                if (o1.countRatings != o2.countRatings) {
                    return o2.countRatings - o1.countRatings;
                }
                return o2.username.compareTo(o1.username);
            });
        }
        users.removeIf((user) -> user.getCountRatings() == 0);
    }

    /**
     * Compute message for a command
     *
     * @param users list of users that will form the message
     * @param number max number of user that will appear in message
     * @return String message
     */
    public static String giveMessage(final List<UserInputData> users, final int number) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.QUERY_RESULT);
        stringBuilder.append("[");
        if (!users.isEmpty()) {
            stringBuilder.append(users.get(0).getUsername());
            for (int i = 1; i < Math.min(users.size(), number); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(users.get(i).getUsername());
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
