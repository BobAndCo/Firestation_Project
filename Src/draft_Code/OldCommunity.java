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

public class OldCommunity {
    private ArrayList<Node> cityMap;
    private ArrayList<Node> bestSolutionMap;

    static FileReader fileReader;
    static BufferedReader input;

    final static String FILENAME = "communityInput_";

    private int bestNumOfFS = 0;
    private int mapIndex;
    private int currentNumOfFS=0;

//------------------------------------------------------------
    OldCommunity(int mapIndex){
        this.mapIndex = mapIndex;
        this.cityMap = new ArrayList<Node>();
        this.bestSolutionMap = new ArrayList<Node>();
        this.setMap(mapIndex);
        this.bestNumOfFS = cityMap.size()/2;
        //System.out.println("testing:" + helper(12));
    }
    
    
    /*****************try every solution, store the best one**********************/
    public void findBestSolution(){ // return 
        for(int i=1; i<(int)Math.pow(2, cityMap.size()); i++){
            this.resetOriginalMap();
            String config = helper(i);
            System.out.println("config: " + config);
            getBestSolutions(config);
        }
    }
    /*****************Check if the combination works****************************/
    /*
     * if works and it is the best solution compare to what we found
     * clone the cityMap into best solution map
     */
    public void getBestSolutions(String config){ // we need to return a map here
        int numOfFS=0;
        for(int i=0; i<config.length(); i++){
            if(config.charAt(i) == '1'){
                this.cityMap.get(i).setFireStation();
                System.out.println("FS: " + i);
                numOfFS ++;
            }
        }
        /***********check if all the nodes are protected***********/
        boolean allProtected = true;
        for(int mIdx=0; mIdx<this.cityMap.size(); mIdx++){
            if(!this.cityMap.get(mIdx).isProtected()){
                allProtected = false;
                break;
            }
        }
        //if all nodes are protected, return numOFFs, if no return -1
        if( (allProtected) && (numOfFS < this.bestNumOfFS) ){
           // System.out.println( printCurrentMap(this.cityMap)) ;
            this.bestNumOfFS = numOfFS;
            //System.out.println("Finish work with " + numOfFS + " fire station(s). ");
            this.bestSolutionMap.clear();
            this.cloneNodes(this.cityMap, this.bestSolutionMap);
            return;
        }
        return;
    }
//------------------------------------------------------------
    public String decToBinString(int n, String str){
        if(n<=1){
            return Integer.toString(n) + str;
        }
        str = Integer.toString(n%2) + str;
        return decToBinString(n/2, str);
    }
//------------------------------------------------------------
    public String helper(int m){ // add back 0s in to binString if needed
        String binString = decToBinString(m, "");
        if(binString.length()<=cityMap.size()){
            for(int i=binString.length(); i<cityMap.size(); i++){
                binString = "0" + binString;
            }
        }
        return binString;
    }
//------------------------------------------------------------
    private void resetOriginalMap(){
        for(int i=0; i<this.cityMap.size(); i++){
            cityMap.get(i).resetNode();
        }
    }
//------------------------------------------------------------
    private ArrayList<Node> cloneNodes(ArrayList<Node> inputMap, ArrayList<Node> outputMap){
        for (int i = 0; i < inputMap.size(); i++){
            outputMap.add(inputMap.get(i).copyNode());
        }
        return outputMap;
    }
//------------------------------------------------------------
    public String printCurrentMap(ArrayList<Node> currentMap){ // for debugging
        System.out.println("---------currentMap---------");
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
            currentNumOfFS += 1;
            /*********set currentNode's children as protected**********/
            for(int c=0; c<this.connectionList.size(); c++){
                cityMap.get(this.connectionList.get(c)).setProtected();
            }  
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
