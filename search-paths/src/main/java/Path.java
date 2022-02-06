
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class Path : a path in a graph is composed by vertices linked by edges
 * @author ay
 */
public class Path implements Cloneable, Comparable<Path> {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    private final Graph graph;

    /**
     * Construct a path
     * @param graph the graph
     */
    public Path(Graph graph) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.graph = graph;
    }

    /**
     * Clone a path, this function is important for the SearchPath method in Journey.java
     * 
     * @return cloned path
     * @throws CloneNotSupportedException 
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Path cloned = (Path) super.clone();
        cloned.edges = (ArrayList<Edge>) edges.clone();
        cloned.vertices = (ArrayList<Vertex>) vertices.clone();
        return cloned;
    }

    /**
     * Compare two paths
     * @param o other path
     * @return 0 if they are same
     */
    @Override
    public int compareTo(Path o) {
        return compareToUtil(o, 0);
    }

    /**
     * Compare each vertex of the two paths
     * @param o other path
     * @param index index to start recursion
     * @return sum of compareTo's values from this recursion
     */
    public int compareToUtil(Path o, int index) {
        if (index >= o.getNbVertices() || index >= getNbVertices()) {
            return 0;
        } else {
            return vertices.get(index).compareTo(o.getVertices().get(index)) + compareToUtil(o, index + 1);
        }
    }

    /**
     * Get vertices
     * @return list of vertices
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Get the length of vertices
     * @return length value
     */
    public int getNbVertices() {
        return vertices.size();
    }

    /**
     * Get all the edges they have a stop
     * @return 
     */
    public ArrayList<Edge> getEdgesWithStops() {
        return (ArrayList<Edge>) edges.stream().filter(p -> p.isStop()).collect(Collectors.toList());
    }

    /**
     * Get to total euclidean distance of this path
     * @return distance
     */
    public int getTotalEuclideanDistance() {
        return edges.stream().mapToInt(e -> e.getLength()).sum();
    }

    /**
     * Add a vertex in this path, if in the graph, we find an edge e having v as destination, add the edge to
     * this current path
     * @param v vertex
     */
    public void addVertex(Vertex v) {
        vertices.add(v);
        if ((Edge) graph.getEdges().stream().filter(e -> e.getP2().equals(v)
                && vertices.contains(e.getP1())).findAny().orElse(null) != null) {
            edges.add((Edge) graph.getEdges().stream().filter(e -> e.getP2().equals(v)
                    && vertices.contains(e.getP1())).findAny().orElse(null));
        }
    }

    /**
     * Remove a vertex in the path, also remove the edges if the source or the destination corresponds
     * @param v 
     */
    public void removeVertex(Vertex v) {
        vertices.remove(v);
        if ((Edge) graph.getEdges().stream().filter(e -> e.getP1().equals(v)).findAny().orElse(null) != null) {
            edges.remove((Edge) graph.getEdges().stream().filter(r -> r.getP1().equals(v)).findAny().orElse(null));
        }
        if ((Edge) graph.getEdges().stream().filter(e -> e.getP2().equals(v)).findAny().orElse(null) != null) {
            edges.remove((Edge) graph.getEdges().stream().filter(r -> r.getP2().equals(v)).findAny().orElse(null));
        }
    }

    /**
     * Display informations and vertices of the path
     */
    public void displayPath() {
        System.out.println("Nb vertices : " + getNbVertices()
                + " | Nb stops : " + getEdgesWithStops().size()
                + " (weight : " + getEdgesWithStops().size() * 30 + ")"
                + " | Total euclidean distance : " + getTotalEuclideanDistance());
        vertices.forEach(v -> {
            System.out.print(v.getId() + " ");
        });
        System.out.println();
    }
}
