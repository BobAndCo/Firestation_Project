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
    private ArrayList<Node> bestSolution;

//------------------------------------------------------------
    Graph(int mapIndex){
        this.cityMap = new ArrayList<Node> ();
        this.setMap(mapIndex);
        this.getBestSolution();
        System.out.println("Final num of FS: " + this.bestNumOfFS); // failed !!!!!!!!!!!!!!!!!!!!
    }
//------------------------------------------------------------
    private void getBestSolution(){
        this.bestSolution = this.createFireStations(cityMap, 0, 0); // only testing node 0 first
        /*
         * to be implement: call createFireStations for each node 
         */
        return;
    }
//------------------------------------------------------------
    private ArrayList<Node> createFireStations(ArrayList<Node> currentMap, int currentIndex, int numOfFS){ // key method !!!!!!!!!!
        //this.printCurrentMap(currentMap);
        if( (this.bestNumOfFS!= 0) && (numOfFS>this.bestNumOfFS) ){
            return null;        // base case: not the best solution
        }
        
//Kylie: I believe there is a better way to check if every node is protected
        boolean allProtected = true;
        for(int mIdx=0; mIdx<currentMap.size(); mIdx++){
            if(!currentMap.get(mIdx).isProtected()){
                allProtected = false;
                break;
            }
        }
        if(allProtected){  // base case: if every node is protected, work finish
            System.out.println("Finish work with " + numOfFS + " fire station(s). ");
            this.bestNumOfFS = numOfFS;  // failed !!!!!!!!!!!!!!!!!!!!
            this.printCurrentMap(currentMap);
            return currentMap;
        }   
        Node currentNode = currentMap.get(currentIndex);
        ArrayList<Integer> childrenList = currentNode.getConnectionList();
        if(currentNode.hasVisited()){
            return null;
        }else{
            currentNode.setVisited();
            if(!currentNode.isProtected()){
                currentNode.setFireStation();
                numOfFS = numOfFS +1;
                /*********set currentNode's children as protected and recursion - move to its children**********/
                for(int c=0; c<childrenList.size(); c++){
                    int index= childrenList.get(c);
                    currentMap.get(index).setProtected();
                    createFireStations(currentMap, index, numOfFS);
                }  
            }else{ //currentNode is protected
                 /*********recursion: move to its children**********/
                for(int c=0; c<childrenList.size(); c++){
                    int index= childrenList.get(c);
                    createFireStations(currentMap, index, numOfFS);
                } 
            }
        }
        return null;
    }
//------------------------------------------------------------
    private void printCurrentMap(ArrayList<Node> currentMap){ // for debugging
        System.out.println("--------------currentMap--------" + this.bestNumOfFS);
        String city = "";
        String temp = "";
        Node currentNode;
        for(int i=0; i<currentMap.size(); i++){ 
            // example print format: √ 0_FS_P - [1] // visited, index, isFS, Protected , connectionlist
            currentNode = currentMap.get(i);
            temp = currentNode.getConnectionList().toString();
            if(currentNode.hasVisited()){ city += "√ ";
            }else{ city += "X "; }
            city += currentNode.getItem();
            
            if(currentNode.isFireStation()){ city += "_FS"; 
            }else{ city += "_..";}
            if(currentNode.isProtected()){ city += "_P"; 
            }else{ city += "_."; }
            
            city += " - " + temp + "\n";
        }
        System.out.println(city);
    }
//------------------------------------------------------------
    @Override
    public String toString(){
        String city = "\nFinal toString method\n";
        String temp = "";

        for(int i=0; i<this.cityMap.size(); i++){
            temp = this.cityMap.get(i).getConnectionList().toString();
            city += this.cityMap.get(i).getItem() + " - " + temp.substring(1, temp.length() - 1) + "\n";
        }
        return city;
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
        public boolean isFireStation(){
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