//Mare Sorin-Alexandru

package MineSweeper;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MSKeyListener implements KeyListener
{
    //current game
    private Game game;

    public MSKeyListener(Game game)
    {
        this.game=game;
    }

    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e)
    {
        //new easy game
        if(e.getKeyCode() == KeyEvent.VK_E)
            GameSetup.gameSetupAux(game,Game.EASY);
        //mew medium game
        if(e.getKeyCode() == KeyEvent.VK_M)
            GameSetup.gameSetupAux(game,Game.MEDIUM);
        //new hard game
        if(e.getKeyCode() == KeyEvent.VK_H)
            GameSetup.gameSetupAux(game,Game.HARD);
        //new game with the same difficulty as the current one
        if(e.getKeyCode() == KeyEvent.VK_R)
            GameSetup.gameSetupAux(game,game.getDifficulty());
    }

    @Override
    public void keyReleased(KeyEvent e){}
}
