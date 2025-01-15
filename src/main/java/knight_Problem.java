import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class knight_Problem {

    public static void main(String[] args) {
        knight_Problem project2 = new knight_Problem();
        boolean exit = false;
        while(!exit){
            Scanner sc = new Scanner(System.in);
            System.out.println("Start the Knight problem? (y/n)");
            String answer = sc.next();
            //In case someone type capital letter we will case it into lower case
            String LowerCaseAnswer = answer.toLowerCase();
            switch(LowerCaseAnswer){
                case "y":
                    int n = project2.AskUser();
                    SimpleGraph<String, DefaultEdge> HorsePathGraph = project2.CreateHorseGraph(n);
                    project2.placingHorse(HorsePathGraph, n);
                    break;
                case "n":
                    exit = true;
                    break;
                default:
                    System.out.println("Please type only y (yes) or n (no)");
                    break;
            }

        }

    }

    public int AskUser(){
        boolean valid = false;
        int n = 0;
        Scanner sc = new Scanner(System.in);
        while(!valid) {
            System.out.println("Enter N for N*N board (N must be at least 5)");
            n = sc.nextInt();
            if(n < 5) {
                System.out.println("Please enter N with the value at least 5");
            }
            else{
                valid = true;
            }
        }
        return n;
    }

    public SimpleGraph<String, DefaultEdge> CreateHorseGraph(int n){
        HashMap<String, Point> PointCoordinateMap = new HashMap<>();
        int PointNum = 0;
        System.out.print("Cell IDs");
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Point newPoint = new Point(i, j);
                String PointName = Integer.toString(PointNum);
                PointCoordinateMap.put(PointName,newPoint);
                if(j==0 && i!=0){
                    System.out.printf("%18s",PointName);
                }
                else{
                    System.out.printf("%10s", PointName);
                }
                PointNum++;
            }
            System.out.println();
        }
        return createGraph(PointCoordinateMap, n);
    }

    public void placingHorse(SimpleGraph<String, DefaultEdge> HorsePathGraph, int n) {
        System.out.println("Enter Knight ID :");
        Scanner sc = new Scanner(System.in);
        String Knight_ID = sc.nextLine();
        System.out.println("Enter Castle ID :");
        String Castle_ID = sc.nextLine();
        System.out.println("Enter Bomb IDs separated by comma (invalid IDs will be ignored)");
        String line = sc.nextLine();
        String[] cols = line.split(",");

        LinkedList<String> BombList = new LinkedList<>();

        //Remove vertex that is considered a bomb
        for(String bombID : cols){
            //If the bombID exists in Graph then remove it otherwise ignore
            //We also ignore if the bomb id is the same as knight id and castle id
            if(HorsePathGraph.containsVertex(bombID.trim())
                    && !bombID.trim().equals(Knight_ID) && !bombID.trim().equals(Castle_ID)){
                BombList.add(bombID.trim());
                HorsePathGraph.removeVertex(bombID.trim());
            }
        }
        System.out.println();

        //Using BFS
        //DijkstraShortestPath<String, DefaultEdge> dijkstra = new DijkstraShortestPath<>(HorsePathGraph);
        //BellmanFordShortestPath<String, DefaultEdge> bellmanFord = new BellmanFordShortestPath<>(HorsePathGraph);
        ShortestPathAlgorithm<String, DefaultEdge> bfs = new BFSShortestPath<>(HorsePathGraph);
        //FloydWarshallShortestPaths<String, DefaultEdge> floydWarshall = new FloydWarshallShortestPaths<>(HorsePathGraph);
        //JohnsonShortestPaths<String, DefaultEdge> johnson = new JohnsonShortestPaths<>(HorsePathGraph);

        // Record the start time
        long startTime = System.nanoTime();

        // Find the shortest path
        GraphPath<String, DefaultEdge> path = bfs.getPath(Knight_ID, Castle_ID);

        // Record the end time
        long endTime = System.nanoTime();

        // Check if a path exists
        if (path == null) {
            // If no path exists, print a message
            System.out.println("The path from " + Knight_ID + " to " + Castle_ID + " doesn't exist.");
        }
        else {
            // If a path exists, visualize it and print runtime
            System.out.println("Breadth-First Search (BFS):");

            System.out.println("Path: " + path.getVertexList() + "\n");
            HorseVisualization(path, BombList, n, Castle_ID);
            float milliSec = (float) (endTime - startTime)/1000000;
            System.out.printf("Runtime: %.5f ms\n", milliSec);
        }
        /*
        //Using Bellman-Ford algo
        BellmanFordShortestPath<String, DefaultEdge> bellmanFord = new BellmanFordShortestPath<>(HorsePathGraph);
        shortestPathEdges = bellmanFord.getPath(Knight_ID, Castle_ID).getEdgeList();
        System.out.println("\nBellman-Ford Algo : ");
        for(DefaultEdge eachEdge : shortestPathEdges){
            System.out.println(eachEdge);
        }
        shortestPathEdges.clear();

        //Using Breadth-First-Search (BFS)
        System.out.println("\nBreadth-First-Search (BFS) :");
        ShortestPathAlgorithm<String, DefaultEdge> bfs = new BFSShortestPath<>(HorsePathGraph);
        GraphPath<String, DefaultEdge> path = bfs.getPath(Knight_ID, Castle_ID);
        System.out.println("Path: " + path.getVertexList());

        // Floyd-Warshall Algorithm
        System.out.println("\nFloyd-Warshall Algorithm :");
        FloydWarshallShortestPaths<String, DefaultEdge> floydWarshall = new FloydWarshallShortestPaths<>(HorsePathGraph);
        shortestPathEdges = floydWarshall.getPath(Knight_ID, Castle_ID).getEdgeList();
        for(DefaultEdge eachEdge : shortestPathEdges){
            System.out.println(eachEdge);
        }
        shortestPathEdges.clear();

        // Johnson
        System.out.println("\nJohnson Algorithm");
        JohnsonShortestPaths<String, DefaultEdge> johnson = new JohnsonShortestPaths<>(HorsePathGraph);
        shortestPathEdges = johnson.getPath(Knight_ID, Castle_ID).getEdgeList();
        for(DefaultEdge eachEdge : shortestPathEdges){
            System.out.println(eachEdge);
        }
        shortestPathEdges.clear();
         */

    }

    public void HorseVisualization(GraphPath<String, DefaultEdge> VertexList, LinkedList<String> BombList, int n, String Castle_ID){
        for(int k=0; k<=VertexList.getEdgeList().size(); k++){
            System.out.println("Step : "+(k+1)+" | move to "+VertexList.getVertexList().get(k));
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    int VertexName = (i*n)+j;
                    //if this vertex exists in the shortest path
                    if(VertexList.getVertexList().get(k).equals(Integer.toString(VertexName))){
                        if(VertexName == Integer.parseInt(Castle_ID)){
                            System.out.printf("\u001B[35m%5s\u001B[0m","K+C");
                        }
                        else{
                            System.out.printf("\u001B[32m%5s\u001B[0m","K");
                        }
                    }
                    else if(BombList.contains(Integer.toString(VertexName))){
                        System.out.printf("\u001B[31m%5s\u001B[0m","B");
                    }
                    else if(VertexName == Integer.parseInt(Castle_ID)){
                        System.out.printf("\u001B[33m%5s\u001B[0m","C");
                    }
                    else{
                        System.out.printf("%5d",VertexName);
                    }
                }
                System.out.println();
            }
            System.out.println("==================================================");
        }
    }

    public SimpleGraph<String, DefaultEdge> createGraph(HashMap<String, Point> PointCoordinateMap, int n){
        SimpleGraph<String,DefaultEdge> HorsePathGraph= new SimpleGraph<>(DefaultEdge.class);
        ArrayList<Point> HorseMoveList = new ArrayList<>();

        HorseMoveList.add(new Point(1,2)); // Q1
        HorseMoveList.add(new Point(2,1));

        HorseMoveList.add(new Point(2,-1));// Q2
        HorseMoveList.add(new Point(1,-2));

        HorseMoveList.add(new Point(-1,-2));// Q3
        HorseMoveList.add(new Point(-2,-1));

        HorseMoveList.add(new Point(-2,1));// Q4
        HorseMoveList.add(new Point(-1,2));

        //Add all the PointName to the Graph each one represent one vertex
        for(String PointName : PointCoordinateMap.keySet()) {
            HorsePathGraph.addVertex(PointName);
        }

        for(String PointName : PointCoordinateMap.keySet()) {
            for(Point eachMove : HorseMoveList) {
                Point DestinationPoint = eachMove.Addition(PointCoordinateMap.get(PointName));
                //Convert DestinationPoint Coor to name
                int DestinationNum = (DestinationPoint.getX()*n)+DestinationPoint.getY();
                String DestinationName = Integer.toString(DestinationNum);
                //if the destination point exists in the graph then create edge
                if(DestinationPoint.getX() >= 0 && DestinationPoint.getX() < n
                   && DestinationPoint.getY() >= 0 && DestinationPoint.getY() < n){
                    //adding edge
                    HorsePathGraph.addEdge(PointName, DestinationName);
                }
            }
        }
        return HorsePathGraph;
    }
}
