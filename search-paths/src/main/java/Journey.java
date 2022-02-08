
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author ay
 */
public class Journey {

    private Graph graph;

    /**
     * Construct a graph with attributes from a txt file, this function read
     * line by line the txt. The user must respects the syntax of the txt file, and must put
     * at least 1 of each kind of attributes : start, end, vertices, edges, stops
     *
     * @param file
     * @throws FileNotFoundException
     * @throws CloneNotSupportedException
     */
    public void generateGraph(File file) throws FileNotFoundException, CloneNotSupportedException, IOException {
        Scanner sc = new Scanner(file);
        String row = sc.nextLine();
        System.out.println(FileUtils.readFileToString(file, StandardCharsets.UTF_8) + "\n");

        if (row.contains("#1")) {
            row = sc.nextLine();
            graph = new Graph(new Vertex(parseInt(row.split(" ")[0])), new Vertex(parseInt(row.split(" ")[1])));
            row = sc.nextLine();
        }

        if (row.contains("#2")) {
            row = addVertices(sc.nextLine(), sc);
            graph.initAdjVertices(0);
        }
        if (row.contains("#3")) {
            row = addEdges(sc.nextLine(), sc);
        }
        if (row.contains("#4")) {
            setStops(sc.nextLine(), sc);
        }
    }

    /* ---------------- Getters  ---------------- */
    /**
     * Get the graph
     *
     * @return
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Get a vertex from his id
     *
     * @param id id
     * @return id value
     */
    public Vertex getVertexFromId(int id) {
        return (Vertex) graph.getVertices().stream().filter(v -> v.getId() == id).findAny().orElse(null);
    }

    /**
     * Get the first vertex of the row ex : "0 2 4 4 ...etc" will return 0
     *
     * @param row row
     * @return a vertex
     */
    public Vertex getFirstVertex(String row) {
        return getVertexFromId(parseInt(row.split(" ")[0]));
    }

    /**
     * Return the second vertex of the row
     *
     * @param row
     * @return
     */
    public Vertex getSecondVertex(String row) {
        return getVertexFromId(parseInt(row.split(" ")[1]));
    }

    /* ---------------- Construct a graph  ---------------- */
    /**
     * Add vertices with recursion by reading line by line, if the vertex
     * doesn't exist add it, else set the longitude and latitude (this case is
     * for the source and the destination vertices) then return the current row
     * read, to let nexts methods to get this row and to continue to read the
     * txt file in generateGraphFromFile method
     *
     * @param row row
     * @param sc scanner
     * @return return the last row read
     */
    private String addVertices(String row, Scanner sc) {
        if (row.contains("#3")) {
            return row;
        } else {
            if (getFirstVertex(row) == null) {
                graph.addVertex(new Vertex(parseInt(row.split(" ")[0]), Float.parseFloat(row.split(" ")[1]), Float.parseFloat(row.split(" ")[2])));
            } else {
                getFirstVertex(row).setLongitude(Float.parseFloat(row.split(" ")[1]));
                getFirstVertex(row).setLatitude(Float.parseFloat(row.split(" ")[2]));
            }
            return addVertices(sc.nextLine(), sc);
        }
    }

    /**
     * Add edges with recursion, same principe as addVertices method
     *
     * @param row
     * @param sc
     * @return
     */
    private String addEdges(String row, Scanner sc) {
        if (row.contains("#4")) {
            return row;
        } else {
            graph.addEdge(new Edge(getFirstVertex(row), getSecondVertex(row)));
            return addEdges(sc.nextLine(), sc);
        }
    }

    /**
     * Set stops with recursion
     *
     * @param row
     * @param scanner
     */
    private void setStops(String row, Scanner scanner) {
        if (scanner.hasNextLine()) {
            graph.getEdges().stream().filter(e -> e.getP1().equals(getFirstVertex(row))
                    && e.getP2().equals(getSecondVertex(row)))
                    .findAny().orElse(null)
                    .setStopToTrue();
            setStops(scanner.nextLine(), scanner);
        }
        // for the last edge line in the txt file...
        graph.getEdges().stream().filter(e -> e.getP1().equals(getFirstVertex(row))
                && e.getP2().equals(getSecondVertex(row)))
                .findAny().orElse(null)
                .setStopToTrue();
    }

    /* ---------------- Search paths  ---------------- */
    /**
     * Get all the paths of this graph, given a source and a destination
     *
     * @param s source
     * @param d destination
     * @return list of all paths
     * @throws CloneNotSupportedException
     */
    public ArrayList<Path> findAllPaths(int s, int d) throws CloneNotSupportedException {
        boolean[] isVisited = new boolean[graph.getVertices().size()];
        Path path = new Path(graph);
        path.addVertex(getVertexFromId(s));
        searchPaths(s, d, isVisited, path);
        return graph.getPaths();
    }

    /**
     * Search and add the path to the graph, given a source, destination
     * isVisited (array of booleans) is there to check if vertices are visited,
     * their index matches their id. If they are not visited then we can add
     * them to the path. Run the function searchAdjacentVertex to find all the
     * vertices linked to the u vertex, then verify if u correspond to d and add
     * this path to the graph. This method runs recursively until getting all
     * the paths and adding them in the graph
     *
     * @param u source
     * @param d destination
     * @param isVisited array of booleans
     * @param p current path
     * @throws CloneNotSupportedException
     */
    private void searchPaths(int u, int d, boolean[] isVisited, Path p) throws CloneNotSupportedException {
        if (u == d) {
            graph.addPath((Path) p.clone());
            ((Path) p.clone()).displayPath();
            return;
        }

        isVisited[u] = true;  // error if an id is superior to the length of vertices
        searchAdjacentVertex(u, d, isVisited, (Path) p.clone(), 0);
        isVisited[u] = false;
    }

    /**
     * If the vertex linked with the u vertex is not visited, he's added to the
     * current path, and run it recursively. When the recursion finishes, remove
     * the last vertex added, then check if its previous vertex has other
     * adjacent ones by recalling the method to index + 1, and run this
     * recursively to rebuild a new path
     *
     * @param u source
     * @param d destination
     * @param isVisited array of booleans
     * @param p current path
     * @param index current index to have the current adjacent vertex
     * @throws CloneNotSupportedException
     */
    private void searchAdjacentVertex(int u, int d, boolean[] isVisited, Path p, int index) throws CloneNotSupportedException {
        ArrayList<Integer> adj = graph.getAdjVertices()[u];
        if (index < adj.size()) {
            if (!isVisited[adj.get(index)]) {
                p.addVertex(getVertexFromId(adj.get(index)));
                searchPaths(adj.get(index), d, isVisited, p);
                p.removeVertex(getVertexFromId(adj.get(index)));
            }
            searchAdjacentVertex(u, d, isVisited, p, index + 1);
        }
    }
    
    /**
     * Find the best path, comparing the weight of stops, and the euclidean
     * distance
     *
     * @return return who has the minimum
     */
    public Path getBestPath() {
        System.out.println("\nBest path :");
        return Collections.min(graph.getPaths(), Comparator
                .comparing(p -> p.getEdgesWithStops().size() * 30 + p.getTotalEuclideanDistance()));
    }
}
