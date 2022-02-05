
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
public class Path implements Cloneable {

    private ArrayList<Vertice> vertices;
    private final Graph graph;
    private ArrayList<Edge> edges;

    public Path(Graph graph) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.graph = graph;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Path cloned = (Path) super.clone();
        cloned.edges = (ArrayList<Edge>) edges.clone();
        cloned.vertices = (ArrayList<Vertice>) vertices.clone();
        return cloned;
    }

    public ArrayList<Edge> getEdgesWithStops() {
        return (ArrayList<Edge>) edges.stream().filter(p -> p.isStop()).collect(Collectors.toList());
    }

    public int getNbVertices() {
        return vertices.size();
    }

    public int getTotalEuclideanDistance() {
        return edges.stream().mapToInt(e -> e.getLength()).sum();
    }

    public void addVertice(Vertice e) {
        if ((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)
                && vertices.contains(r.getP1())).findAny().orElse(null) != null) {
            edges.add((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)
                    && vertices.contains(r.getP1())).findAny().orElse(null));
        }
        vertices.add(e);
    }

    public void removeVertice(Vertice e) {
        if ((Edge) graph.getEdges().stream().filter(r -> r.getP1().equals(e)).findAny().orElse(null) != null) {
            edges.remove((Edge) graph.getEdges().stream().filter(r -> r.getP1().equals(e)).findAny().orElse(null));
        }
        if ((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)).findAny().orElse(null) != null) {
            edges.remove((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)).findAny().orElse(null));
        }
        vertices.remove(e);
    }

    public void displayPath() {
        System.out.println("Nb vertices : " + getNbVertices()
                + " | Nb stops : " + getEdgesWithStops().size()
                + " (weight : " + getEdgesWithStops().size() * 10 + ")"
                + " | Total euclidean distance : " + getTotalEuclideanDistance());
        vertices.forEach(p -> {
            System.out.print(p.getId() + " ");
        });
        System.out.println();
    }
}
