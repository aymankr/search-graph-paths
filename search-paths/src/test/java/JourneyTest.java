
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author ay
 */
public class JourneyTest {

    /**
     * Testing a journey without stop
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws CloneNotSupportedException
     */
    @Test
    public void journeyWithoutStopTest() throws IOException, FileNotFoundException, CloneNotSupportedException {
        // creating a temporary test file
        File file = File.createTempFile("test", ".txt", new File(System.getProperty("user.dir").replace('\\', '/') + "/files"));

        // edit the text file
        FileUtils.writeStringToFile(file, "#1:start and end points\n"
                + "0 5\n"
                + "#2:points and routes\n"
                + "0 50.63 3.07 # Lille\n"
                + "1 48.86 2.35 # Paris\n"
                + "2 44.84 -0.58 # Bordeaux\n"
                + "3 45.76 4.84 # Lyon\n"
                + "4 43.3 5.37 # Marseille\n"
                + "5 43.6 1.44 # Toulouse\n"
                + "#3:routes\n"
                + "0 1 # route from Lille to Paris\n"
                + "1 2\n"
                + "1 3\n"
                + "0 2\n"
                + "3 4\n"
                + "4 3\n"
                + "2 5\n"
                + "#4:no stops");

        // variables
        Journey journey = new Journey();
        journey.generateGraph(file);

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();

        // adding vertices and edges
        Collections.addAll(vertices,
                new Vertex(0, 50.63f, 3.07f),
                new Vertex(1, 48.86f, 2.35f),
                new Vertex(2, 44.84f, -0.58f),
                new Vertex(3, 45.76f, 4.84f),
                new Vertex(4, 43.3f, 5.37f),
                new Vertex(5, 43.6f, 1.44f));

        Collections.addAll(edges,
                new Edge(vertices.get(0), vertices.get(1)),
                new Edge(vertices.get(1), vertices.get(2)),
                new Edge(vertices.get(1), vertices.get(3)),
                new Edge(vertices.get(0), vertices.get(2)),
                new Edge(vertices.get(3), vertices.get(4)),
                new Edge(vertices.get(4), vertices.get(3)),
                new Edge(vertices.get(2), vertices.get(5)));

        // comparing the edges
        JourneyTestUtils.testEdges(edges, journey.getGraph().getEdges(), 0);

        JourneyTestUtils.testVertices(vertices, 0, journey);
        // possible paths
        // path A
        paths.add(new Path(journey.getGraph()));
        paths.get(0).addVertex(vertices.get(0));
        paths.get(0).addVertex(vertices.get(1));
        paths.get(0).addVertex(vertices.get(2));
        paths.get(0).addVertex(vertices.get(5));

        // path B
        paths.add(new Path(journey.getGraph()));
        paths.get(1).addVertex(vertices.get(0));
        paths.get(1).addVertex(vertices.get(2));
        paths.get(1).addVertex(vertices.get(5));

        // comparing the all the possible paths
        JourneyTestUtils.testPaths(paths, journey.findAllPaths(0, 5), 0);

        // verify if the best path is the B
        assertTrue(paths.get(1).compareTo(journey.getBestPath()) == 0);

        // delete temporary test file
        file.deleteOnExit();
    }

    /**
     * Testing a journey without stop
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws CloneNotSupportedException
     */
    @Test
    public void journeyWithStopTest() throws IOException, FileNotFoundException, CloneNotSupportedException {
        // creating a temporary test file
        File file = File.createTempFile("test", ".txt", new File(System.getProperty("user.dir").replace('\\', '/') + "/files"));

        // edit the text file
        FileUtils.writeStringToFile(file, "#1:start and end points\n"
                + "0 2\n"
                + "#2:points and routes\n"
                + "0 0.5 10.2 # id lat lon\n"
                + "1 10.1 10.5\n"
                + "2 10.5 20.3\n"
                + "#3:routes\n"
                + "0 1 # route from 0 to 1\n"
                + "1 2\n"
                + "2 0\n"
                + "#4:stops\n"
                + "1 2");

        // variables
        Journey journey = new Journey();
        journey.generateGraph(file);

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();

        // adding vertices and edges
        Collections.addAll(vertices,
                new Vertex(0, 0.5f, 10.2f),
                new Vertex(1, 10.1f, 10.5f),
                new Vertex(2, 10.5f, 20.3f));

        Collections.addAll(edges,
                new Edge(vertices.get(0), vertices.get(1)),
                new Edge(vertices.get(1), vertices.get(2)), // (1 2) edge is a stop
                new Edge(vertices.get(2), vertices.get(0)));
        edges.get(1).setStopToTrue();

        // comparing the edges
        JourneyTestUtils.testEdges(edges, journey.getGraph().getEdges(), 0);

        JourneyTestUtils.testVertices(vertices, 0, journey);
        // possible path
        paths.add(new Path(journey.getGraph()));
        paths.get(0).addVertex(vertices.get(0));
        paths.get(0).addVertex(vertices.get(1));
        paths.get(0).addVertex(vertices.get(2));

        // comparing the all the possible paths
        JourneyTestUtils.testPaths(paths, journey.findAllPaths(0, 2), 0);

        // verify if the best path is the B because there is a stop in (1 2)
        assertTrue(paths.get(0).compareTo(journey.getBestPath()) == 0);

        // delete temporary test file
        file.deleteOnExit();
    }
}
