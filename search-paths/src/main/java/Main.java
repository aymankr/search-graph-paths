
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
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

    private static Journey journey;

    public static void main(String[] args) throws FileNotFoundException {
        initializeJourney();
    }

    private static void initializeJourney() throws FileNotFoundException {
        String path = System.getProperty("user.dir").replace('\\', '/') + "/files/journey.txt";
        File f = new File(path);
        Scanner scanner = new Scanner(f);
        String row = scanner.nextLine();

        if (row.contains("#1")) {
            row = scanner.nextLine();
            journey = new Journey(getStartPoint(row), getEndPoint(row));
            row = scanner.nextLine();
        }

        if (row.contains("#2")) {
            row = scanner.nextLine();
            while (!row.contains("#3")) {
                addPoints(row);
                row = scanner.nextLine();
            }
        }
        if (row.contains("#3")) {
            row = scanner.nextLine();
            while (!row.contains("#4")) {
                addRoutes(row);
                row = scanner.nextLine();
            }
        }
        if (row.contains("#4")) {
            while (scanner.hasNextLine()) {
                row = scanner.nextLine();
                setStop(row);
            }
        }
    }

    private static Point getStartPoint(String s) {
        return new Point(parseInt(s.split(" ")[0]));
    }

    private static Point getEndPoint(String s) {
        return new Point(parseInt(s.split(" ")[1]));
    }

    private static void addPoints(String s) {
        Point point = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        if (point == null) {
            journey.addPoint(new Point(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
        } else {
            point.setLongitude(Float.parseFloat(s.split(" ")[1]));
            point.setLatitude(Float.parseFloat(s.split(" ")[2]));
        }
    }

    private static void addRoutes(String s) {
        Point p1 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        Point p2 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[1])).findAny().orElse(null);
        journey.addRoute(new Route(p1, p2));
    }

    private static void setStop(String s) {
        Point p1 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        Point p2 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[1])).findAny().orElse(null);

        Route route = (Route) journey.getRoutes().stream().filter(r -> r.getP1().equals(p1)
                && r.getP2().equals(p2)).findAny().orElse(null);
        route.setStopToTrue();
    }
}
