import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**[Graph.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * This is a class representing the community(graph)
  * @author Khrush, Kylie, Alon - ICS4UE
  * @version1.1, April 21, 2022
  */

public class Graph {
    private boolean[][] map;
    private ArrayList<ArrayList<Integer>> arrayList;
    static FileReader fileReader;
    static String fileName = "communityInput_";
    static BufferedReader input;
    
    Graph(int type){
        arrayList = new ArrayList<ArrayList<Integer>> ();
        
        /***********read input from text file************/
        try{
            fileReader = new FileReader("TestCases/" + fileName + type + ".txt");
            input = new BufferedReader(fileReader);
            String str;
            while(input.ready()){
                str = input.readLine();            //format as: 1 - 0, 2, 5
                str = str.substring(4);            // only take 0, 2, 5 for operations 
                
                String[] connectionStr = str.split(" *, *");    // sample: [0, 2, 5]
                Integer[] connectionIntArray = new Integer[connectionStr.length];
                for(int node=0; node<connectionStr.length; node++){ // change it into int array
                    connectionIntArray[node] = Integer.parseInt(connectionStr[node]);
                }
                List<Integer> newList = Arrays.asList(connectionIntArray);
                arrayList.add(new ArrayList<Integer>(newList));
            }input.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        printArrayList();
    }
    
    public void printArrayList(){
        for(int i=0; i<this.arrayList.size(); i++){
            System.out.println(this.arrayList.get(i).toString());
        }
    }
    
    public static void getMap(){
        
    }
    
}