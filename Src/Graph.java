import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

/**[Graph.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * This is a class representing the community(graph)
  * @author Khrush, Kylie, Alon - ICS4UE
  * @version1.1, April 21, 2022
  */

public class Graph {
    protected ArrayList<Node> cityMap;
    static FileReader fileReader;
    static String fileName = "communityInput_";
    static BufferedReader input;
    protected static final boolean FIRESTATION = true;
    protected static final boolean NOTFIRESTATION = !FIRESTATION; //sets to false
    private int bestNumOfFS = 0;
    
//------------------------------------------------------------
    public Graph(int mapIndex){
        this.cityMap = new ArrayList<Node> ();
        this.setMap(mapIndex);
        //this.createFireStations(cityMap, 0, 0);
    }
//------------------------------------------------------------
    public ArrayList<Node> getBestSolution(){
        //call create fire stations for each node 
        return null;
    }
//------------------------------------------------------------
    public ArrayList<Node> createFireStations(ArrayList<Node> currentMap, int currentIndex, int numOfFS){ // key method !!!!!!!!!!
        if( (this.bestNumOfFS!= 0) && (numOfFS>this.bestNumOfFS) ){
            return null;        // base case: not the best solution
        }
        System.out.println("hello");
        if(false){  // base case: if every node is protected, work finish
            /*********** add code here************/ 
            //how to check all node is protected?
            return currentMap;
        }   
        Node currentNode = currentMap.get(currentIndex);
        if(!currentNode.hasVisited()){
            currentNode.setVisited();
            if(currentNode.isProtected()){
                currentNode.setFireStation();
            }
        }
        
        return null;
    }
//------------------------------------------------------------
    public void setMap(int mapIndex){
        try{
            fileReader = new FileReader("TestCases/" + fileName + mapIndex + ".txt");
            input = new BufferedReader(fileReader);
            String str;
            int nodeIndex;
            while(input.ready()){
                str = input.readLine();            //format as: 1 - 0, 2, 5
                nodeIndex = Integer.parseInt(str.substring(0, 1));
                str = str.substring(4);            // only take 0, 2, 5 for operations 
                
                String[] connectionStr = str.split(" *, *");    // sample: [0, 2, 5]
                Integer[] connectionIntArray = new Integer[connectionStr.length];
                for(int node=0; node<connectionStr.length; node++){ // change it into int array
                    connectionIntArray[node] = Integer.parseInt(connectionStr[node]);
                }
                ArrayList<Integer> connectionArr = new ArrayList<Integer>();
                Collections.addAll(connectionArr, connectionIntArray);
                this.cityMap.add(new Node(nodeIndex, connectionArr));
            }
            input.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------
    @Override
    public String toString(){
        String city = "";
        String temp = "";

        for(int i=0; i<this.cityMap.size(); i++){
            temp = this.cityMap.get(i).getConnectionList().toString();
            city += this.cityMap.get(i).getItem() + " - " + temp.substring(1, temp.length() - 1) + "\n";
        }
        return city;
    }
    public void print(){
        System.out.print(this.toString());
    }
//-----------------------------------------------------------------------------------------
// inner class
//-----------------------------------------------------------------------------------------
    private class Node{
        private int item;
        private boolean isFireStation = NOTFIRESTATION;
        private boolean isProtected = false;
        private boolean visited = false;
        private ArrayList<Integer> connectionList;
        
        Node(int number, ArrayList<Integer> connections){
            this.item = number;
            this.connectionList = connections;
        }
//---------------------------getters---------------------------
        public int getItem(){
            return this.item;
        }
        public boolean isFirestation(){
            return this.isFireStation;
        }
        public boolean isProtected(){
            return this.isProtected;
        }
        public boolean hasVisited(){
            return this.visited;
        }
        public ArrayList<Integer> getConnectionList(){
            return this.connectionList;
        }
//---------------------------setters---------------------------
        private void setFireStation(){ //boolean stat){
            this.isFireStation = true;
            this.isProtected = true;
        }
        private void setProtected(){
            this.isProtected = true;
        }
        private void setVisited(){
            this.visited = true;
        }
    }
    
}