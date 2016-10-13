import java.util.ArrayList;
import java.util.Random;

// Class: MyGraph
// Description: Generate the random graph or small world connection graph
// originalAdjacentMatrix: store the adjacent matrix of the small world connection for p=0
// adjacentMatrix: store current adjacent matrix
// weightMatrix: store the shortest path between all nodes
// sizeN: number of nodes
// inf: a number to represent infinity distance
// nodes: a list to store all nodes
// img:  an instance of ImageConstruction class to visualize the graph

public class MyGraph {

    // Class: Node2D
    // Description: Represent a 2d node
    // id: node's id
    // x: x coordinate
    // y: y coordinate
    // inLargestCluster: a boolean variable to tell if this node is in the largest cluster
    public class Node2D {
        int id;
        double x;
        double y;
        boolean inLargestCluster;

        // Methods: Node2D
        // Description: construction function, compute the (x, y) of the node
        // Parameters: mId          the id of this node
        //             ringRadius   the radius of the ring
        //             ringSize     the number of nodes on the ring
        Node2D(int mId, double ringRadius, int ringSize)
        {
            this.id = mId;
            this.x = ringRadius * Math.cos(this.id * 2 * Math.PI / ringSize);
            this.y = ringRadius * Math.sin(this.id * 2 * Math.PI / ringSize);
            this.inLargestCluster = false;
        }

        // Method: getX
        // Description: get x coordinate of this node
        public double getX()
        {
            return this.x;
        }

        // Method: getY
        // Description: get y coordinate of this node
        public double getY()
        {
            return this.y;
        }

        // Method: setInLargestCluster
        // Description: set inLargestCluster true or false
        public void setInLargestCluster(boolean mInLargestCluster)
        {
            this.inLargestCluster = mInLargestCluster;
        }

        // Method: getInLargestCluster
        // Description: get inLargestCluster
        public boolean getInLargestCluster()
        {
            return this.inLargestCluster;
        }
    }

    private int [][] originalAdjacentMatrix;
    private int [][] adjacentMatrix;
    private int [][] weightMatrix;
    private int sizeN;
    private final int inf = 100000;

    private ArrayList<Node2D> nodes;
    ImageConstruction img;

    // Methods: MyGraph
    // Description: construction function
    // Parameters: n      the number of nodes
    MyGraph(int n)
    {
        sizeN = n;
        originalAdjacentMatrix = new int[n][n];
        adjacentMatrix = new int[n][n];
        weightMatrix = new int[n][n];
        nodes = new ArrayList<>();

        // initialize adjacent matrix
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (i == j)
                {
                    originalAdjacentMatrix[i][j] = 0;
                }
                else
                {
                    originalAdjacentMatrix[i][j] = inf;
                }
            }
        }

        // set current adjacent matrix
        resetCurrentMatrix();

        //
        for (int i = 0; i < n; i++)
        {
            nodes.add(new Node2D(i, 0.8, n));
        }

        //
        img = new ImageConstruction(700, 700, -1, 1, -1, 1, 1);
    }

    // construction part

    // Methods: setK
    // Description: set k value for small world connection graph
    // Parameters: k     the k value
    public void setK(int k)
    {
        // construct original un-rewired graph (ring)
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = i - k / 2; j <= i + k / 2; j++)
            {
                if (i != j)
                {
                    originalAdjacentMatrix[i][(j + sizeN) % sizeN] = 1;
                }
            }
        }

        // set current adjacent matrix
        resetCurrentMatrix();
    }


    // Methods: setRandomConnection
    // Description: construct random graph with specific probability
    // Parameters: p    the probability to connect a pair of nodes
    public void setRandomConnection(double p)
    {
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (Math.random() <= p)
                {
                    adjacentMatrix[i][j] = 1;
                    adjacentMatrix[j][i] = 1;
                }
            }
        }
    }

    // Methods: resetCurrentMatrix
    // Description: set current adjacent matrix to original adjacent matrix
    private void resetCurrentMatrix()
    {
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < sizeN; j++)
            {
                adjacentMatrix[i][j] = originalAdjacentMatrix[i][j];
            }
        }
    }

    // Methods: rewire
    // Description: rewire edges of small connection graph with specific probability
    // Parameters: p    the probability to rewire an edge
    public void rewire(double p)
    {
        resetCurrentMatrix();
        Random random = new Random();
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < sizeN; j++)
            {
                if (adjacentMatrix[i][j] == 1)
                {
                    if (Math.random() <= p)
                    {
                        int idx = i;
                        while (idx == i)
                        {
                            idx = random.nextInt(sizeN);
                        }
                        if (adjacentMatrix[i][idx] != 1)
                        {
                            adjacentMatrix[i][j] = inf;
                            adjacentMatrix[j][i] = inf;
                            adjacentMatrix[i][idx] = 1;
                            adjacentMatrix[idx][i] = 1;
                        }
                    }
                }
            }
        }
    }

    // algorithm part

    // Method: neighbourNumber
    // Description: get the neighbour nodes' number of a given node
    // Parameters: idx      The index of given node
    private int neighbourNumber(int idx)
    {
        int ret = 0;
        for (int i = 0; i < sizeN; i++)
        {
            if (adjacentMatrix[idx][i] == 1)
            {
                ret = ret + 1;
            }
        }
        return ret;
    }

    // Method: printNeighbouringTable
    // Description: print the table showing how many nodes are connected to 1, 2, 3, ..., etc
    public void printNeighbouringTable()
    {
        System.out.println("Display the neighbouring table:");
        System.out.println("Node ID\t\t#Neighbours");
        for (int i = 0; i < sizeN; i++)
        {
            System.out.println((i + 1) + "\t\t\t" + neighbourNumber(i));
        }
    }

    // Method: computeShortestPathes
    // Description: compute the shortest path for each pair of vertex by using Floyd algorithm
    public void computeShortestPathes()
    {
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < sizeN; j++)
            {
                weightMatrix[i][j] = adjacentMatrix[i][j];
            }
        }

        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < sizeN; j++)
            {
                for (int k = 0; k < sizeN; k++)
                {
                    if (weightMatrix[j][k] > weightMatrix[j][i] + weightMatrix[i][k])
                    {
                        weightMatrix[j][k] = weightMatrix[j][i] + weightMatrix[i][k];
                    }
                }
            }
        }
    }

    // Method: connectedNodesNumber
    // Description: compute the number of all the nodes connected (directly or indirectly) to a given node
    // Parameters: idx      The index of the given node
    private int connectedNodesNumber(int idx)
    {
        int ret = 1;
        for (int i = 0; i < sizeN; i++)
        {
            if (i != idx)
            {
                if (weightMatrix[idx][i] < inf)
                {
                    ret = ret + 1;
                }
            }
        }
        return ret;
    }

    // Method: markLargestClusterNodes
    // Description: mark the nodes in the largest cluster
    // Return: the size of largest cluster
    public int markLargestClusterNodes()
    {
        int largestClusterSize = -1;
        int largestClusterStart = 0;
        for (int i = 0; i < sizeN; i++)
        {
            int clusterSize = connectedNodesNumber(i);
            if (clusterSize > largestClusterSize)
            {
                largestClusterSize = clusterSize;
                largestClusterStart = i;
            }
        }

        for (int i = 0; i < sizeN; i++)
        {
            if (weightMatrix[largestClusterStart][i] < inf)
            {
                nodes.get(i).setInLargestCluster(true);
            }
        }
        return largestClusterSize;
    }

    // Methods: computeL
    // Description: compute L(p)
    public double computeL()
    {
        double t = 0;
        double L = 0;
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if(nodes.get(i).getInLargestCluster() && nodes.get(j).getInLargestCluster())
                {
                    L = L + weightMatrix[i][j];
                    t = t + 1;
                }
            }
        }
        return L / t;
    }

    // Methods: computeSingleC
    // Description: compute C for a single vertex
    // Parameters: idx      The index of the given node
    private double computeSingleC(int idx)
    {
        double C = 0;
        int NN = neighbourNumber(idx);
        double s = NN * (NN - 1.0) / 2.0;
        if (NN > 1)
        {
            int [] neighbourIdxList = new int[NN];
            int t = 0;
            for (int i = 0; i < sizeN; i++)
            {
                if (adjacentMatrix[idx][i] == 1)
                {
                    neighbourIdxList[t] = i;
                    t = t + 1;
                }
            }

            for (int i = 0; i < NN - 1; i++)
            {
                for (int j = i + 1; j < NN; j++)
                {
                    if (adjacentMatrix[i][j] == 1)
                    {
                        C = C + 1;
                    }
                }
            }
            return C / s;
        }
        else
        {
            return 0;
        }
    }


    // Methods: computeC
    // Description: compute C(p)
    public double computeC()
    {
        double t = 0;
        double C = 0;

        for (int i = 0; i < sizeN; i++)
        {
            if (nodes.get(i).getInLargestCluster())
            {
                C = C + computeSingleC(i);
                t = t + 1;
            }
        }

        return C / t;
    }

    // Method: computeEdgesVertexRatio
    // Desciption: compute the edges/vertex ratio
    public double computeEdgesVertexRatio()
    {
        double nEdges = 0;
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (adjacentMatrix[i][j] == 1)
                {
                    nEdges = nEdges + 1;
                }
            }
        }

        return nEdges / sizeN;
    }

    // rendering part

    // Method: renderNodes
    // Description: Only render the nodes of a graph
    public void renderNodes()
    {
        img.clearImage(255, 255, 255);
        for (Node2D node : nodes)
        {
            img.insertCircle(node.getX(), node.getY(), 0.012, 0, 0, 0, true);
        }
    }

    // Methods: renderGraph
    // Description: render a graph
    public void renderGraph()
    {
        img.clearImage(255, 255, 255);
        for (int i = 0; i < sizeN; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (adjacentMatrix[i][j] == 1)
                {
                    if (nodes.get(i).getInLargestCluster() && nodes.get(j).getInLargestCluster())
                    {
                        img.insertLine(
                                nodes.get(i).getX(), nodes.get(i).getY(),
                                nodes.get(j).getX(), nodes.get(j).getY(),
                                230, 70, 70
                        );
                    }
                    else
                    {
                        img.insertLine(
                                nodes.get(i).getX(), nodes.get(i).getY(),
                                nodes.get(j).getX(), nodes.get(j).getY(),
                                170, 170, 170
                        );
                    }
                }
            }
        }
        for (Node2D node : nodes)
        {
            if (node.getInLargestCluster())
            {
                img.insertCircle(node.getX(), node.getY(), 0.012, 255, 0, 0, true);
            }
            else
            {
                img.insertCircle(node.getX(), node.getY(), 0.012, 0, 0, 0, true);
            }
        }
    }

    // Method: displayGraph
    // Description: display the graph on screen
    public void displayGraph(String windowTitle)
    {
        img.displayImage(true, windowTitle, false);
    }

    // Method: closeDisplay
    // Description: close the display
    public void closeDisplay()
    {
        img.closeDisplay();
    }
}