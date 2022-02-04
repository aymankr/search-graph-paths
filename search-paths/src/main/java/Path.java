
import java.util.ArrayList;
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
public class Path {

    private ArrayList<Point> points;

    public Path () {
        points = new ArrayList<>();
    }    
    public int getLength() {
        return points.size();
    }
/*
    public ArrayList<Point> getStops() {
        return (ArrayList<Point>) points.stream().filter(r -> r.isStop()).collect(Collectors.toList());
    }
*/
    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point e) {
        points.add(e);
    }

    public void removePoint(Point e) {
        points.remove(e);
    }
    
        public void displayPath() {
        points.forEach(p -> {
            System.out.print(p.getId() + " ");
        });
        System.out.println();
    }
}
