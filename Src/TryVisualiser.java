/**
 * Auto Generated Java Class.
 */
public class TryVisualiser {
    
    
    public static void main(String[] args) { 
        Graph city1 = new Graph(2);
        city1.getBestSolution();
        System.out.println("\nFinal result printed in Main.class:\nnumber of Fire Station: " + city1.getbestNumOfFS());
        System.out.println(city1.printCurrentMap(city1.getBestSolutionMap()));
        Visualizer v = new Visualizer(city1);
        //"2"
    }
    
    /* ADD YOUR CODE HERE */
    
}
