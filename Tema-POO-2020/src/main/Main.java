package main;

import actor.ActorsAwards;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;

import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import fileio.ActionInputData;
import fileio.UserInputData;
import fileio.ShowInput;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ActorInputData;

import org.json.simple.JSONArray;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        List<ActorInputData> actors = input.getActors();
        List<MovieInputData> movies = input.getMovies();
        List<SerialInputData> serials = input.getSerials();
        List<UserInputData> users = input.getUsers();
        List<ActionInputData> commands = input.getCommands();
        String message = null;

        for (ActionInputData action : commands) {

            if (action.getActionType().equals(Constants.COMMAND)) {
                UserInputData user = UserInputData.containsUsername(users, action.getUsername());
                if (user == null) {
                    message = Constants.NO_USER;
                    continue;
                }

                if (action.getType().equals(Constants.FAVORITE)) {
                    message = user.addVideoToFavorite(action.getTitle());
                }

                if (action.getType().equals(Constants.VIEW)) {
                    message = user.viewVideo(action.getTitle());
                }

                if (action.getType().equals(Constants.RATING)) {
                    message = user.giveRatingToVideo(action.getTitle(), action.getSeasonNumber(), action.getGrade());
                }
            }

            if (action.getActionType().equals(Constants.QUERY)) {
                if (action.getObjectType().equals(Constants.MOVIES)
                        || action.getObjectType().equals(Constants.SHOWS)) {
                    List<String> actionYears = action.getFilters().get(0);
                    List<String> actionGenres = action.getFilters().get(1);
                    List<ShowInput> filteredMovies;

                    if (action.getObjectType().equals(Constants.MOVIES)) {
                        filteredMovies = MovieInputData.computeShowsByYearAndGenres(movies, actionYears, actionGenres);
                    } else {
                        filteredMovies = SerialInputData.computeShowsByYearAndGenres(serials, actionYears, actionGenres);
                    }

                    if (action.getCriteria().equals(Constants.LONGEST)) {
                        ShowInput.sortVideosByDuration(filteredMovies, action.getSortType());
                    }

                    if (action.getCriteria().equals(Constants.FAVORITE)) {
                        ShowInput.computeCountFavoriteForArray(filteredMovies, users);
                        ShowInput.sortVideosByFavorites(filteredMovies, action.getSortType());
                    }

                    if (action.getCriteria().equals(Constants.MOST_VIEWED)) {
                        ShowInput.computeCountViewsForArray(filteredMovies, users);
                        MovieInputData.sortVideosByViews(filteredMovies, action.getSortType());
                    }

                    if (action.getCriteria().equals(Constants.RATINGS)) {
                        ShowInput.computeRatingForArray(filteredMovies, users);
                        ShowInput.sortVideosByRating(filteredMovies, action.getSortType());
                    }

                    message = MovieInputData.giveMessage(filteredMovies, action.getNumber());
                }

                if (action.getObjectType().equals(Constants.USERS)) {
                    List<UserInputData> sortedUsers = new ArrayList<>(users);
                    UserInputData.sortUsersByRatings(sortedUsers, action.getSortType());
                    message = UserInputData.giveMessage(sortedUsers, action.getNumber());
                }
            }
            arrayResult.add(fileWriter.writeFile(action.getActionId(), message));
        }
        fileWriter.closeJSON(arrayResult);
    }
}
