//Mare Sorin-Alexandru

package MineSweeper;

public abstract class ScoreAreaHandler
{
    private static String[][] Scores = new String[3][5];

    //returns a string to be displayed in the text area at the bottom of the frame
    public static String handleScoreArea()
    {
        //get top scores amd store them according to difficulty
        for(int i = 0; i < 3; i++)
            Scores[i]=TopScores.getTopScores(i);

        //string to be returned
        String returnString = "";

        //first line
        returnString+="                                                                                                                        Top Scores\n";
        //second line
        returnString+="                                                                            Easy Difficulty               Medium Difficulty               Hard Difficulty\n";

        //score lines
        for(int i = 0; i < 5; i++)
        {
            //position on podium
            returnString+="                                                                 #"+String.valueOf(i+1)+"              ";
            for(int j =0; j < 3; j++)
            {
                try
                {
                    //scores + appropriate spacing
                    if(Integer.parseInt(Scores[j][i])<10)
                        returnString+="    "+Scores[j][i]+"s";
                    else if(Integer.parseInt(Scores[j][i])<100)
                        returnString+="  "+Scores[j][i]+"s";
                    else
                        returnString+=Scores[j][i]+"s";
                }
                catch (Exception e)
                {
                    //file is reset and the right message is displayed if possible
                    if(TopScores.resetTopScores())
                        return handleScoreArea();
                    //else return an appropriate error message
                    return "                                                                                                      Failed To Access Or Change Top Scores File";
                }
                //space between scores
                returnString+="                                  ";
            }
            //add new line
            returnString+="\n";
        }

        //return the string
        return returnString;
    }
}
