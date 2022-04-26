/**[Main.java]
  * This is Algorithms Assignment - Fire Stations Planner
  * This is the main method 
  * @author Khush, Kylie, Alon - ICS4UE
  * @version1.0, April 21, 2022
  */

public class Main{

    public static void main(String[] args){

        Graph city1 = new Graph(1);
        city1.getBestSolution();
        System.out.println("\nFinal result:\nnum of FS: " + city1.bestNumOfFS);
//        System.out.print(city1);
        
    }

}
