//Mare Sorin-Alexandru

package MineSweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MSGameController implements MouseListener
{
    //current game
    private Game game;
    //if game is over, the board is locked (and Timer thread is stopped)
    private boolean lockGrid = false;
    //podium position of the current won game
    private int podiumPosition;
    //start time of the board
    private long startTime= System.currentTimeMillis();
    //handles timer (sends accumulated time to be displayed)
    private TimerUpdater timerUpdater = new TimerUpdater();

    //TImer is handled on a new thread
    class TimerUpdater extends Thread
    {
        @Override
        public void run()
        {
            while (!lockGrid)
            {
                game.getAccumulatedTime().setText(String.valueOf((System.currentTimeMillis()-startTime)/1000));
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MSGameController(Game game)
    {
        this.game=game;
        timerUpdater.start();
    }

    //if the game is won this is executed
    private void onWin()
    {
        //lock the board
        lockGrid = true;

        //update scoreboard
        podiumPosition = TopScores.updateTopScores((System.currentTimeMillis()-startTime)/1000,game.getDifficulty());
        System.out.println(podiumPosition);
        System.out.println(game.getDifficulty());
        switch (podiumPosition)
        {
            case 1:
                game.getEndOfGameMessage().setForeground(Color.MAGENTA);
                game.getEndOfGameMessage().setText("Legend! You are #1");
                break;
            case 2:
                game.getEndOfGameMessage().setForeground(new Color(0,128,0));
                game.getEndOfGameMessage().setText("Well Done! You have the 2nd highest score");
                break;
            case 3:
                game.getEndOfGameMessage().setForeground(new Color(0,128,0));
                game.getEndOfGameMessage().setText("Well Done! You have the 3rd highest score");
                break;
            case 4:
                game.getEndOfGameMessage().setForeground(new Color(0,128,0));
                game.getEndOfGameMessage().setText("Well Done! You have the 4th highest score");
                break;
            case 5:
                game.getEndOfGameMessage().setForeground(new Color(0,128,0));
                game.getEndOfGameMessage().setText("Well Done! You have the 5th highest score");
                break;
            default:
                game.getEndOfGameMessage().setForeground(new Color(0,128,0));
                game.getEndOfGameMessage().setText("Well Done!");
                break;
        }

        //update the score area
        game.getScoreArea().setText(ScoreAreaHandler.handleScoreArea());
    }

    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e)
    {
        //if game is not over
        if(!lockGrid)
        {
            //left-click
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                //if not flagged discover
                if (!game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].isFlagged())
                    game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].discover();

                //if discovered Tile is not surrounded by any bombs (the whole area with no bombs around is discovered)
                if(game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].getNumberOfBombsAround()==0
                        && !game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].isFlagged())
                {
                    game.getGrid().discoverUnMinedArea(e.getY() / 25,e.getX() / 25);
                    //bomb counter is updated
                    game.getNumberOfRemainingBombs().setText(String.valueOf(game.getGrid().getBombs() - game.getGrid().numberOfFlaggedTiles()));
                }

                //if discovered Tile is a bomb (game over)
                if(game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].isBomb() && !game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].isFlagged())
                {
                    //bomb explodes
                    game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].explode();

                    //discover all Bomb Tiles
                    for(int i = 0; i < game.getGrid().getXSize(); i++)
                        for(int j = 0; j < game.getGrid().getYSize(); j++)
                            if(!game.getGrid().getTileGrid()[i][j].isFlagged() && game.getGrid().getTileGrid()[i][j].isBomb())
                                game.getGrid().getTileGrid()[i][j].discover();

                    //lock the board
                    lockGrid = true;

                    //loss message
                    game.getEndOfGameMessage().setForeground(Color.RED);
                    game.getEndOfGameMessage().setText("Game Over");
                }

                //check for winning : if there are as many discovered (non - bombed) tiles as there are tiles - bombs
                if(game.getGrid().getNumberOfDiscoveredTiles()
                        == game.getGrid().getXSize() * game.getGrid().getYSize() - game.getGrid().getBombs())
                {
                    //flag all bomb tiles and discover the rest
                    for(int i = 0; i < game.getGrid().getXSize(); i++)
                        for(int j = 0; j < game.getGrid().getYSize(); j++)
                            if(game.getGrid().getTileGrid()[i][j].isBomb())
                                game.getGrid().getTileGrid()[i][j].flag();

                    //game is won
                    onWin();
                }

                //repaint
                game.repaint();
            }

            //right-click
            if(e.getButton() == MouseEvent.BUTTON3)
            {
                //if tile is flagged then un-flag it
                if(game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].isFlagged())
                {
                    game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].unFlag();
                    //bomb counter is updated
                    game.getNumberOfRemainingBombs().setText(String.valueOf(game.getGrid().getBombs() - game.getGrid().numberOfFlaggedTiles()));
                }
                //if tile is discovered flag it
                else if(!game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].isDiscovered())
                {
                    game.getGrid().getTileGrid()[e.getY() / 25][e.getX() / 25].flag();
                    //bomb counter is updated
                    game.getNumberOfRemainingBombs().setText(String.valueOf(game.getGrid().getBombs() - game.getGrid().numberOfFlaggedTiles()));

                    //check for winning : if all bomb tiles are flagged and no other tiles are flagged
                    boolean Win = true;
                    if(game.getGrid().numberOfFlaggedTiles() == game.getGrid().getBombs())
                        for(int i = 0; i < game.getGrid().getXSize(); i++)
                            for(int j = 0; j < game.getGrid().getYSize(); j++)
                            {
                                if(game.getGrid().getTileGrid()[i][j].isFlagged()
                                        && !game.getGrid().getTileGrid()[i][j].isBomb())
                                {
                                    Win = false;
                                }
                            }
                    else
                        Win = false;

                    if(Win)
                    {
                        //discover all non-bomb tiles
                        for(int i = 0; i < game.getGrid().getXSize(); i++)
                            for(int j = 0; j < game.getGrid().getYSize(); j++)
                                if(!game.getGrid().getTileGrid()[i][j].isBomb())
                                    game.getGrid().getTileGrid()[i][j].discover();

                        onWin();
                    }
                }

                game.repaint();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}

    //lock grid (stops thread) - used when new game buttons are pressed
    public void lockGrid()
    {
        lockGrid=true;
    }
}
