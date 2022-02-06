/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ay
 */
public class Vertex implements Comparable<Vertex>{
    
    private int id;
    
    private float longitude, latitude;
    
    public Vertex(int id) {
        this.id = id;
    }
    
    public Vertex(int id, float longitude, float latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    
    public float getLongitude() {
        return longitude;
    }
    
    public float getLatitude() {
        return latitude;
    }
    
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Vertex o) {
        return (id - o.getId());
    }
}
