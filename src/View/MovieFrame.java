package View;

import Controller.MovieController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MovieFrame extends JFrame
{
    public MovieFrame(MovieController movieController)
    {
        setTitle("Movie Suggestions");
        setSize(600,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());

        MovieComponent movie = new MovieComponent();
        root.add(movie, BorderLayout.CENTER);

        InputFrame inputy = new InputFrame();
        root.add(inputy.createControlPanel(movieController, movie), BorderLayout.NORTH);
        setContentPane(root);
    }
}
