public abstract class GameSetup
{
    public static void gameSetupAux(Game game, int difficulty)
    {
        //TimeUpdater from the previous game is stopped
        game.getMsGameController().lockGrid();
        //new game is being set up (depending on the difficulty)
        game.setDifficulty(difficulty);

        if(difficulty == Game.EASY)
        {
            game.getNumberOfRemainingBombs().setText(String.valueOf(10));
            game.setGrid(new Grid(9,9,10));
            game.getSpacerLabel().setText(Game.easySpacer);
        }
        else if(difficulty == Game.MEDIUM)
        {
            game.getNumberOfRemainingBombs().setText(String.valueOf(40));
            game.setGrid(new Grid(15,16,40));
            game.getSpacerLabel().setText(Game.mediumSpacer);
        }
        else if(difficulty == Game.HARD)
        {
            game.getNumberOfRemainingBombs().setText(String.valueOf(99));
            game.setGrid(new Grid(16,30,99));
            game.getSpacerLabel().setText(Game.hardSpacer);
        }

        game.setMsGameController(new MSGameController(game));
        game.getGridPanel().addMouseListener(game.getMsGameController());

        //repaints / resets
        game.getEndOfGameMessage().setText("");
        game.repaint();
    }
}
