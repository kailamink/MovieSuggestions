import Controller.MovieController;
import View.MovieFrame;

import java.io.IOException;

public class Main {
    public static void main(String [] args) throws IOException {
        MovieFrame movieFrame = new MovieFrame(new MovieController());
        movieFrame.setVisible(true);
    }
}
