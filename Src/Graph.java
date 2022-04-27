import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**[Graph.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * This is a class representing the community(graph)
  * @author Khush, Kylie, Alon - ICS4UE
  * @version1.1, April 21, 2022
  */

public class Graph {
    protected ArrayList<Node> cityMap;
    private ArrayList<Node> bestSolutionMap;

    static FileReader fileReader;
    static BufferedReader input;

    final static String FILENAME = "communityInput_";

    private int bestNumOfFS = 0;
    private int mapIndex;
    private int currentNumOfFS=0;

//------------------------------------------------------------
    Graph(int mapIndex){
        this.mapIndex = mapIndex;
        this.cityMap = new ArrayList<Node>();
        this.bestSolutionMap = new ArrayList<Node>();
        this.setMap(mapIndex);
        //Visualizer v = new Visualizer(this.cityMap.getConnections());
    }
    public int counter = 0; // for debug 
//------------------------------------------------------------
    public void getBestSolution(){
        for (int i = 0; i < this.cityMap.size(); i++){
            this.counter =0;
            this.currentNumOfFS = 0;
            System.out.printf("We are starting at Node %d\n", i);
            this.reset();
            this.createFireStations(cityMap, i);
            System.out.println("This called " + counter + " times");
        }
        //this.createFireStations(cityMap, 1);
    }
    private void reset(){
        for(int i=0; i<this.cityMap.size(); i++){
            cityMap.get(i).resetNode();
        }
    }
    private ArrayList<Node> cloneNodes(ArrayList<Node> inputMap, ArrayList<Node> outputMap){
        for (int i = 0; i < inputMap.size(); i++){
            outputMap.add(inputMap.get(i).copyNode());
        }
        return outputMap;
    }
//------------------------------------------------------------
    private void createFireStations(ArrayList<Node> currentMap, int currentIndex){ // key method !!!!!!!!!!
        counter = counter+1;
        int numberOfFS = this.currentNumOfFS;
        //same numOfFS, different coloring, doesn't rlly matter
        if( (this.bestNumOfFS!= 0) && (this.currentNumOfFS>=this.bestNumOfFS) ){
            //System.out.print(numberOfFS + " From Node " + currentIndex + "---------currentMap---------\n" + this.printCurrentMap(currentMap));
            return;        // base case: not the best solution
        }
        if(this.currentNumOfFS > this.cityMap.size()/2){
            System.out.println("waste");
            return;
        }
        //System.out.print(numberOfFS + " From Node " + currentIndex + "---------currentMap---------\n" + this.printCurrentMap(currentMap));
        
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
            this.bestSolutionMap.clear();
            this.cloneNodes(currentMap, this.bestSolutionMap);
            //v.print();
            return;
        }   
        Node currentNode = currentMap.get(currentIndex);
        ArrayList<Integer> childrenList = currentNode.getConnectionList();
        int childrenSize = childrenList.size();
        currentNode.setVisited();
        if(!currentNode.isProtected()){
            if(childrenSize>1){
                if(childrenSize>2){
                    currentNode.setFireStation();
                    this.currentNumOfFS += 1;
                }else{
                    int numOfProtected =0;
                    int numOfchildreIsFS=0;
                    for(int chP=0; chP<childrenSize; chP++){
                        if(cityMap.get(childrenList.get(chP)).isProtected()){
                            numOfProtected++;
                        }
                        if(cityMap.get(childrenList.get(chP)).isFireStation()){
                            numOfchildreIsFS++;
                        }
                    }
                    if(numOfProtected !=2){
                        currentNode.setFireStation();
                        this.currentNumOfFS += 1;
                    }else{
                        if(numOfchildreIsFS==0){
                            currentNode.setFireStation();
                            this.currentNumOfFS += 1;
                        }
                    }
                }
            }else{
                currentNode.setFireStation();
                this.currentNumOfFS += 1;
            }
            /*********set currentNode's children as protected**********/
            for(int c=0; c<childrenList.size(); c++){
                currentMap.get(childrenList.get(c)).setProtected();
            }  
        }
        /*********recursion: move to its children**********/
        for(int c=childrenList.size()-1; c>=0; c--){
            int childIdx = childrenList.get(c);
            if(!currentMap.get(childIdx).hasVisited()){
                createFireStations(currentMap, childIdx);
            }
        } 
        return;
    }
//------------------------------------------------------------
    public String printCurrentMap(ArrayList<Node> currentMap){ // for debugging
        //System.out.println("---------currentMap---------");
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
        return city;
    }
//------------------------------------------------------------
    public ArrayList<Node> getBestSolutionMap(){
        return this.bestSolutionMap;
    }
//------------------------------------------------------------
    public int getbestNumOfFS(){
        return this.bestNumOfFS;
    }
//------------------------------------------------------------
    @Override
    public String toString(){
        String city = "\ntoString method\n";
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
        private Node(int number, ArrayList<Integer> connections, boolean isFS, boolean isP, boolean isVis){
            this.item = number;
            this.connectionList = connections;
            this.isFireStation = isFS;
            this.isProtected = isP;
            this.visited = isVis;
        }
//---------------------------getters---------------------------
        private int getItem(){
            return this.item;
        }
        private boolean isFireStation(){
            return this.isFireStation;
        }
        private boolean isProtected(){
            return this.isProtected;
        }
        private boolean hasVisited(){
            return this.visited;
        }
        private ArrayList<Integer> getConnectionList(){
            return this.connectionList;
        }

        private Node copyNode(){
            return new Node(this.item, this.connectionList, this.isFireStation, this.isProtected, this.visited);
        }
        
        public void resetNode(){
           this.isFireStation = false;
           this.isProtected = false;
           this.visited = false;
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
