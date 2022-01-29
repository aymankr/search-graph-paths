
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

    private ArrayList<Route> routes;

    public int getLength() {
        return routes.size();
    }

    public ArrayList<Route> getStops() {
        return (ArrayList<Route>) routes.stream().filter(r -> r.isStop()).collect(Collectors.toList());
    }
}
