import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Visualizer extends JFrame{
    Graph city;
     int[][] locationOfPoints;
    private ArrayList<ArrayList<Integer> > cityArray = new ArrayList<ArrayList<Integer> >();
    private ArrayList<Integer> comunitisInCityList = new ArrayList<Integer>();
    private String connections;
    NodePanel panel;
        
    final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();    
    //final int GRIDSIZE = MAX_Y/60;
    int comunitySize;
    int comunitySpace;
    Visualizer(Graph city){
        // 
        this.city = city; 
        this.locationOfPoints = new int[city.cityMap.size()][2];
        this.comunitySpace=MAX_Y/(city.cityMap.size());
        this.comunitySize = MAX_Y/(city.cityMap.size()+10);
        
        this.connections = connections;
       
        
        generateCityArray();
        for(int row=0; row<cityArray.size(); row++){
                for(int col=0; col<cityArray.get(row).size(); col++){
                    Integer currentNode =cityArray.get(row).get(col);
                    this.locationOfPoints[currentNode][0]=row;
                    this.locationOfPoints[currentNode][1]=col;
                    System.out.println("Zhopaaaaaaaaaaaaa" + currentNode+"," + row +", "+ col);
                }
        }
         this.panel =  new NodePanel();
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setVisible(true);
    }
    
     // generate a siquence of  indexes for visualiser
    // make an array of used indexes
    // make a 2D array with y-as index and all values in the row as x
    public void generateCityArray(){   
 // param int nodeIndex, int row
//        if(!comunitisInCityList.contains(Integer.valueOf(nodeIndex))){
//             System.out.println("Zhopa" + "  " + row + "   " + comunitisInCityList  + "   " + nodeIndex);
//            if(row>=cityArray.size()){
//                cityArray.add(new ArrayList<Integer>());
//            }
//            comunitisInCityList.add(Integer.valueOf(nodeIndex));
//            cityArray.get(row).add(nodeIndex);
//            for(Integer nodeIn: city.getConectByIndex(nodeIndex)){
//                generateCityArray(nodeIn.intValue(), row+1);
//            } 
//        }
        // row = 0
        int row = 0;
        //int nodeIndex = 0;
        // create first line
        cityArray.add(new ArrayList<Integer>());
        cityArray.get(0).add(0);
        comunitisInCityList.add(0);
        boolean adding = true; 
        // double llop
        while(adding){
            ArrayList<Integer> tempCnct = new ArrayList<Integer>();
            for(Integer nodeIndex: cityArray.get(row)){
                System.out.println("Debug 1 " + row + " " + cityArray.get(row) + "    nodeIndex: " + nodeIndex);
                for(Integer nodeIn: city.getConectByIndex(nodeIndex.intValue())){
                    System.out.println("Debug2 " + nodeIn );
                    // check all the comunities you have been into
                    // check if They have connections
                    if(!comunitisInCityList.contains(Integer.valueOf(nodeIn))){
                        
                        // send all their connections which are not in the visited list to the next line
                        comunitisInCityList.add(Integer.valueOf(nodeIn));
                        tempCnct.add(nodeIn);
                    }
                }
            }
            System.out.println("Debug3 " + tempCnct );
            row +=1;
            if(!tempCnct.isEmpty()){
                System.out.println("Zhopa" + "   row=" + row  + "  tempcnt=" + tempCnct);
                cityArray.add(tempCnct);
            }
            else{
                 System.out.println("NEZHOPA");
                 adding = false;
            }
        }
      System.out.println("NEZHOPA2");  
        
    }

    private class NodePanel extends JPanel{
        
        NodePanel(){
            
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //g.setFont(font);
            //Font font = new Font("Verdana", Font.BOLD, 14);
            int originX;
            int originY;
            for(int row=0; row<cityArray.size(); row++){
                originY =row*comunitySpace;
                for(int col=0; col<cityArray.get(row).size(); col++){
                     originX = col*comunitySpace;
                    Integer currentNode =cityArray.get(row).get(col);
                    g.setColor(Color.BLACK);
                     int cnctPointX = 0;
                     int cnctPointY = 0;
                    for(int location: city.getConectByIndex(currentNode)){
                        if(locationOfPoints[location][0] >= row){
                            cnctPointX = locationOfPoints[location][1]*comunitySpace;
                            cnctPointY = locationOfPoints[location][0]*comunitySpace;
                            g.drawLine(originX+comunitySize/2, originY+comunitySize/2, cnctPointX+comunitySize/2, cnctPointY+comunitySize/2 ); 
                            System.out.println("Zhopa " + "("+ col +", "+row+")"+"(" + cnctPointX +", "+cnctPointY+ ")");
                        }  
                     }
                }
            }

            for(int row=0; row<cityArray.size(); row++){
                originY =row*comunitySpace;
                for(int col=0; col<cityArray.get(row).size(); col++){
                     originX = col*comunitySpace;
                    Integer currentNode =cityArray.get(row).get(col);
                    if(city.checkIsFireStation(currentNode)){
                        g.setColor(Color.RED);
                    }
                    else{
                        g.setColor(Color.YELLOW);
                    }
                    g.fillOval(originX , originY, comunitySize, comunitySize);
                    g.setColor(Color.black);
                    g.drawString(currentNode.toString(), originX+comunitySize/2 , originY+comunitySize/2 );

                     
                    
                }
            }
            

            
            
            //g.fillRect(150,150,150,150); 
            this.repaint();
        }
    }
}
