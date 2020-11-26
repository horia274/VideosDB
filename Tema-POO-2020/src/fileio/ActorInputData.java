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

    /**
     * Compute the rating of current actor using a list of shows
     *
     * @param shows list of shows
     */
    private void computeRating(final List<ShowInput> shows) {
        double sumRatings = 0;
        int countRatings = 0;
        for (ShowInput show : shows) {
            if (filmography.contains(show.getTitle()) && show.getRating() != 0) {
                sumRatings += show.getRating();
                countRatings++;
            }
        }
        if (countRatings > 0) {
            rating = sumRatings / countRatings;
        }
    }

    /**
     * Use the above method for a list of actors
     *
     * @param actors list of actors
     * @param shows list of shows
     */
    public static void computeRatingForArray(final List<ActorInputData> actors,
                                             final List<ShowInput> shows) {
        for (ActorInputData actor : actors) {
            actor.computeRating(shows);
        }
    }

    /**
     * Sort a list of actors by ratings
     *
     * @param actors list which will be sorted
     * @param sortType set the sort order
     */
    public static void sortActorsByRatings(final List<ActorInputData> actors,
                                           final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            actors.sort((o1, o2) -> {
                if (o1.getRating() != o2.getRating()) {
                    return Double.compare(o1.getRating(), o2.getRating());
                }
                return o1.name.compareTo(o2.name);
            });
        } else {
            actors.sort((o1, o2) -> {
                if (o1.getRating() != o2.getRating()) {
                    return Double.compare(o2.getRating(), o1.getRating());
                }
                return o2.name.compareTo(o1.name);
            });
        }
        actors.removeIf((actor) -> actor.getRating() == 0);
    }

    /**
     * Count the number of awards for the current actor
     */
    private void computeCountAwards() {
        if (countAwards == 0) {
            for (Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
                countAwards += award.getValue();
            }
        }
    }

    /**
     * Compute actors who earned all award from a given list of awards
     * @param actors list of actors to be checked
     * @param awards list of awards
     * @return List Object of actors
     */
    public static List<ActorInputData> getActorsWithAwards(final List<ActorInputData> actors,
                                                           final List<ActorsAwards> awards) {
        List<ActorInputData> actorsWithAwards = new ArrayList<>();
        for (ActorInputData actor : actors) {
            if (actor.getAwards().keySet().containsAll(awards)) {
                actor.computeCountAwards();
                actorsWithAwards.add(actor);
            }
        }
        return actorsWithAwards;
    }

    /**
     * Sort actors by number of awards
     * second criteria: name of actor
     *
     * @param actors list of actors who will be sorted
     * @param sortType set the sort order
     */
    public static void sortActorsByAwards(final List<ActorInputData> actors,
                                          final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            actors.sort((o1, o2) -> {
                if (o1.countAwards != o2.countAwards) {
                    return o1.countAwards - o2.countAwards;
                }
                return o1.name.compareTo(o2.name);
            });
        } else {
            actors.sort((o1, o2) -> {
                if (o1.countAwards != o2.countAwards) {
                    return o2.countAwards - o1.countAwards;
                }
                return o2.name.compareTo(o1.name);
            });
        }
    }

    /**
     * Check if the actor's description contains all words from a given text
     *
     * @param description list of String that must contains the actor's description
     * @return boolean value
     */
    private boolean checkDescription(final List<String> description) {
        String lowerDescription = careerDescription.toLowerCase();
        String[] wordsFromDescription = lowerDescription.split("\\s+|,|\\.|-");
        List<String> words = new ArrayList<>();
        Collections.addAll(words, wordsFromDescription);
        for (String word : description) {
            if (!words.contains(word.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Apply the above method for a list of actors
     *
     * @param actors list of actors
     * @param description list of String that must contains the actor's description
     * @return List with actors that contains all words from the given text
     */
    public static List<ActorInputData> getActorsWithDescription(final List<ActorInputData> actors,
                                                                final List<String> description) {
        List<ActorInputData> actorsWithDescription = new ArrayList<>();
        for (ActorInputData actor : actors) {
            if (actor.checkDescription(description)) {
                actorsWithDescription.add(actor);
            }
        }
        return actorsWithDescription;
    }

    /**
     * Sort actors by name
     *
     * @param actors list of actors
     * @param sortType set the sort order
     */
    public static void sortActorsByName(final List<ActorInputData> actors,
                                        final String sortType) {
        if (sortType.equals(Constants.ASC)) {
            actors.sort((o1, o2) -> o1.name.compareTo(o2.name));
        } else {
            actors.sort((o1, o2) -> o2.name.compareTo(o1.name));
        }
    }

    /**
     * /**
     * Compute message for a query
     *
     * @param actors list of actors who will form the message
     * @param number max number of actors who will appear in message
     * @return String message
     */
    public static String giveMessage(final List<ActorInputData> actors,
                                     final int number) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.QUERY_RESULT);
        stringBuilder.append("[");
        if (!actors.isEmpty()) {
            stringBuilder.append(actors.get(0).getName());
            for (int i = 1; i < Math.min(actors.size(), number); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(actors.get(i).getName());
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
