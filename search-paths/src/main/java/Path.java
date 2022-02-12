
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class Path : a path in a graph is composed by vertices linked by edges
 *
 * @author ay
 */
public class Path implements Cloneable, Comparable<Path> {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    private final Graph graph;

    /**
     * Construct a path
     *
     * @param graph the graph
     */
    public Path(Graph graph) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.graph = graph;
    }

    /**
     * Clone a path, this function is important for the search path method in
     * Journey.java Since we use only one path to search for them all in the
     * graph, when it is necessary to add a found path, we must clone this path
     * to add it because we must avoid adding the same path each time in the
     * list, by changing the "reference" by cloning
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
     *
     * @param o other path
     * @return 0 if they are same
     */
    @Override
    public int compareTo(Path o) {
        return compareToUtil(o, 0);
    }

    /**
     * Compare each vertex of the two paths
     *
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
     *
     * @return list of vertices
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Get the length of vertices
     *
     * @return length value
     */
    public int getNbVertices() {
        return vertices.size();
    }

    /**
     * Get to total euclidean distance of this path
     *
     * @return distance
     */
    public int getTotalEuclideanDistance() {
        return edges.stream().mapToInt(e -> e.getLength()).sum();
    }

    /**
     * Verify if in this path, an another edge has the same source vertex as the e Edge
     * 
     * @param e
     * @return 
     */
    private boolean sourceEdgeExists(Edge e) {
        return edges.stream().anyMatch(e2 -> (e2.getP1() == e.getP1()));
    }

    /**
     * Add a vertex in this path, if in the graph, we find an edge e having v as
     * destination, add the edge to this current path
     *
     * @param v vertex
     */
    public void addVertex(Vertex v) {
        vertices.add(v);
        Edge e1 = (Edge) graph.getEdges().stream().filter(e -> e.getP2().equals(v)
                && !sourceEdgeExists(e) && vertices.contains(e.getP1())).findAny().orElse(null);
        if (e1 != null) {
            edges.add(e1);
        }
    }

    /**
     * Remove a vertex in the path, also remove the edges if the source or the
     * destination corresponds
     *
     * @param v
     */
    public void removeVertex(Vertex v) {
        vertices.remove(v);
        Edge e1 = (Edge) graph.getEdges().stream().filter(e -> e.getP1().getId() == v.getId()).findAny().orElse(null);
        Edge e2 = (Edge) graph.getEdges().stream().filter(e -> e.getP2().getId() == v.getId()).findAny().orElse(null);

        if (e1 != null) {
            edges.remove(e1);
        }
        if (e2 != null) {
            edges.remove(e2);
        }
    }

    /**
     * Show informations of this path
     */
    public void displayPath() {
        vertices.forEach(v -> {
            System.out.print(v.getId() + " ");
        });
        System.out.print("--> Vertices : " + getNbVertices()
                + " | Total euclidean dist. : " + getTotalEuclideanDistance());
        System.out.println();
    }

    /**
     * Verify if this path passes throught all stops
     * 
     * @return boolean
     */
    public boolean hasAllStops() {
        if (graph.getEdgesWithStops().isEmpty()) { // if there is no stops
            return true;
        }
        return Collections.indexOfSubList(edges, graph.getEdgesWithStops()) != -1;
    }
}
