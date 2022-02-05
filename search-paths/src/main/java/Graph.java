
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

    private final Vertice start;
    private final Vertice end;

    private final ArrayList<Edge> edges;
    private ArrayList<Integer>[] adjacentVerticesId;
    private final ArrayList<Vertice> vertices;
    private final ArrayList<Path> paths;

    public Graph(Vertice start, Vertice end) {
        this.start = start;
        this.end = end;
        this.edges = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.paths = new ArrayList<>();
        vertices.add(start);
        vertices.add(end);
    }

    public void initAdjVertices() {
        adjacentVerticesId = new ArrayList[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            adjacentVerticesId[i] = new ArrayList<>();
        }
    }

    public Vertice getStart() {
        return start;
    }

    public Vertice getEnd() {
        return end;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public ArrayList<Integer>[] getAdjVertices() {
        return adjacentVerticesId;
    }

    public void addVertice(Vertice p) {
        vertices.add(p);
    }

    public void addEdge(Edge r) {
        edges.add(r);
        adjacentVerticesId[r.getP1().getId()].add(r.getP2().getId());
    }

    public void addPath(Path p) {
        paths.add(p);
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }
}
