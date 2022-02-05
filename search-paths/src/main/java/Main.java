
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
        Scanner scanner = new Scanner(new File(System.getProperty("user.dir").replace('\\', '/') + "/files/journey.txt"));
        String row = scanner.nextLine();

        if (row.contains("#1")) {
            row = scanner.nextLine();
            graph = new Graph(new Vertice(parseInt(row.split(" ")[0])), new Vertice(parseInt(row.split(" ")[1])));
            row = scanner.nextLine();
        }

        if (row.contains("#2")) {
            row = scanner.nextLine();
            while (!row.contains("#3")) {
                addVertices(row);
                row = scanner.nextLine();
            }
            graph.initAdjList();
        }
        if (row.contains("#3")) {
            row = scanner.nextLine();
            while (!row.contains("#4")) {
                addEdge(row);
                row = scanner.nextLine();
            }
        }
        if (row.contains("#4")) {
            while (scanner.hasNextLine()) {
                row = scanner.nextLine();
                setStop(row);
            }
        }

        findAllPaths(graph.getStart().getId(), graph.getEnd().getId());
        displayBestPath();
    }

    public static Vertice getVerticeFromId(Integer id) {
        return (Vertice) graph.getVertices().stream().filter(ptmp -> ptmp.getId() == id).findAny().orElse(null);
    }

    public static Vertice getFirstVertice(String s) {
        return getVerticeFromId(parseInt(s.split(" ")[0]));
    }

    public static Vertice getSecondVertice(String s) {
        return getVerticeFromId(parseInt(s.split(" ")[1]));
    }

    private static void addVertices(String s) {
        if (getFirstVertice(s) == null) {
            graph.addVertice(new Vertice(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
        } else {
            getFirstVertice(s).setLongitude(Float.parseFloat(s.split(" ")[1]));
            getFirstVertice(s).setLatitude(Float.parseFloat(s.split(" ")[2]));
        }
    }

    private static void addEdge(String s) {
        graph.addEdge(new Edge(getFirstVertice(s), getSecondVertice(s)));
    }

    private static void setStop(String s) {
        graph.getEdges().stream().filter(r -> r.getP1().equals(getFirstVertice(s))
                && r.getP2().equals(getSecondVertice(s)))
                .findAny().orElse(null)
                .setStopToTrue();
    }

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

        isVisited[u] = true;
        for (Integer i : graph.getAdjList()[u]) {
            if (!isVisited[i]) {
                p.addVertice(getVerticeFromId(i));
                searchPaths(i, d, isVisited, p);
                p.removeVertice(getVerticeFromId(i));
            }
        }
        isVisited[u] = false;
    }

    private static void displayBestPath() {
        System.out.println("\nBest path :");
        Collections.min(graph.getPaths(), Comparator.comparing(p -> p.getNbVertices() + p.getEdgesWithStops().size() * 10 + p.getTotalEuclideanDistance())).displayPath();
    }
}
