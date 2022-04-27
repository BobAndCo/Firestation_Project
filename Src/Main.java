/**[Main.java]
  * This is Algorithms Assignment - Fire Stations Planner
  * This is the main method 
  * @author Khush, Kylie, Alon - ICS4UE
  * @version1.0, April 21, 2022
  */

public class Main{

    public static void main(String[] args){

        Graph city1 = new Graph(6);
        city1.getBestSolution();
        System.out.println("\nFinal result printed in Main.class:\nnumber of Fire Station: " + city1.bestNumOfFS);
        System.out.println(city1.printCurrentMap(city1.bestSolutionMap));
        //System.out.print(city1);
        
    }

}
