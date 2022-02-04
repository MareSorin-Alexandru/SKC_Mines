//Mare Sorin-Alexandru

import java.awt.*;

public class Tile {
    //tiles start out undiscovered
    private boolean isDiscovered = false;
    //tiles start as un-flagged
    private boolean isFlagged = false;
    //whether current Tile is a bomb
    private boolean isBomb;
    //numberOfBombsAround starts off as 0
    private int numberOfBombsAround = 0;
    //used in automatically discovering areas with no bombs if one Tile in that contiguous group is discovered
    private boolean isExpanded = false;
    //whether this bomb is the one that ended the game
    private boolean hasExploded = false;

    //constructor that sets isBomb
    public Tile(boolean isBomb) {
        this.isBomb = isBomb;
    }

    //return whether current tile is a bomb tile or not
    public boolean isBomb() {
        return isBomb;
    }

    //increase value of numberOfBombsAround by 1 of the current tile
    public void increaseNumberOfBombsAround() {
        numberOfBombsAround++;
    }

    //return the number of bombs around the current tile
    public int getNumberOfBombsAround() {
        return numberOfBombsAround;
    }

    //discover current file
    public void discover() {
        isDiscovered = true;
        isFlagged = false;
    }

    //return whether current Tile is discovered
    public boolean isDiscovered() {
        return isDiscovered;
    }

    //return whether current Tile is flagged
    public boolean isFlagged() {
        return isFlagged;
    }

    //flag current tile
    public void flag() {
        isFlagged = true;
    }

    //un-flag current tile
    public void unFlag() {
        isFlagged = false;
    }

    //return whether current tile has been expanded
    public boolean isExpanded() {
        return isExpanded;
    }

    //current tile is marked as expanded
    public void expand() {
        isExpanded = true;
    }

    //explode current tile
    public void explode()
    {
        this.hasExploded = true;
    }

    //paintComponent for tiles
    public void paintComponent(Graphics g,int i, int j)
    {
        Font msFont = new Font("MSFont",Font.BOLD,15);
        g.setFont(msFont);

        //draw tile outline
        g.setColor(new Color(150,150,150));
        g.drawRect(25 * j,25 * i,25,25);
        //if Tile is discovered
        if(isDiscovered())
        {
            //basic discovered tile (0 bombs around)
            g.setColor(new Color(238,238,238));
            g.fillRect(25 * j + 1, 25 * i + 1, 23,23);

            //if TIle is a bomb
            if(isBomb())
            {
                if(hasExploded)
                {
                    g.setColor(Color.RED);
                    g.fillRect(25 * j + 1, 25 * i + 1, 23,23);
                }

                g.setColor(Color.BLACK);
                g.fillOval(25 * j + 3, 25 * i + 3, 19,19);
                //diagonal 1
                g.drawLine(25 * j + 3,25 * i + 3,25 * j + 22,25 * i + 22);
                //diagonal 2
                g.drawLine(25 * j + 22,25 * i +3,25 * j + 3, 25 * i + 22);
                //left-right
                g.fillRect(25 * j + 2, 25 * i + 11, 22,3);
                //up-down
                g.fillRect(25 * j + 11, 25 * i + 2, 3,22);
                //shine
                g.setColor(new Color(238,238,238));
                g.fillRect(25 * j + 9, 25 * i + 9, 2,2);

            }
            //tiles that have n number of bombs around them
            else
            {
                if(getNumberOfBombsAround() == 1)
                {
                    char a[] = {'1'};
                    g.setColor(Color.BLUE);
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 2)
                {
                    char a[] = {'2'};
                    g.setColor(new Color(0,150,0));
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 3)
                {
                    char a[] = {'3'};
                    g.setColor(Color.RED);
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 4)
                {
                    char a[] = {'4'};
                    g.setColor(new Color(25,0,150));
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 5)
                {
                    char a[] = {'5'};
                    g.setColor(new Color(150,0,0));
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 6)
                {
                    char a[] = {'6'};
                    g.setColor(new Color(0,150,150));
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 7)
                {
                    char a[] = {'7'};
                    g.setColor(Color.BLACK);
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
                if(getNumberOfBombsAround() == 8)
                {
                    char a[] = {'8'};
                    g.setColor(Color.GRAY);
                    g.drawChars(a,0,1,25 * j + 9,25 * i + 18);
                }
            }
        }
        //if Tile is not discovered
        else
        {
            //if tile is not flagged
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(25 * j + 1, 25 * i + 1, 23,23);

            //if Tile is flagged
            if(isFlagged())
            {
                g.setColor(Color.BLACK);
                //flag pole
                g.fillRect(25 * j + 10, 25 * i + 2,3,20);
                //flag base
                g.fillRect(25 * j + 5, 25 * i + 20,15,2);
                g.fillRect(25 * j + 8, 25 * i + 18,10,2);
                //flag
                g.setColor(Color.RED);
                g.fillRect(25 * j + 13, 25 * i + 4, 7,6);
            }
        }
    }
}
