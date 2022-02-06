
/**
 * Class Edge : link between two vertices
 * @author ay
 */
public class Edge implements Comparable<Edge> {

    private final Vertex v1, v2;
    private boolean stop;

    /**
     * Construct an edge linked by vertices v1 and v2
     * @param v1
     * @param v2 
     */
    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    /**
     * Compare two edges
     * @param e
     * @return returns 0 if the sources are same
     */
    @Override
    public int compareTo(Edge e) {
        return v1.getId() - e.getP1().getId();
    }

    /**
     * get source
     * @return 
     */
    public Vertex getP1() {
        return v1;
    }

    /**
     * get destination
     * @return 
     */
    public Vertex getP2() {
        return v2;
    }

    /**
     * Get euclidean distance of this edge
     * @return distance value
     */
    public int getLength() {
        return (int) Math.sqrt(Math.pow(v1.getLatitude() - v1.getLongitude(), 2) + Math.pow(v2.getLatitude() - v2.getLongitude(), 2));
    }
    
    /**
     * Return true if this edge is a stop
     * @return 
     */
    public boolean isStop() {
        return stop;
    }
    
    /**
     * Set the "stop" boolean to true
     */
    public void setStopToTrue() {
        stop = true;
    }
}
