package com.graphsim.model;

public class Edge {
    private int u;
    private int v;
    private int weight;

    public Edge() {}

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public int getU() { return u; }
    public void setU(int u) { this.u = u; }

    public int getV() { return v; }
    public void setV(int v) { this.v = v; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
}
