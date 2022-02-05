
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
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
public class Main {

    private static Graph graph;

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        buildGraphFromFile();
    }

    private static void buildGraphFromFile() throws FileNotFoundException, CloneNotSupportedException {
        String path = System.getProperty("user.dir").replace('\\', '/') + "/files/journey.txt";
        File f = new File(path);
        Scanner scanner = new Scanner(f);
        String row = scanner.nextLine();

        if (row.contains("#1")) {
            row = scanner.nextLine();
            graph = new Graph(getStartPoint(row), getEndPoint(row));
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

    private static Point getStartPoint(String s) {
        return new Point(parseInt(s.split(" ")[0]));
    }

    private static Point getEndPoint(String s) {
        return new Point(parseInt(s.split(" ")[1]));
    }

    private static void addPoints(String s) {
        Point point = (Point) graph.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        if (point == null) {
            graph.addPoint(new Point(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
        } else {
            point.setLongitude(Float.parseFloat(s.split(" ")[1]));
            point.setLatitude(Float.parseFloat(s.split(" ")[2]));
        }
    }

    private static void addEdge(String s) {
        Point p1 = (Point) graph.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        Point p2 = (Point) graph.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[1])).findAny().orElse(null);
        graph.addEdge(new Edge(p1, p2));
    }

    private static void setStop(String s) {
        Point p1 = (Point) graph.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        Point p2 = (Point) graph.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[1])).findAny().orElse(null);

        Edge edge = (Edge) graph.getEdges().stream().filter(r -> r.getP1().equals(p1)
                && r.getP2().equals(p2)).findAny().orElse(null);
        edge.setStopToTrue();
    }

    private static void bestPath() {
        System.out.println("\nBest path :");
        Collections.min(graph.getPaths(), Comparator.comparing(p -> p.getNbPoints() + p.getEdgesWithStops().size() + p.getTotalEuclideanDistance())).displayPath();
    }

    public static void getAllPaths(int s, int d) throws CloneNotSupportedException {
        boolean[] isVisited = new boolean[graph.getPoints().size()];
        Path path = new Path(graph);
        path.addPoint(graph.getPoint(s));
        searchAllPaths(s, d, isVisited, path);
        bestPath();
    }

    private static void searchAllPaths(Integer u, Integer d, boolean[] isVisited, Path p) throws CloneNotSupportedException {
        if (u.equals(d)) {
            Path p2 = (Path) p.clone();
            graph.addPath(p2);
            p2.displayPath();
            return;
        }

        isVisited[u] = true;
        for (Integer i : graph.getAdjList()[u]) {
            if (!isVisited[i]) {
                p.addPoint(graph.getPoint(i));
                searchAllPaths(i, d, isVisited, p);
                p.removePoint(graph.getPoint(i));
            }
        }
        isVisited[u] = false;
    }
}
