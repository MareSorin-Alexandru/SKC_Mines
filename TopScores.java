//Mare Sorin-Alexandru

package MineSweeper;

import java.io.*;

public abstract class TopScores
{
    //returns the top 5 top scores of the selected difficulty
    public static String[] getTopScores(int difficulty)
    {
        //String array to store the file inputs
        String[] st = new String[15] ;

        int i = 0;
        try
        {
            //open file, copy scores in the String array
            File topScoresFile = new File("topScores");
            BufferedReader br = new BufferedReader(new FileReader(topScoresFile));

            while ((st[i++] = br.readLine()) != null && i < 15);
            //close file
            br.close();

            //depending on the difficulty, the appropriate scores are returned
            switch (difficulty)
            {
                case Game.EASY:
                    String easyScores[] = {st[0],st[1],st[2],st[3],st[4]};
                    return easyScores;
                case Game.MEDIUM:
                    String mediumScores[] = {st[5],st[6],st[7],st[8],st[9]};
                    return mediumScores;
                case Game.HARD:
                    String hardScores[] = {st[10],st[11],st[12],st[13],st[14]};
                    return hardScores;
                default:
                    return new String[5];
            }

        }
        catch (Exception e)
        {
            //if file cannot be opened copy the error message in the first element of the array and void the rest
            String errorString[] = new String[5];
            errorString[0]="File could not be Opened";
            for(i = 1; i < 5; i++)
                errorString[i]="";

            return errorString;
        }
    }

    //updates top 5 scores of all time (of that difficulty)
    public static int updateTopScores(long timeInSeconds, int difficulty)
    {
        //file input --> String array
        String[] st = new String[15] ;
        //String array --> Long array (and will use these longs to write back to the scores file)
        Long[] scores = new Long[15];

        int i = 0;
        try
        {
            //open file, copy scores in the String array
            File topScoresFile = new File("topScores");
            BufferedReader br = new BufferedReader(new FileReader(topScoresFile));

            while ((st[i++] = br.readLine()) != null && i < 15);
            br.close();

            //extract longs from strings
            for(i = 0; i < 15; i++)
                scores[i]=Long.parseLong(st[i]);

            //podium position is initialised with -1 (the value returned if score is not in the top 5 or file cannot be accessed)
            int podiumPosition = -1;

            //decide whether the new score is a high score (and if so add it to the top 5 at the right position)
            //duplicates allowed
            //per difficulty
            for(i = 0 + difficulty * 5; i < 5 + difficulty * 5; i++)
                if(timeInSeconds < scores[i])
                {
                    for(int j = 3 + difficulty * 5; j >= i; j--)
                        scores[j+1] = scores[j];
                    scores[i] = timeInSeconds;
                    //podium starts with #1 not #0
                    podiumPosition = i+1 - difficulty * 5;
                    break;
                }

            //write results back to file in order
            BufferedWriter bw = new BufferedWriter(new FileWriter(topScoresFile));
            for(i = 0; i < 15; i++)
            {
                bw.write(scores[i].toString());
                bw.newLine();
            }
            bw.close();

            //return podium position
            return podiumPosition;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    //resets Top Scores
    public static boolean resetTopScores()
    {
        try
        {
            //writes everything over with the default top Scores
            File topScoresFile = new File("topScores");
            BufferedWriter bw = new BufferedWriter(new FileWriter(topScoresFile));
            for(int i = 0; i < 15; i++)
            {
                //default top score is 999
                bw.write("999");
                bw.newLine();
            }
            bw.close();

            //on success return true
            return true;
        }
        catch (Exception e)
        {
            //returns false if it cannot reset the file / make a new file (permission issue etc.)
            return false;
        }
    }

}
