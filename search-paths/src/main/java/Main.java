
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
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

    private Journey journey;

    public static void main(String[] args) throws FileNotFoundException {

    }

    private void setJourney() throws FileNotFoundException {
        File f = new File("src/files/journey.txt");

        Scanner scanner = new Scanner(f);
        String row = scanner.nextLine();

        if (row.contains("#1")) {
            row = scanner.nextLine();
            setStartPoint(row);
            setEndPoint(row);
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
            row = scanner.nextLine();
            while (!scanner.hasNext()) {
                setStop(row);
                row = scanner.nextLine();
            }
        }
    }

    private void setStartPoint(String s) {
        journey.setStart(new Point(parseInt(s.split(" ")[0])));
    }

    private void setEndPoint(String s) {
        journey.setEnd(new Point(parseInt(s.split(" ")[1])));
    }

    private void addPoints(String s) {
        journey.addPoint(new Point(parseInt(s.split(" ")[0]), Float.parseFloat(s.split(" ")[1]), Float.parseFloat(s.split(" ")[2])));
    }

    private void addRoutes(String s) {
        Point p1 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        Point p2 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[1])).findAny().orElse(null);
        journey.addRoute(new Route(p1, p2));
    }

    private void setStop(String s) {
        Point p1 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[0])).findAny().orElse(null);
        Point p2 = (Point) journey.getPoints().stream().filter(p -> p.getId() == parseInt(s.split(" ")[1])).findAny().orElse(null);

        Route route = (Route) journey.getRoutes().stream().filter(r -> r.getP1().equals(p1)
                && r.getP2().equals(p2)).findAny().orElse(null);
        route.setStopToTrue();
    }
}
