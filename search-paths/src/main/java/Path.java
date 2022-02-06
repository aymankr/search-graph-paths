
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
public class Path implements Cloneable, Comparable<Path> {

    private ArrayList<Vertex> vertices;
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
        cloned.vertices = (ArrayList<Vertex>) vertices.clone();
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

    public void addVertex(Vertex e) {
        if ((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)
                && vertices.contains(r.getP1())).findAny().orElse(null) != null) {
            edges.add((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(e)
                    && vertices.contains(r.getP1())).findAny().orElse(null));
        }
        vertices.add(e);
    }

    public void removeVertex(Vertex e) {
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
                + " (weight : " + getEdgesWithStops().size() * 30 + ")"
                + " | Total euclidean distance : " + getTotalEuclideanDistance());
        vertices.forEach(p -> {
            System.out.print(p.getId() + " ");
        });
        System.out.println();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public int compareTo(Path o) {
        return compareToUtil(o, 0);
    }

    public int compareToUtil(Path o, int index) {
        if (index >= o.getNbVertices() || index >= getNbVertices()) {
            return 0;
        } else {
            return vertices.get(index).compareTo(o.getVertices().get(index)) + compareToUtil(o, index + 1);
        }
    }
}
