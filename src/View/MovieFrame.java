package View;

import javax.swing.*;
import java.awt.*;

public class MovieFrame extends JFrame
{
    public MovieFrame()
    {
        setTitle("Movie Suggestions");
        setSize(600,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());

        MovieComponent movie = new MovieComponent();
        root.add(movie, BorderLayout.CENTER);

        InputFrame inputy = new InputFrame();
        root.add(inputy.createControlPanel(movie), BorderLayout.NORTH);
        setContentPane(root);

    }
}
