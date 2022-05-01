import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**[Visualizer.java]
  * This is Algorithms Assignment - Fire Stations Planner
  * This is the visualizer that takes in the Community that draws the cities and fire station
  * @author Khush, Kylie, Alon - ICS4UE
  * @version 1.0, May 1, 2022
  */
public class Visualizer extends JFrame{
    private ArrayList<ArrayList<Integer> > cityArray = new ArrayList<ArrayList<Integer> >();
    private ArrayList<Integer> comunitisInCityList = new ArrayList<Integer>();
    private Community community;
    private HashMap<Integer, ArrayList<Integer>> communityMap;
    private HashSet<Integer> fireStationList;
    NodePanel panel;
    private int[][] locationOfPoints;
    
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();    

    private int communitySize;
    private int communitySpace;
//---------------------------------------------------------------------------------------------------------------
    Visualizer(Community com){
        this.community = com;
        this.communityMap = com.getCommunityMap();
        this.locationOfPoints = new int[communityMap.size()][2];
        this.communitySpace = MAX_Y/(communityMap.size()); 
        this.communitySize = MAX_Y/(communityMap.size()+10);
        this.fireStationList = com.getFireStationList();
        
        this.generateCityArray();
        for(int row=0; row<cityArray.size(); row++){
            for(int col=0; col<cityArray.get(row).size(); col++){
                Integer currentNode =cityArray.get(row).get(col);
                this.locationOfPoints[currentNode][0]=row;
                this.locationOfPoints[currentNode][1]=col;
            }
        }
        
        this.panel =  new NodePanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_X, MAX_Y);
        this.setVisible(true);
    }
//---------------------------------------------------------------------------------------------------------------
    /*generate a sequence of indexes for visualiser
     * make an array of used indexes
     * make a 2D array with y-axis index and all values in the row as x
     */
    public void generateCityArray(){   
        int row = 0;
        //int nodeIndex = 0;
        // create first line
        cityArray.add(new ArrayList<Integer>());
        cityArray.get(0).add(0);
        comunitisInCityList.add(0);
        boolean adding = true; 
        //double loop
        while(adding){
            ArrayList<Integer> tempCnct = new ArrayList<Integer>();
            for(Integer nodeIndex: cityArray.get(row)){
                for(Integer nodeIn: communityMap.get(nodeIndex)){
                    //check all the comunities you have been to, check if they have connections
                    if(!comunitisInCityList.contains(Integer.valueOf(nodeIn))){
                        //set all their connections which are not in the visited list to the next line
                        comunitisInCityList.add(Integer.valueOf(nodeIn));
                        tempCnct.add(nodeIn);
                    }
                }
            }
            row +=1;
            if(!tempCnct.isEmpty()){
                cityArray.add(tempCnct);
            }
            else{
                adding = false;
            }
        } 
    }
//---------------------------------------------------------------------------------------------------------------
//inner class - NodePanel
//---------------------------------------------------------------------------------------------------------------
    private class NodePanel extends JPanel{
        NodePanel(){ 
        }
//--------------------------------------------------------------------------------------
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int originX;
            int originY;
            for(int row=0; row<cityArray.size(); row++){
                originY =row*communitySpace;
                for(int col=0; col<cityArray.get(row).size(); col++){
                    originX = col*communitySpace;
                    Integer currentNode =cityArray.get(row).get(col);
                    g.setColor(Color.BLACK);
                    int cnctPointX = 0;
                    int cnctPointY = 0;
                    for(int location: communityMap.get(currentNode)){ 
                        if(locationOfPoints[location][0] >= row){
                            cnctPointX = locationOfPoints[location][1]*communitySpace;
                            cnctPointY = locationOfPoints[location][0]*communitySpace;
                            g.drawLine(originX+communitySize/2, originY+communitySize/2, 
                                       cnctPointX+communitySize/2, cnctPointY+communitySize/2 ); 
                        }  
                    }
                }
            }
            
            for(int row=0; row<cityArray.size(); row++){
                originY =row*communitySpace;
                for(int col=0; col<cityArray.get(row).size(); col++){
                    originX = col*communitySpace;
                    Integer currentNode =cityArray.get(row).get(col);

                    if(fireStationList.contains(currentNode)){ // if the node is a fire station
                        g.setColor(Color.RED);
                    }
                    else{
                        g.setColor(Color.YELLOW);
                    }
                    g.fillOval(originX , originY, communitySize, communitySize);
                    g.setColor(Color.black);
                    g.drawString(currentNode.toString(), originX+communitySize/2-6 , originY+communitySize/2+5);
                }
            }
        }
    }
}
