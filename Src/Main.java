/**[Main.java]
  * This is Algorithms Assignment - Fire Stations Planner
  * This is the main method 
  * @author Khush, Kylie, Alon - ICS4UE
  * @version1.0, April 21, 2022
  */

public class Main{

    public static void main(String[] args){
        long start, end;  
        double executionTime;
        start = System.nanoTime(); 
                
        NewCommunity com1 = new NewCommunity(0); // map0, map 5 - null exception
        //all works, problem: some node is lost when it has no connection but not yet protected 
        //- find that node and set it as FS
        
        com1.callMethod();
        System.out.println(com1);
        
        end = System.nanoTime(); 
        executionTime = (end - start)/1000000.0; 
        System.out.println("This took: " + executionTime + "ms");
        
        /*
        start = System.nanoTime(); 
               
        Graph city1 = new Graph(1);
        city1.getBestSolution();
        System.out.println("\nFinal result printed in Main.class:\nnumber of Fire Station: " + city1.getbestNumOfFS());
        System.out.println(city1.printCurrentMap(city1.getBestSolutionMap()));
        
        end = System.nanoTime(); 
        executionTime = (end - start)/1000000.0; 
        System.out.println("This took: " + executionTime + "ms");
        
        //////////////////////////////////
        start = System.nanoTime(); 
        Community com = new Community(1);
        com.callMethod();
        //com.findBestSolution();
        
        System.out.println("best solution: " + com.getbestNumOfFS());
        System.out.println(com.printCurrentMap(com.getBestSolutionMap()));
        
        end = System.nanoTime(); 
        executionTime = (end - start)/1000000.0; 
        System.out.println("This took: " + executionTime + "ms");
        */
    }
}
