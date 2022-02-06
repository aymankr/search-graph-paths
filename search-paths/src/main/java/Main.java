
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class
 * @author ay
 */
public class Main {
    private static Journey j;

    /**
     * Start the journey and find the paths
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws CloneNotSupportedException 
     */
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException, IOException {
        j = new Journey();
        j.generateGraph(new File(System.getProperty("user.dir").replace('\\', '/') + "/files/journey.txt"));
        j.findAllPaths(j.getGraph().getSource().getId(), j.getGraph().getDestination().getId());
        j.getBestPath().displayPath();
    }
}
