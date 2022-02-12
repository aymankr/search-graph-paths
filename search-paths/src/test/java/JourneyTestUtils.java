
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ay
 */
public class JourneyTestUtils {

    /**
     * Compare the vertices of the list with the vertices of the graph. the list
     * of the Graph vertices is in the disorder, and therefore the id also. So
     * we use the getVertexFromId method to compare in the same order
     *
     * @param vertices
     * @param index
     * @param j
     */
    public static void testVertices(ArrayList<Vertex> vertices, int index, Journey j) {
        if (index < vertices.size() && index < j.getGraph().getVertices().size()) {
            assertTrue(vertices.get(index).compareTo(j.getVertexFromId(index)) == 0);
            testVertices(vertices, index + 1, j);
        }
    }

    /**
     * Compare all the edges, if 2 edges are same, compareTo returns 0
     *
     * @param edges1 list 1
     * @param edges2 list 2
     * @param index
     */
    public static void testEdges(ArrayList<Edge> edges1, ArrayList<Edge> edges2, int index) {
        if (index < edges1.size() && index < edges2.size()) {
            assertTrue(edges1.get(index).compareTo(edges2.get(index)) == 0);
            testEdges(edges1, edges2, index + 1);
        }
    }

    /**
     * Compare all the paths
     *
     * @param paths1 list 1
     * @param paths2 list 2
     * @param index
     */
    public static void testPaths(ArrayList<Path> paths1, ArrayList<Path> paths2, int index) {
        if (index < paths1.size() && index < paths2.size()) {
            assertTrue(paths1.get(index).compareTo(paths2.get(index)) == 0);
            testPaths(paths1, paths2, index + 1);
        }
    }
}
