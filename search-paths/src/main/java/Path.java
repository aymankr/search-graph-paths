
import java.util.ArrayList;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ay
 */
public class Path {

    private ArrayList<Point> points;
    private Graph graph;
    private ArrayList<Edge> edges;

    public Path(Graph graph) {
        points = new ArrayList<>();
        edges = new ArrayList<>();
        this.graph = graph;
    }

    public ArrayList<Edge> getEdgesWithStops() {
        return (ArrayList<Edge>) edges.stream().filter(p -> p.isStop()).collect(Collectors.toList());
    }

    public int getLengthPath() {
        return points.size();
    }

    public void addPoint(Point e) {
        Edge edge1 = (Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e) && points.contains(r.getP1())).findAny().orElse(null);
        if (edge1 != null) {
            edges.add(edge1);
        }
        points.add(e);
    }

    public void removePoint(Point e) {
        Edge edge1 = (Edge) graph.getEdges().stream().filter(r -> r.getP1().equals(e)).findAny().orElse(null);
        if (edge1 != null) {
            edges.remove(edge1);
        }
        Edge edge2 = (Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)).findAny().orElse(null);
        if (edge2 != null) {
            edges.remove(edge2);
        }
        points.remove(e);
    }

    public void displayPath() {
        System.out.println("Nb points : " + getLengthPath() + ", nb edges with stops : " + getEdgesWithStops().size());
        points.forEach(p -> {
            System.out.print(p.getId() + " ");
        });
        System.out.println();
    }
}
