//1801713
//Mare Sorin-Alexandru

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public class Grid
{
    //number of tiles / column
    private int xSize;
    //number of tiles / row
    private int ySize;
    //number of bombs in the whole grid
    private int bombs;

    //the grid is composed of Tiles
    private Tile[][] tileGrid;

    //generates a new randomised game grid
    public Grid(int xSize,int ySize,int bombs)
    {
        this.xSize=xSize;
        this.ySize=ySize;
        this.bombs=bombs;
        tileGrid = new Tile[xSize][ySize];

        //Integers from 0 to the number of tiles -1 are put in a vector
        Vector<Integer> integerVector = new Vector<Integer>(xSize*ySize);
        for(int i = 0; i< integerVector.capacity(); i++)
            integerVector.add(i,i);
        //we shuffle the vector and use it to fill out grid (with bombs) in a random way
        Collections.shuffle(integerVector);
        //populate the grid (with bombs)
        {
            int count = 0;
            for (int i = 0; i < xSize; i++)
                for (int j = 0; j < ySize; j++)
                    if (integerVector.elementAt(count++) >= bombs)
                        tileGrid[i][j] = new Tile(false);
                    else
                        tileGrid[i][j] = new Tile(true);
        }
        //update bomb counters for each tile of the grid
        {
            for (int i = 0; i < xSize; i++)
                for (int j = 0; j < ySize; j++)
                {
                    if (tileGrid[i][j].isBomb())
                    {
                        //update the bomb counters
                        //corners
                        // TOP LEFT
                        if (i == 0 && j == 0)
                        {
                            for (int i1 = i; i1 <= i + 1; i1++)
                                for (int j1 = j; j1 <= j + 1; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        // TOP RIGHT
                        else if (i == 0 && j == ySize - 1)
                        {
                            for (int i1 = i; i1 <= i + 1; i1++)
                                for (int j1 = j - 1; j1 <= j; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        // BOTTOM LEFT
                        else if (i == xSize - 1 && j == 0)
                        {
                            for (int i1 = i - 1; i1 <= i; i1++)
                                for (int j1 = j; j1 <= j + 1; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        // BOTTOM RIGHT
                        else if (i == xSize - 1 && j == ySize - 1)
                        {
                            for (int i1 = i - 1; i1 <= i; i1++)
                                for (int j1 = j - 1; j1 <= j; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        //sides excluding corners
                        //TOP
                        else if (i == 0)
                        {
                            for (int i1 = i; i1 <= i + 1; i1++)
                                for (int j1 = j - 1; j1 <= j + 1; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        //BOTTOM
                        else if (i == xSize - 1)
                        {
                            for (int i1 = i - 1; i1 <= i; i1++)
                                for (int j1 = j - 1; j1 <= j + 1; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        //LEFT
                        else if (j == 0)
                        {
                            for (int i1 = i - 1; i1 <= i + 1; i1++)
                                for (int j1 = j; j1 <= j + 1; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        //RIGHT
                        else if (j == ySize - 1)
                        {
                            for (int i1 = i - 1; i1 <= i + 1; i1++)
                                for (int j1 = j - 1; j1 <= j; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                        //middle
                        else
                        {
                            for (int i1 = i - 1; i1 <= i + 1; i1++)
                                for (int j1 = j - 1; j1 <= j + 1; j1++)
                                    tileGrid[i1][j1].increaseNumberOfBombsAround();
                        }
                    }
                }
        }
    }

    //returns current grid
    public Tile[][] getTileGrid()
    {
        return tileGrid;
    }

    //returns the number of rows of the grid
    public int getXSize()
    {
        return xSize;
    }

    //returns the number of columns of the grid
    public int getYSize()
    {
        return ySize;
    }

    //returns the total number of bombs on the grid
    public int getBombs()
    {
        return bombs;
    }

    //discovers whole un-mined area if one tile of it is discovered
    public void discoverUnMinedArea(int i, int j)
    {
        tileGrid[i][j].expand();
        // TOP LEFT
        if (i == 0 && j == 0)
        {
            for (int i1 = i; i1 <= i + 1; i1++)
                for (int j1 = j; j1 <= j + 1; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i; i1 <= i + 1; i1++)
                for (int j1 = j; j1 <= j + 1; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        // TOP RIGHT
        else if (i == 0 && j == ySize - 1)
        {
            for (int i1 = i; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        // BOTTOM LEFT
        else if (i == xSize - 1 && j == 0)
        {
            for (int i1 = i - 1; i1 <= i; i1++)
                for (int j1 = j; j1 <= j + 1; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i - 1; i1 <= i; i1++)
                for (int j1 = j; j1 <= j + 1; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        // BOTTOM RIGHT
        else if (i == xSize - 1 && j == ySize - 1)
        {
            for (int i1 = i - 1; i1 <= i; i1++)
                for (int j1 = j - 1; j1 <= j; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i - 1; i1 <= i; i1++)
                for (int j1 = j - 1; j1 <= j; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        //sides excluding corners
        //TOP
        else if (i == 0)
        {
            for (int i1 = i; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j + 1; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j + 1; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        //BOTTOM
        else if (i == xSize - 1)
        {
            for (int i1 = i - 1; i1 <= i; i1++)
                for (int j1 = j - 1; j1 <= j + 1; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i - 1; i1 <= i; i1++)
                for (int j1 = j - 1; j1 <= j + 1; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        //LEFT
        else if (j == 0)
        {
            for (int i1 = i - 1; i1 <= i + 1; i1++)
                for (int j1 = j; j1 <= j + 1; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i - 1; i1 <= i + 1; i1++)
                for (int j1 = j; j1 <= j + 1; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        //RIGHT
        else if (j == ySize - 1)
        {
            for (int i1 = i - 1; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i - 1; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
        //middle
        else
        {
            for (int i1 = i - 1; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j + 1; j1++)
                    tileGrid[i1][j1].discover();
            for (int i1 = i - 1; i1 <= i + 1; i1++)
                for (int j1 = j - 1; j1 <= j + 1; j1++)
                    if(tileGrid[i1][j1].getNumberOfBombsAround() == 0 && !tileGrid[i1][j1].isExpanded())
                        discoverUnMinedArea(i1,j1);
        }
    }

    //returns number of discovered Tiles on current grid
    public int getNumberOfDiscoveredTiles()
    {
        int numberOfDiscoveredTiles = 0;
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                if(tileGrid[i][j].isDiscovered())
                    numberOfDiscoveredTiles++;
        return numberOfDiscoveredTiles;
    }

    //returns number of flagged tiles
    public int numberOfFlaggedTiles()
    {
        int numberOfFlaggedTiles = 0;
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                if(tileGrid[i][j].isFlagged())
                    numberOfFlaggedTiles++;
        return numberOfFlaggedTiles;
    }

    //paint component for grids - for each tile, paint the tile
    public void paintComponent(Graphics g)
    {
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                tileGrid[i][j].paintComponent(g,i,j);
    }

    //debugging - prints grid (bombs and number of bombs around) to the console
    public void printGridToConsole()
    {
        for (int i = 0; i < xSize; i++)
            for (int j = 0; j < ySize; j++)
            {
                if (tileGrid[i][j].isBomb())
                    System.out.print("* ");
                else
                    System.out.print(tileGrid[i][j].getNumberOfBombsAround() + " ");
                if (j == ySize - 1)
                    System.out.print('\n');
            }
    }

}
