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

    private final Vertice p1;
    private final Vertice p2;

    private boolean stop;

    public Edge(Vertice p1, Vertice p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public int getLength() {
        return (int) Math.sqrt(Math.pow(p1.getLatitude() - p1.getLongitude(), 2) + Math.pow(p2.getLatitude() - p2.getLongitude(), 2));
    }

    public void setStopToTrue() {
        stop = true;
    }

    public Vertice getP1() {
        return p1;
    }

    public Vertice getP2() {
        return p2;
    }

    public boolean isStop() {
        return stop;
    }
}
