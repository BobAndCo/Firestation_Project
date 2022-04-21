import java.io.FileReader;
import java.io.BufferedReader;
/**[Graph.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * @author Khrush, Kylie, Alon - ICS4UE
  * @version1.0, April 21, 2022
  */

public class Graph {
    boolean[][] map;
    static FileReader fileReader;
    static String fileName = "communityInput_";
    static BufferedReader input;
    
    Graph(int type){
        try{
        fileReader = new FileReader(fileName + type + ".txt");
        input = new BufferedReader(fileReader);
        
        while(input.ready()){
           // input.readLine();
        }input.close();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getMap(){

    }

}