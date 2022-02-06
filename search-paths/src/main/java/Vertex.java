
/**
 * Class Vertex : a point in the graph
 * @author ay
 */
public class Vertex implements Comparable<Vertex> {

    private int id;
    private float longitude, latitude;

    /**
     * Construct a vertex with only id
     * @param id id number
     */
    public Vertex(int id) {
        this.id = id;
    }

    /**
     * Construct a vertex with id, longitude, latitude
     * @param id
     * @param longitude
     * @param latitude 
     */
    public Vertex(int id, float longitude, float latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Compare two vertices
     * @param o other vertice
     * @return 0 if they are same
     */
    @Override
    public int compareTo(Vertex o) {
        return id - o.getId();
    }

    /**
     * Get the longitude
     * @return 
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Get the latitude
     * @return 
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Get id
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Set id value of this vertex
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set longitude
     * @param longitude 
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Set latitude
     * @param latitude 
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
