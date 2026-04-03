package com.graphsim.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int numVertices;
    private List<List<Edge>> adjList;
    private boolean isDirected;

    public Graph(int numVertices, boolean isDirected) {
        this.numVertices = numVertices;
        this.isDirected = isDirected;
        this.adjList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        if (u >= 0 && u < numVertices && v >= 0 && v < numVertices) {
            adjList.get(u).add(new Edge(u, v, weight));
            if (!isDirected) {
                adjList.get(v).add(new Edge(v, u, weight));
            }
        }
    }

    public int getNumVertices() { return numVertices; }
    public List<List<Edge>> getAdjList() { return adjList; }
    public boolean isDirected() { return isDirected; }
}
