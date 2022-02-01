//1801713
//Mare Sorin-Alexandru

package MineSweeper;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame
{
    //difficulties
    //EASY = 0  size = 9x9  bombs = 10
    public static final int EASY = 0;
    //MEDIUM = 1   size = 15x16  bombs = 40
    public static final int MEDIUM = 1;
    //HARD = 2  size = 16x30  bombs = 99
    public static final int HARD = 2;

    //spacers
    public static final String easySpacer="----------------------------------------------------------------------";
    public static final String mediumSpacer="-----------------------------------------------";
    public static final String hardSpacer="----";

    //difficulty
    private int difficulty;
    //game board
    private Grid grid;
    //BoardPanel
    private GridPanel gridPanel;
    //mouseListener for the boardPanel
    private MSGameController msGameController;
    //keyListener
    private MSKeyListener msKeyListener;

    //for buttons
    private JPanel buttonPanel;
    //for displaying info on the current game
    private JPanel gameInfoPanel;
    //for displaying high scores
    private JPanel highScoresPanel;

    //buttons for launching new games / help with controls / resetting top scores
    JButton newEasyGame = new JButton("Easy");
    JButton newMediumGame = new JButton("Medium");
    JButton newHardGame = new JButton("Hard");
    JButton controls = new JButton("Controls");
    JButton resetTS = new JButton("Reset TS");

    //labels for current number of bombs, time elapsed in current game, for end of game message and for spacing
    private JLabel accumulatedTime = new JLabel("0");
    private JLabel numberOfRemainingBombs = new JLabel("10");
    private JLabel endOfGameMessage = new JLabel("");
    private JLabel spacerLabel = new JLabel(easySpacer);

    //ScoreArea Holds Top Scores
    private JTextArea scoreArea = new JTextArea(6,5);

    public Game()
    {
        //title, defaultCloseOperation, size, setResizable, layout
        setTitle("MineSweeper by 1801713 / Mare Sorin-Alexandru");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 680);
        setResizable(false);
        setLayout(new BorderLayout());

        //###
        difficulty = 0;
        grid = new Grid(9,9,10);
        //###

        //panels are initialised

        //wrapper panel (holds newGamePanel and gameInfoPanel)
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        gameInfoPanel = new JPanel();
        //holds GridPanel and the spacer panel
        JPanel wrapperPanel2 = new JPanel(new BorderLayout());
        //boardPanel is initialised
        gridPanel = new GridPanel(this);
        //holds end of game messages
        JPanel wrapperPanel3 = new JPanel();
        //holds the highScorePanel and wrapperPanel3
        JPanel wrapperPanel4 = new JPanel(new BorderLayout());
        //highScores will display all top scores
        highScoresPanel = new JPanel(new  BorderLayout());


        //controller is initialised
        msGameController = new MSGameController(this);
        //controller is added to the boardPanel
        gridPanel.addMouseListener(msGameController);

        //keyListener is initialised
        msKeyListener = new MSKeyListener(this);
        //keylistener is added
        gridPanel.addKeyListener(msKeyListener);

        //newGamePanel
        //buttons are already declared
        //button handler
        MenuButtonHandler menuButtonHandler = new MenuButtonHandler(this);

        //add action listener to the button
        newEasyGame.addActionListener(menuButtonHandler);
        newMediumGame.addActionListener(menuButtonHandler);
        newHardGame.addActionListener(menuButtonHandler);
        controls.addActionListener(menuButtonHandler);
        resetTS.addActionListener(menuButtonHandler);

        //add buttons to Panel
        buttonPanel.add(newEasyGame);
        buttonPanel.add(newMediumGame);
        buttonPanel.add(newHardGame);
        buttonPanel.add(controls);
        buttonPanel.add(resetTS);

        //gameInfoPanel
        //JLabels with Information about the current game
        //time elapsed
        JLabel accumulatedTimeAux = new JLabel("Elapsed Time: ");
        //number of bombs remaining
        JLabel numberOfRemainingBombsAux = new JLabel("Remaining Bombs: ");

        //Items are added to the panel
        gameInfoPanel.add(accumulatedTimeAux);
        gameInfoPanel.add(accumulatedTime);
        gameInfoPanel.add(numberOfRemainingBombsAux);
        gameInfoPanel.add(numberOfRemainingBombs);

        //highScoresPanel
        scoreArea.setBackground(new Color(238,238,238));
        scoreArea.setEditable(false);
        scoreArea.setText(ScoreAreaHandler.handleScoreArea());

        //Items are added to the highScoresPanel
        highScoresPanel.add(scoreArea,BorderLayout.CENTER);

        //spacerLabel is painted the background color so its not observable
        spacerLabel.setForeground(new Color(238,238,238));

        //all panels are added to the frame
        //via wrappers
        wrapperPanel.add(buttonPanel, BorderLayout.NORTH);
        wrapperPanel.add(gameInfoPanel,BorderLayout.CENTER);

        wrapperPanel2.add(spacerLabel,BorderLayout.WEST);
        wrapperPanel2.add(gridPanel,BorderLayout.CENTER);

        wrapperPanel3.add(endOfGameMessage);

        wrapperPanel4.add(wrapperPanel3,BorderLayout.NORTH);
        wrapperPanel4.add(highScoresPanel,BorderLayout.CENTER);

        add(wrapperPanel,BorderLayout.NORTH);
        add(wrapperPanel2,BorderLayout.CENTER);
        add(wrapperPanel4,BorderLayout.SOUTH);


        //grabs focus away from the buttons so KeyListener can work
        newEasyGame.setFocusable(false);
        newMediumGame.setFocusable(false);
        newHardGame.setFocusable(false);
        controls.setFocusable(false);
        resetTS.setFocusable(false);

        gridPanel.grabFocus();
        gridPanel.requestFocusInWindow();
        gridPanel.setFocusable(true);

        //visibility is set to true
        setVisible(true);
    }

    //returns difficulty of the current game
    public int getDifficulty()
    {
        return difficulty;
    }

    //returns grid used by the current game
    public Grid getGrid()
    {
        return grid;
    }

    //set the current game difficulty
    public void setDifficulty(int difficulty)
    {
        this.difficulty=difficulty;
    }

    //set the current game grid
    public void setGrid(Grid grid)
    {
        this.grid=grid;
    }

    //set current game controller
    public void setMsGameController(MSGameController msGameController)
    {
        this.msGameController = msGameController;
    }

    //get current game controller
    public MSGameController getMsGameController()
    {
        return msGameController;
    }

    //get current grid panel
    public GridPanel getGridPanel()
    {
        return gridPanel;
    }

    //get the number of current remaining bombs (bombs - flags)
    public JLabel getNumberOfRemainingBombs()
    {
        return numberOfRemainingBombs;
    }

    //return the current elapsed time label
    public JLabel getAccumulatedTime()
    {
        return accumulatedTime;
    }

    //return end of game message label
    public JLabel getEndOfGameMessage()
    {
        return endOfGameMessage;
    }

    //return spacer label
    public JLabel getSpacerLabel()
    {
        return  spacerLabel;
    }

    //return scoreArea
    public JTextArea getScoreArea()
    {
        return scoreArea;
    }

}
