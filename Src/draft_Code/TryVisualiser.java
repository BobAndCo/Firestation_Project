/**
 * Auto Generated Java Class.
 */
public class TryVisualiser {
    
    
    public static void main(String[] args) { 
        Graph city1 = new Graph(5);
        city1.getBestSolution();
        System.out.println("\nFinal result printed in Main.class:\nnumber of Fire Station: " + city1.getbestNumOfFS());
        System.out.println(city1.printCurrentMap(city1.getBestSolutionMap()));
       // Visualizer v = new Visualizer(city1);
        
        Community com1 = new Community(2);
        com1.fireStationPlanner();
        System.out.println(com1);
        Visualizer v = new Visualizer(com1);
        //"2"
    }
    
    /* ADD YOUR CODE HERE */
    
}
