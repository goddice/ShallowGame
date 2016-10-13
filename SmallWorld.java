// Program: SmallWorld.java
// Course:
// Description: This program use the MyGraph class to generate random graph
//              or small world connection. There is some basic user interaction
//              function.
// Author:
// Revised:
// Language: Java
// IDE: NetBeans IDE 8.1
// ****************************************************************************
// ****************************************************************************
public class SmallWorld {
    public static void main(String[] argv){

        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        String userInput;

        userInput = keyboardInput.getKeyboardInput("Please enter the number of nodes:");
        int N = Integer.parseInt(userInput);

        // create a new MyGraph object
        MyGraph graph = new MyGraph(N);
        graph.renderNodes();
        graph.displayGraph("Nodes"); // only display nodes

        // prompt user to select graph type
        System.out.println("There are two types of graphs");
        System.out.println("1. Random graphs");
        System.out.println("2. Small-world connectivity");
        userInput = keyboardInput.getKeyboardInput("Please choose the type of graphs (1 or 2):");
        int choice = Integer.parseInt(userInput);
        if (choice == 1)
        {
            // user selected random graph
            userInput = keyboardInput.getKeyboardInput("Please specify the probability of pair connection (0 ~ 1):");
            double p = Double.parseDouble(userInput);
            graph.setRandomConnection(p); // random connection
            graph.closeDisplay();
            graph.renderGraph();
            graph.displayGraph("Random Graph");
        }
        if (choice == 2)
        {
            // user selected small world connection
            userInput = keyboardInput.getKeyboardInput("Please specify the number of nearest neighbors k (even number):");
            int k = Integer.parseInt(userInput);
            graph.setK(k); // set k nearest neighbour connection
            graph.closeDisplay();
            graph.renderGraph();
            graph.displayGraph("Small World");
            userInput = keyboardInput.getKeyboardInput("Please specity the rewire probability p (0~1):");
            double p = Double.parseDouble(userInput);
            graph.setRandomConnection(p);
            graph.rewire(p); // rewire
            graph.closeDisplay();
            graph.renderGraph();
            graph.displayGraph("Small World");
        }

        graph.printNeighbouringTable();
        graph.computeShortestPathes();
        int largestClusterSize = graph.markLargestClusterNodes();
        System.out.println("L(p) = " + graph.computeL());
        System.out.println("C(p) = " + graph.computeC());
        System.out.println("The size of largest cluster: " + largestClusterSize);
        System.out.println("The edges/vertex ratio is: " + graph.computeEdgesVertexRatio());

        // display the largest cluster nodes and edges with red color
        graph.closeDisplay();
        graph.renderGraph();
        graph.displayGraph("Colored Largest Cluster");

        keyboardInput.getKeyboardInput("Enter any key to exit");
        graph.closeDisplay();
    }
}
