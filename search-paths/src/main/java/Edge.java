/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ay
 */
public class Edge {
    
    private final Point p1;
    private final Point p2;
    
    private boolean stop;
    
    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public int getLength() {
        return (int) Math.sqrt((p1.getLatitude() - p1.getLongitude()) + (p2.getLatitude() - p2.getLongitude()));
    }
    
    public void setStopToTrue() {
        stop = true;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public boolean isStop() {
        return stop;
    }
}
