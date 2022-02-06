
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

    private static Journey journey;

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        journey = new Journey();
        journey.generateGraph(new File(System.getProperty("user.dir").replace('\\', '/') + "/files/hardJourney.txt"));
        journey.findAllPaths(journey.getGraph().getStart().getId(), journey.getGraph().getEnd().getId());
        journey.getBestPath().displayPath();
    }
}
