import java.io.File;
/**[Main.java]
  * This is Algorithms Assignment - Fire Stations Planner
  * This is the main method 
  * @author Khush, Kylie, Alon - ICS4UE
  * @version 2.0, April 30, 2022
  */

public class Main{
    public static void main(String[] args){  
        Community com1 = new Community(5);
        com1.fireStationPlanner();
        System.out.println(com1);
        Visualizer v = new Visualizer(com1);
    }
}
