/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ay
 */
public class Edge implements Comparable<Edge> {

    private final Vertex p1;
    private final Vertex p2;

    private boolean stop;

    public Edge(Vertex p1, Vertex p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public int getLength() {
        return (int) Math.sqrt(Math.pow(p1.getLatitude() - p1.getLongitude(), 2) + Math.pow(p2.getLatitude() - p2.getLongitude(), 2));
    }

    public void setStopToTrue() {
        stop = true;
    }

    public Vertex getP1() {
        return p1;
    }

    public Vertex getP2() {
        return p2;
    }

    public boolean isStop() {
        return stop;
    }
    
    @Override
    public int compareTo(Edge e) {
        return p1.getId() - e.getP1().getId();
    }
}
