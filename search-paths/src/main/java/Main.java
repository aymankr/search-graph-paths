
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class
 *
 * @author ay
 */
public class Main {

    /**
     * Start the journey and find the paths
     *
     * @param args
     * @throws FileNotFoundException
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException, IOException {
        Journey j = new Journey();
        j.generateGraph(new File(System.getProperty("user.dir").replace('\\', '/') + "/files/easyJourney.txt"));
        j.findAllPaths(j.getGraph().getSource().getId(), j.getGraph().getDestination().getId());
        if (!j.getGraph().getPaths().isEmpty()) {
            j.getBestPath().displayPath();
        } else {
            System.out.println("No path can pass through these stops.");
        }
    }
}
