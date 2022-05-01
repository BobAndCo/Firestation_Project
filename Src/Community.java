import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**[Community.java]
  * This is Algorithms Assignment - Fire Stations Planner 
  * This is a class representing the community
  * @author Khush, Kylie, Alon - ICS4UE
  * @version 3.1, April 30, 2022
  */

public class Community{
    private HashSet<Integer> fireStationList;
    private HashMap<Integer, ArrayList<Integer>> communityMap;

    static FileReader fileReader;
    static BufferedReader input;

    final static String FILENAME = "communityInput_";

    private int mapIndex;
    
//------------------------------------------------------------
    Community(int mapIndex){
        this.mapIndex = mapIndex;
        this.communityMap = new HashMap<Integer, ArrayList<Integer>>(); // key, value
        this.fireStationList = new HashSet<Integer>();
        this.setMap(mapIndex);
    }
//------------------------------------------------------------------------------------
    public void fireStationPlanner(){ //Method that creates the fire stations by calling createcommunityMapFireStationList
        HashMap<Integer, ArrayList<Integer>> tempcommunityMap = new HashMap<Integer, ArrayList<Integer>>();
        HashSet<Integer> tempFSList = new HashSet<Integer>();
        HashSet<Integer> tempVisited = new HashSet<Integer>();
        
        for(Integer nodeIndex : this.communityMap.keySet()){ //pass in a duplicate of the HashMap communityMap
            tempcommunityMap.put(nodeIndex, this.communityMap.get(nodeIndex));
        }
        
        this.fireStationList = this.createcommunityMapFireStationList(tempcommunityMap, tempFSList, 0, tempVisited);
        //createcommunityMapFireStationList
        System.out.println("Fire Station at: " + this.fireStationList);
    }
//---------------------------------------------------------------------------------------------------------------------
    /* LOGIC: (note: FS = Fire Station)
     * Find a node that has ONLY ONE child, then that node's child MUST be a FS so that it is protected
     * if there is no node that satisfy condition above, make the last node we transverse as FS
     * 
     * Erase all connections with FS and protected node, and erase the nodes
     * then proceed to the protected node's children, until every node is erased from the map
     */
    private HashSet<Integer> createcommunityMapFireStationList(HashMap<Integer, ArrayList<Integer>> hashMap, 
                                              HashSet<Integer> fsList, int nodeIndex, HashSet<Integer> visitedNodes){
        if(hashMap.size()==0){       //base case: when every node is either protected / is a FS
            return fsList;
        }
        visitedNodes.add(nodeIndex);
        ArrayList<Integer> childrenList = hashMap.get(nodeIndex);
        
/*******if: the node has ONLY ONE child *****************/
        if(childrenList.size()==1){
            visitedNodes.clear();
            Integer childIndex = childrenList.get(0); //get the only child
            /*Make currentNode's child a FS, then call protected node's children recursively*/
            ArrayList<Integer> protectedNodeConnectionList = this.makeFirestation(hashMap, fsList, childIndex);
            for(int callCh=0; callCh<protectedNodeConnectionList.size(); callCh++){
                int callIndex = protectedNodeConnectionList.get(callCh);
                if( hashMap.containsKey(callIndex) ){ 
                    this.createcommunityMapFireStationList(hashMap, fsList, callIndex, visitedNodes);
                }
            }
/*******else: tranverse the map until we find a node that has ONLY ONE child*******/
        }else{
            boolean haveWay = false;
            int index=0;
            for(int ch=0; ch<childrenList.size(); ch++){
                index = childrenList.get(ch);
                if(!visitedNodes.contains(index)){ //if the node is not visited yet
                    haveWay = true;
                    this.createcommunityMapFireStationList(hashMap, fsList, index, visitedNodes);
                }
            }
            // if all node is visited, but not one node has only one child --> set the last visited node as FS
            if(!haveWay){ 
                ArrayList<Integer> protectedNodeConnectionList = this.makeFirestation(hashMap, fsList, index);

                for(int callCh=0; callCh<protectedNodeConnectionList.size(); callCh++){
                    int callIndex = protectedNodeConnectionList.get(callCh);
                    if(hashMap.containsKey(callIndex) ){
                        this.createcommunityMapFireStationList(hashMap, fsList, callIndex, visitedNodes);
                    }
                }
            }
        }
        return fsList;
    }
//------------------------------------------------------------------------------------
    private ArrayList<Integer> makeFirestation(HashMap<Integer, ArrayList<Integer>> hashMap, 
                                                HashSet<Integer> fsList, int fsIndex){
        fsList.add(fsIndex);
        ArrayList<Integer> connectionList = this.eraseConnection(hashMap, fsList, fsIndex);
        return connectionList;
    }
//method - erase connection between nodes to shrink the map after a FS is placed-----------------------------------
    private ArrayList<Integer> eraseConnection(HashMap<Integer, ArrayList<Integer>> hashMap, 
                                               HashSet<Integer> fsList, int fireStationIdx){
        ArrayList<Integer> returnConnectionList = new ArrayList<Integer>();
        ArrayList<Integer> currentNodeChildren = hashMap.get(fireStationIdx); //get connection of the FS
        
        Set<Integer> deleteList = new HashSet<Integer>();
        Set<Integer> originalList = new HashSet<Integer>();
        /******add the items that we need to disconnect - FS and protected Node *******/
        deleteList.add(fireStationIdx);
        for(int i=0; i<currentNodeChildren.size(); i++){
            deleteList.add(currentNodeChildren.get(i));
            returnConnectionList.add(currentNodeChildren.get(i)); // protected node's children
        }
        /************Go through every node and delete connections************/
        for(Integer c : hashMap.keySet()){
            ArrayList<Integer> childrenList = hashMap.get(c); 
            originalList.clear();
            originalList.addAll(childrenList);
            /**remove all connection between FS/Protected node**/
            originalList.removeAll(deleteList);
            /**update connections between nodes**/
            ArrayList<Integer> temp = new ArrayList<>(originalList);
            childrenList = temp;
            hashMap.replace(c, childrenList);
            if(childrenList.isEmpty()){
                returnConnectionList.remove(c);
            }
        }
        
        //remove all node that has an empty ArrayList
        ArrayList<Integer> myList = new ArrayList<Integer>();
        hashMap.values().removeAll(Collections.singleton(myList));
        //deleteList stores the protected nodes that have children
        deleteList.retainAll(returnConnectionList); 
        returnConnectionList.clear();
        
        for(Integer deleteIdx : deleteList){
            //returnConenctionList stores the children of the protected nodes
            returnConnectionList.addAll(hashMap.get(deleteIdx)); 
            /**check if node that is not protected but its children are all protected, then that node must be a FS**/
            ArrayList<Integer> deleteNodeConnection = hashMap.get(deleteIdx);
            for(int d=0; d<deleteNodeConnection.size(); d++){
                int index =  deleteNodeConnection.get(d);
                if(!hashMap.containsKey(index)){
                    fsList.add(index);
                }
            }
            hashMap.remove(deleteIdx); // remove all node that are protected / is a FS
        }
        return returnConnectionList;    //return the children of the protected nodes
    }
 //Getters-------------------------------------------------------------------------
    public HashMap<Integer, ArrayList<Integer>> getCommunityMap(){
        return this.communityMap;
    }
    public HashSet<Integer> getFireStationList(){
        return this.fireStationList;
    }
//---------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString(){
        String city = ""; //"\ntoString method\n";
        String temp = "";
        
        for(Integer nodeIndex : this.communityMap.keySet()){
            if(this.fireStationList.contains(nodeIndex)){
                city += "FS ";
            }else{
                city += "   ";
            }
            temp = this.communityMap.get(nodeIndex).toString();
            city += nodeIndex + " - " + temp.substring(1, temp.length() - 1) + "\n";
        }
        return city;
    }
//---------------------------------------------------------------------------------------------------------------------
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
                this.communityMap.put(nodeIndex, connectionArr);
            }
            input.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
   
