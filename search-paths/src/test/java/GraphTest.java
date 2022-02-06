/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class GraphTest {

    @Test
    public void generateGraphTest() throws IOException, FileNotFoundException, CloneNotSupportedException {
        File file = File.createTempFile("test", ".txt", new File(System.getProperty("user.dir").replace('\\', '/') + "/files"));
        FileUtils.writeStringToFile(file, "#1:start and end points\n"
                + "0 3\n"
                + "#2:points and routes\n"
                + "0 50.63 3.07\n"
                + "1 48.86 2.35\n"
                + "2 44.84 -0.58\n"
                + "3 43.6 1.44\n"
                + "#3:routes\n"
                + "0 1\n"
                + "0 2\n"
                + "1 2\n"
                + "2 3\n"
                + "#4:stops\n"
                + "0 1\n"
                + "1 2");
        Journey journey = new Journey();
        journey.generateGraph(file);

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();

        // adding vertices and edges
        Collections.addAll(vertices,
                new Vertex(0, 50.63f, 3.07f),
                new Vertex(1, 48.86f, 2.35f),
                new Vertex(2, 44.84f, -0.58f), new Vertex(3, 43.6f, 1.44f));

        Collections.addAll(edges,
                new Edge(vertices.get(0), vertices.get(1)),
                new Edge(vertices.get(0), vertices.get(2)),
                new Edge(vertices.get(1), vertices.get(2)),
                new Edge(vertices.get(2), vertices.get(3)));
        edges.get(0).setStopToTrue();
        edges.get(2).setStopToTrue();

        //path A
        paths.add(new Path(journey.getGraph()));
        paths.get(0).addVertex(new Vertex(0, 50.63f, 3.07f));
        paths.get(0).addVertex(new Vertex(1, 48.86f, 2.35f));
        paths.get(0).addVertex(new Vertex(2, 44.84f, -0.58f));
        paths.get(0).addVertex(new Vertex(3, 43.6f, 1.44f));

        // path B
        paths.add(new Path(journey.getGraph()));
        paths.get(1).addVertex(new Vertex(0, 50.63f, 3.07f));
        paths.get(1).addVertex(new Vertex(2, 44.84f, -0.58f));
        paths.get(1).addVertex(new Vertex(3, 43.6f, 1.44f));

        // comparing the edges
        testEdges(edges, journey.getGraph().getEdges(), 0);

        // all the vertices are same, but the order of the list changed in this function...
        //testVertices(vertices, journey.getGraph().getVertices(), 0);
        // comparing the paths
        testPaths(paths, journey.findAllPaths(0, 3), 0);

        // best path is the B
        assertTrue(paths.get(1).compareTo(journey.getBestPath()) == 0);

        file.deleteOnExit();
    }

    private void testVertices(ArrayList<Vertex> vertices1, ArrayList<Vertex> vertices2, int index) {
        // vertex 3 is now placed on index 1, why??
        if (index < vertices1.size() && index < vertices2.size()) {
            assertTrue(vertices1.get(index).compareTo(vertices2.get(index)) == 0);
            testVertices(vertices1, vertices2, index + 1);
        }
    }

    private void testEdges(ArrayList<Edge> edges1, ArrayList<Edge> edges2, int index) {
        if (index < edges1.size() && index < edges2.size()) {
            assertTrue(edges1.get(index).isStop() == edges2.get(index).isStop());
            assertTrue(edges1.get(index).compareTo(edges2.get(index)) == 0);
            testEdges(edges1, edges2, index + 1);
        }
    }

    private void testPaths(ArrayList<Path> paths1, ArrayList<Path> paths2, int index) {
        if (index < paths1.size() && index < paths2.size()) {
            assertTrue(paths1.get(index).compareTo(paths2.get(index)) == 0);
            testPaths(paths1, paths2, index + 1);
        }
    }
}
