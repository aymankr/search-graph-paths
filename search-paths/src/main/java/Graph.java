
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ay
 */
public class Graph {

    private final Point start;
    private final Point end;

    private final ArrayList<Edge> edges;
    private ArrayList<Integer>[] adjList;
    private final ArrayList<Point> points;
    private ArrayList<Path> paths;

    public Graph(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.edges = new ArrayList<>();
        this.points = new ArrayList<>();
        this.paths = new ArrayList<>();
        points.add(start);
        points.add(end);
    }

    public void initAdjList() {
        adjList = new ArrayList[points.size()];
        for (int i = 0; i < points.size(); i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Integer>[] getAdjList() {
        return adjList;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public void addEdge(Edge r) {
        edges.add(r);
        adjList[r.getP1().getId()].add(r.getP2().getId());
    }
    
    public void addPath(Path p) {
        paths.add(p);
    }  
    
    public ArrayList<Path> getPaths() {
        return paths;
    }
}
