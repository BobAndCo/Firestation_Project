/**[Main.java]
  * This is Algorithms Assignment - Fire Stations Planner
  * This is the main method 
  * @author Khush, Kylie, Alon - ICS4UE
  * @version 2.0, April 30, 2022
  */

public class Main{
    public static void main(String[] args){  
        long start, end;  
        double executionTime;
        start = System.nanoTime(); 
        
        Community com1 = new Community(6);
        com1.fireStationPlanner();
        System.out.println(com1);
        end = System.nanoTime(); 
        executionTime = (end - start)/1000000.0; 
        System.out.println("The sort took: " + executionTime + "ms");
        
        Visualizer v = new Visualizer(com1);
    }
}
