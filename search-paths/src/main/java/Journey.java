
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ay
 */
public class Journey {

    private Graph graph;

    public void generateGraph(File file) throws FileNotFoundException, CloneNotSupportedException {
        Scanner scanner = new Scanner(file);
        String row = scanner.nextLine();

        if (row.contains("#1")) {
            row = scanner.nextLine();
            graph = new Graph(new Vertex(parseInt(row.split(" ")[0])), new Vertex(parseInt(row.split(" ")[1])));
            row = scanner.nextLine();
        }

        if (row.contains("#2")) {
            row = addVertices(scanner.nextLine(), scanner);
            graph.initAdjVertices(0);
        }
        if (row.contains("#3")) {
            row = addEdges(scanner.nextLine(), scanner);
        }
        if (row.contains("#4")) {
            setStops(scanner.nextLine(), scanner);
        }
    }

    /* ---------------- Getters  ---------------- */
    
    public Graph getGraph() {
        return graph;
    }

    public Vertex getVertexFromId(Integer id) {
        return (Vertex) graph.getVertices().stream().filter(ptmp -> ptmp.getId() == id).findAny().orElse(null);
    }

    public Vertex getFirstVertex(String s) {
        return getVertexFromId(parseInt(s.split(" ")[0]));
    }

    public Vertex getSecondVertex(String s) {
        return getVertexFromId(parseInt(s.split(" ")[1]));
    }

    /* ---------------- Construct a graph  ---------------- */
    private String addVertices(String s, Scanner scanner) {
        if (s.contains("#3")) {
            return s;
        } else {
            if (getFirstVertex(s) == null) {
                graph.addVertex(new Vertex(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
            } else {
                getFirstVertex(s).setLongitude(Float.parseFloat(s.split(" ")[1]));
                getFirstVertex(s).setLatitude(Float.parseFloat(s.split(" ")[2]));
            }
            return addVertices(scanner.nextLine(), scanner);
        }
    }

    private String addEdges(String s, Scanner scanner) {
        if (s.contains("#4")) {
            return s;
        } else {
            graph.addEdge(new Edge(getFirstVertex(s), getSecondVertex(s)));
            return addEdges(scanner.nextLine(), scanner);
        }
    }

    private void setStops(String s, Scanner scanner) {
        if (scanner.hasNextLine() || s.isEmpty()) {
            graph.getEdges().stream().filter(r -> r.getP1().equals(getFirstVertex(s))
                    && r.getP2().equals(getSecondVertex(s)))
                    .findAny().orElse(null)
                    .setStopToTrue();
            setStops(scanner.nextLine(), scanner);
        }
        // for the last edge line in the txt file...
        if (!s.isEmpty()) {
            graph.getEdges().stream().filter(r -> r.getP1().equals(getFirstVertex(s))
                    && r.getP2().equals(getSecondVertex(s)))
                    .findAny().orElse(null)
                    .setStopToTrue();
        }
    }

    /* ---------------- Search paths  ---------------- */
    public ArrayList<Path> findAllPaths(int s, int d) throws CloneNotSupportedException {
        boolean[] isVisited = new boolean[graph.getVertices().size()];
        Path path = new Path(graph);
        path.addVertex(getVertexFromId(s));
        searchPaths(s, d, isVisited, path);
        return graph.getPaths();
    }

    private void searchPaths(Integer u, Integer d, boolean[] isVisited, Path p) throws CloneNotSupportedException {
        if (u.equals(d)) {
            graph.addPath((Path) p.clone());
            ((Path) p.clone()).displayPath();
            return;
        }

        isVisited[u] = true;  // error if an id is superior to the length of vertices
        searchAdjVertices(u, d, isVisited, p, 0);
        isVisited[u] = false;
    }

    private void searchAdjVertices(Integer u, Integer d, boolean[] isVisited, Path p, int index) throws CloneNotSupportedException {
        if (index < graph.getAdjVertices()[u].size()) {
            if (!isVisited[graph.getAdjVertices()[u].get(index)]) {
                p.addVertex(getVertexFromId(graph.getAdjVertices()[u].get(index)));
                searchPaths(graph.getAdjVertices()[u].get(index), d, isVisited, p);
                p.removeVertex(getVertexFromId(graph.getAdjVertices()[u].get(index)));
            }
            searchAdjVertices(u, d, isVisited, p, index + 1);
        }
    }

    public Path getBestPath() {
        System.out.println("\nBest path :");
        return Collections.min(graph.getPaths(), Comparator
                .comparing(p -> p.getEdgesWithStops().size() * 30 + p.getTotalEuclideanDistance()));
    }
}
