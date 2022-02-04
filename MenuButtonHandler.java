//Mare Sorin-Alexandru

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuButtonHandler implements ActionListener
{
    //game the menubuttonhandler is applied to
    private Game game;

    public MenuButtonHandler(Game game)
    {
        this.game=game;
    }

    //appropriate action per button
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //new game
        //if the new easy game button is pressed
        if(e.getSource() == game.newEasyGame)
            GameSetup.gameSetupAux(game,Game.EASY);
        //if the new medium game button is pressed
        if(e.getSource() == game.newMediumGame)
            GameSetup.gameSetupAux(game,Game.MEDIUM);
        //if the new hard game button is pressed
        if(e.getSource() == game.newHardGame)
            GameSetup.gameSetupAux(game,Game.HARD);

        //controls are displayed
        if (e.getSource() == game.controls)
        {
            JOptionPane controlPane = new JOptionPane();
            controlPane.showMessageDialog(null,"E = new Easy Game\nM = new Medium Game\nH = new Hard Game\nR = new same difficulty Game\nLC = discover\nRC = flag / de-flag","Controls",JOptionPane.INFORMATION_MESSAGE);
        }

        //top scores are reset
        if(e.getSource() == game.resetTS)
        {
            TopScores.resetTopScores();
            game.getScoreArea().setText(ScoreAreaHandler.handleScoreArea());
        }
    }
}
