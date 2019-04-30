package View;

import Controller.MovieController;

import javax.swing.*;

public class InputFrame extends JComponent
{
    public JPanel createControlPanel(MovieController movieController, MovieComponent movie)
    {
        JPanel panel = new JPanel();
        JLabel movieLabel = new JLabel("Movie Title: ");
        JTextField movieText = new JTextField("", 20);
        JButton button = new JButton("Search");
        button.addActionListener(e -> {
            movie.displayMovieTitle(movieController, movieText.getText());
        });
        panel.add(movieLabel);
        panel.add(movieText);
        panel.add(button);
        return panel;
    }
}
