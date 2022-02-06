
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.Collections;
import java.util.Comparator;
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
public class Main {

    private static Graph graph;

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        generateGraph();
    }

    private static void generateGraph() throws FileNotFoundException, CloneNotSupportedException {
        Scanner scanner = new Scanner(new File(System.getProperty("user.dir").replace('\\', '/') + "/files/hardJourney.txt"));
        String row = scanner.nextLine();

        if (row.contains("#1")) {
            row = scanner.nextLine();
            graph = new Graph(new Vertice(parseInt(row.split(" ")[0])), new Vertice(parseInt(row.split(" ")[1])));
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

        findAllPaths(graph.getStart().getId(), graph.getEnd().getId());
        displayBestPath();
    }

    /* ---------------- Getters  ---------------- */
    public static Vertice getVerticeFromId(Integer id) {
        return (Vertice) graph.getVertices().stream().filter(ptmp -> ptmp.getId() == id).findAny().orElse(null);
    }

    public static Vertice getFirstVertice(String s) {
        return getVerticeFromId(parseInt(s.split(" ")[0]));
    }

    public static Vertice getSecondVertice(String s) {
        return getVerticeFromId(parseInt(s.split(" ")[1]));
    }

    /* ---------------- Construct a graph  ---------------- */
    private static String addVertices(String s, Scanner scanner) {
        if (s.contains("#3")) {
            return s;
        } else {
            if (getFirstVertice(s) == null) {
                graph.addVertice(new Vertice(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
            } else {
                getFirstVertice(s).setLongitude(Float.parseFloat(s.split(" ")[1]));
                getFirstVertice(s).setLatitude(Float.parseFloat(s.split(" ")[2]));
            }
            return addVertices(scanner.nextLine(), scanner);
        }
    }

    private static String addEdges(String s, Scanner scanner) {
        if (s.contains("#4")) {
            return s;
        } else {
            graph.addEdge(new Edge(getFirstVertice(s), getSecondVertice(s)));
            return addEdges(scanner.nextLine(), scanner);
        }
    }

    private static void setStops(String s, Scanner scanner) {
        if (scanner.hasNextLine() || s.isEmpty()) {
            graph.getEdges().stream().filter(r -> r.getP1().equals(getFirstVertice(s))
                    && r.getP2().equals(getSecondVertice(s)))
                    .findAny().orElse(null)
                    .setStopToTrue();
            setStops(scanner.nextLine(), scanner);
        }
        // for the last edge line in the txt file...
        if (!s.isEmpty()) {
            graph.getEdges().stream().filter(r -> r.getP1().equals(getFirstVertice(s))
                    && r.getP2().equals(getSecondVertice(s)))
                    .findAny().orElse(null)
                    .setStopToTrue();
        }
    }

    /* ---------------- Search paths  ---------------- */
    public static void findAllPaths(int s, int d) throws CloneNotSupportedException {
        boolean[] isVisited = new boolean[graph.getVertices().size()];
        Path path = new Path(graph);
        path.addVertice(getVerticeFromId(s));
        searchPaths(s, d, isVisited, path);
    }

    private static void searchPaths(Integer u, Integer d, boolean[] isVisited, Path p) throws CloneNotSupportedException {
        if (u.equals(d)) {
            graph.addPath((Path) p.clone());
            ((Path) p.clone()).displayPath();
            return;
        }

        isVisited[u] = true;  // error if an id is superior to the length of vertices
        searchAdjVertices(u, d, isVisited, p, 0);
        isVisited[u] = false;
    }

    private static void searchAdjVertices(Integer u, Integer d, boolean[] isVisited, Path p, int index) throws CloneNotSupportedException {
        if (index < graph.getAdjVertices()[u].size()) {
            if (!isVisited[graph.getAdjVertices()[u].get(index)]) {
                p.addVertice(getVerticeFromId(graph.getAdjVertices()[u].get(index)));
                searchPaths(graph.getAdjVertices()[u].get(index), d, isVisited, p);
                p.removeVertice(getVerticeFromId(graph.getAdjVertices()[u].get(index)));
            }
            searchAdjVertices(u, d, isVisited, p, index + 1);
        }
    }

    private static void displayBestPath() {
        System.out.println("\nBest path :");
        Collections.min(graph.getPaths(), Comparator
                .comparing(p -> p.getEdgesWithStops().size() * 30 + p.getTotalEuclideanDistance())).displayPath();
    }
}
