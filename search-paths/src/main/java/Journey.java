
import java.util.ArrayList;

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

    private final Point start;
    private final Point end;

    private final ArrayList<Route> routes;

    private final ArrayList<Point> points;

    public Journey(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.routes = new ArrayList<>();
        this.points = new ArrayList<>();
        points.add(start);
        points.add(end);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public void addRoute(Route r) {
        routes.add(r);
    }
    
    
}
