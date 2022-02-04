//Mare Sorin-Alexandru

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel
{
    //current game
    private Game game;

    //constructor
    public GridPanel(Game game)
    {
        this.game=game;
        //gridPanel size is set to fit the grid
        this.setSize(25 * this.game.getGrid().getYSize(),25 * this.game.getGrid().getXSize());
    }

    //paint the GridPanel
    @Override
    public void paintComponent(Graphics g)
    {
        this.game.getGrid().paintComponent(g);
    }

    //debugging - prints the size of the panel to the Console
    public void printSizeToConsole()
    {
        System.out.println(this.getSize());
    }
}
