/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ay
 */
public class Point {
    
    private int id;
    
    private float longitude, latitude;
    
    public Point(int id) {
        this.id = id;
    }
    
    public Point(int id, float longitude, float latitude) {
        this.id = id;
        this.longitude = longitude;
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
}
