package View;

import Controller.MovieController;
import Model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieComponent extends JComponent
{
    List<Movie> _movies = null;
    Graphics _graphics;
    int _tries = 0;

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        if(_movies != null)
        {
            int index = 10;
            for(Movie movie : _movies)
            {
                graphics.setColor(Color.BLACK);
                graphics.drawString(movie.getOriginalTitle(), 10,10 + index);
                index += 15;
            }
        }
        else if(_tries > 0)
        {
            graphics.setColor(Color.BLACK);
            graphics.drawString(new String("404 -- Movie Not Found"),10,10);
        }
        _graphics = graphics.create();

    }

    public void displayMovieTitle(MovieController movieController, String movieTitle)
    {
        ++_tries;
        _movies = _movies == null ? _movies : null;
        _movies =  movieController.searchForSimilarMovies(movieTitle);
        paintComponent(_graphics);
    }
}
