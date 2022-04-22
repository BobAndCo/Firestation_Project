import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**[Graph.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * This is a class representing the community(graph)
  * @author Khrush, Kylie, Alon - ICS4UE
  * @version1.1, April 21, 2022
  */

public class Graph {

    protected boolean[][] map;
    protected ArrayList<ArrayList<Integer>> cityMap;
    static FileReader fileReader;
    static String fileName = "communityInput_";
    static BufferedReader input;

    protected static final boolean FIRESTATION = true;
    protected static final boolean NOFIRESTATION = !FIRESTATION; //sets to false
    
    public Graph(int mapIndex){

        cityMap = new ArrayList<ArrayList<Integer>> ();
        this.getMap(mapIndex);

    }
    
    @Override
    public String toString(){
        
        String city = "";
        String temp = "";

        for(int i=0; i<this.cityMap.size(); i++){
            temp = this.cityMap.get(i).toString();
            city += Integer.toString(i) + " - " + temp.substring(1, temp.length() - 1) + "\n";
        }
        return city;
    }

    public void print(){
        System.out.print(this.toString());
    }

    
    
    public void getMap(int mapIndex){
        try{
            fileReader = new FileReader("TestCases/" + fileName + mapIndex + ".txt");
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
                cityMap.add(new ArrayList<Integer>(newList));
            }
            input.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private class Protect{

        private int node;
        private boolean status;

        Protect(int number, boolean visited){
            this.node = number;
            this.status = visited;
        }

        public boolean getStatus(){
            return this.status;
        }

        public void setStatus(boolean stat){
            this.status = stat;
        }

        public int getNode(){
            return this.node;
        }

    }
    
}