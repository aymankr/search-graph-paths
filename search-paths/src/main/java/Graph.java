
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class Graph : a graph is composed by a source, a destination, list of edges,
 * vertices, paths and the list of adjacency of vertices
 *
 * @author ay
 */
public class Graph {

    private final Vertex source, destination;

    private final ArrayList<Edge> edges;
    private final ArrayList<Vertex> vertices;
    private final ArrayList<Path> paths;

    private ArrayList<Integer>[] adjacentVertices;

    /**
     * Construct a graph
     *
     * @param source
     * @param destination
     */
    public Graph(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
        this.edges = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.paths = new ArrayList<>();
        vertices.add(source);
        vertices.add(destination);
    }

    /**
     * Initialize the list of the adjacency of vertices, with recursion
     *
     * @param i
     */
    public void initAdjVertices(int i) {
        if (i < vertices.size()) {
            if (i == 0) {
                adjacentVertices = new ArrayList[vertices.size()];
            }
            adjacentVertices[i] = new ArrayList<>();
            initAdjVertices(i + 1);
        }
    }

    /**
     * Get source vertex
     *
     * @return
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Get destination vertex
     *
     * @return
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * Get list of edges of this graph
     *
     * @return
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Vertices
     *
     * @return
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Paths
     *
     * @return
     */
    public ArrayList<Path> getPaths() {
        return paths;
    }

    /**
     * List of adjacent vertices
     *
     * @return
     */
    public ArrayList<Integer>[] getAdjVertices() {
        return adjacentVertices;
    }

    /**
     * Add a vertex in the list of vertices, of this graph
     *
     * @param v vertex
     */
    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    /**
     * Add an edge
     *
     * @param e edge
     */
    public void addEdge(Edge e) {
        edges.add(e);
        //two directions
        adjacentVertices[e.getP1().getId()].add(e.getP2().getId());
        adjacentVertices[e.getP2().getId()].add(e.getP1().getId());
    }

    /**
     * Add a path
     *
     * @param p path
     */
    public void addPath(Path p) {
        paths.add(p);
    }

    /**
     * Get all the edges they have a stop
     *
     * @return
     */
    public ArrayList<Edge> getEdgesWithStops() {
        return (ArrayList<Edge>) edges.stream().filter(p -> p.isStop()).collect(Collectors.toList());
    }
}
