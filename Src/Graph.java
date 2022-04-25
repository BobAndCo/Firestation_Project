import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**[Graph.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * This is a class representing the community(graph)
  * @author Khrush, Kylie, Alon - ICS4UE
  * @version1.1, April 21, 2022
  */

public class Graph {
    protected ArrayList<Node> cityMap;
    private ArrayList<Node> bestSolution;

    static FileReader fileReader;
    static BufferedReader input;

    final static String FILENAME = "communityInput_";

    private int bestNumOfFS = 0;
    private int mapIndex;

//------------------------------------------------------------
    Graph(int mapIndex){
        this.mapIndex = mapIndex;
        this.cityMap = new ArrayList<Node>();
        this.setMap(mapIndex);
        this.getBestSolution();
        System.out.println("\nFinal result:\nnum of FS: " + this.bestNumOfFS);
    }
//------------------------------------------------------------
    private void getBestSolution(){
        ArrayList<Node> tempCity = new ArrayList<Node>();        
        for (int i = 0; i < this.cityMap.size(); i++){
            System.out.printf("We are starting at Node %d\n", i);
            tempCity.clear();
            this.cloneNodes(cityMap, tempCity);
            this.createFireStations(tempCity, i);
        }
    }

    private ArrayList<Node> cloneNodes(ArrayList<Node> inputMap, ArrayList<Node> outputMap){
        for (int i = 0; i < inputMap.size(); i++){
            outputMap.add(inputMap.get(i).copyNode());
        }
        return outputMap;
    }

//------------------------------------------------------------
    private int createFireStations(ArrayList<Node> currentMap, int currentIndex){ // key method !!!!!!!!!!
        //this.printCurrentMap(currentMap);
        int numberOfFS = this.numberOfFirestations(currentMap);
        if( (this.bestNumOfFS!= 0) && (numberOfFS>this.bestNumOfFS) ){
            return -1;        // base case: not the best solution
        }
        
        boolean allProtected = true;
        for(int mIdx=0; mIdx<currentMap.size(); mIdx++){
            if(!currentMap.get(mIdx).isProtected()){
                allProtected = false;
                break;
            }
        }
        if(allProtected){  // base case: if every node is protected, work finish
            System.out.println("Finish work with " + numberOfFS + " fire station(s). ");
            this.bestNumOfFS = numberOfFS;
            this.printCurrentMap(currentMap);
            return numberOfFS;
        }   
        Node currentNode = currentMap.get(currentIndex);
        ArrayList<Integer> childrenList = currentNode.getConnectionList();
        if(currentNode.hasVisited()){
            return -1;
        }else{
            currentNode.setVisited();
            if(!currentNode.isProtected()){
                currentNode.setFireStation();
                /*********set currentNode's children as protected**********/
                for(int c=0; c<childrenList.size(); c++){
                    currentMap.get(childrenList.get(c)).setProtected();
                }  
            }
            /*********recursion: move to its children**********/
            for(int c=0; c<childrenList.size(); c++){
                createFireStations(currentMap, childrenList.get(c));
            } 
        }
        return -1;
    }
//------------------------------------------------------------
    private void printCurrentMap(ArrayList<Node> currentMap){ // for debugging
        System.out.println("--------------currentMap--------" + this.bestNumOfFS);
        String city = "";
        String temp = "";
        Node currentNode;
        for(int i=0; i<currentMap.size(); i++){ 
            // example print format: √ 0_FS_P - [1] 
            // visited, index, isFS, Protected , connectionlist
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
    private int numberOfFirestations(ArrayList<Node> currentMap){
        Node currentNode;
        int numOfFirestations = 0;
        for (int i = 0; i < currentMap.size(); i++){
            currentNode = currentMap.get(i);
            if (currentNode.isFireStation()){
                numOfFirestations++;
            }
        }
        return numOfFirestations;
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
            fileReader = new FileReader("TestCases/" + FILENAME + mapIndex + ".txt");
            input = new BufferedReader(fileReader);
            String str;
            int nodeIndex;
            while(input.ready()){
                str = input.readLine();            //format as: 1 - 0, 2, 5
                nodeIndex = Integer.parseInt(str.substring(0, str.indexOf("-")-1) );
                str = str.substring(str.indexOf("-") +2);            // only take 0, 2, 5 for operations 
                
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
        private boolean isFireStation = false;
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

        public Node copyNode(){
            return new Node(this.item, this.connectionList);
        }
//---------------------------setters---------------------------
        private void setFireStation(){
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