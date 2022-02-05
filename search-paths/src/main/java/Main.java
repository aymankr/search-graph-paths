
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
            graph = new Graph(new Point(parseInt(row.split(" ")[0])), new Point(parseInt(row.split(" ")[1])));
            row = scanner.nextLine();
        }

        if (row.contains("#2")) {
            row = scanner.nextLine();
            while (!row.contains("#3")) {
                addPoints(row);
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

        getAllPaths(graph.getStart().getId(), graph.getEnd().getId());
    }

    public static Point getPointFromId(Integer id) {
        return (Point) graph.getPoints().stream().filter(ptmp -> ptmp.getId() == id).findAny().orElse(null);
    }

    public static Point getFirstPoint(String s) {
        return getPointFromId(parseInt(s.split(" ")[0]));
    }

    public static Point getSecondPoint(String s) {
        return getPointFromId(parseInt(s.split(" ")[1]));
    }

    private static void addPoints(String s) {
        if (getFirstPoint(s) == null) {
            graph.addPoint(new Point(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
        } else {
            getFirstPoint(s).setLongitude(Float.parseFloat(s.split(" ")[1]));
            getFirstPoint(s).setLatitude(Float.parseFloat(s.split(" ")[2]));
        }
    }

    private static void addEdge(String s) {
        graph.addEdge(new Edge(getFirstPoint(s), getSecondPoint(s)));
    }

    private static void setStop(String s) {
        graph.getEdges().stream().filter(r -> r.getP1().equals(getFirstPoint(s))
                && r.getP2().equals(getSecondPoint(s)))
                .findAny().orElse(null)
                .setStopToTrue();
    }

    private static void bestPath() {
        System.out.println("\nBest path :");
        Collections.min(graph.getPaths(), Comparator.comparing(p -> p.getNbPoints() + p.getEdgesWithStops().size() * 10 + p.getTotalEuclideanDistance())).displayPath();
    }

    public static void getAllPaths(int s, int d) throws CloneNotSupportedException {
        boolean[] isVisited = new boolean[graph.getPoints().size()];
        Path path = new Path(graph);
        path.addPoint(getPointFromId(s));
        searchAllPaths(s, d, isVisited, path);
        bestPath();
    }

    private static void searchAllPaths(Integer u, Integer d, boolean[] isVisited, Path p) throws CloneNotSupportedException {
        if (u.equals(d)) {
            graph.addPath((Path) p.clone());
            ((Path) p.clone()).displayPath();
            return;
        }

        isVisited[u] = true;
        for (Integer i : graph.getAdjList()[u]) {
            if (!isVisited[i]) {
                p.addPoint(getPointFromId(i));
                searchAllPaths(i, d, isVisited, p);
                p.removePoint(getPointFromId(i));
            }
        }
        isVisited[u] = false;
    }
}
