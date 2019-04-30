import Controller.MovieController;
import View.MovieFrame;

import java.io.IOException;

public class Main {
    public static void main(String [] args) throws IOException {
        MovieController controller = new MovieController();
//        controller.searchForSimilarMovies("anastasia");
        MovieFrame movieFrame = new MovieFrame(controller);
        movieFrame.setVisible(true);
    }
}
