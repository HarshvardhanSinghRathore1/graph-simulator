package com.graphsim.model;

import java.util.List;

public class GraphRequest {
    private int numVertices;
    private List<Edge> edges;
    private int startNode;
    private Integer targetNode; // Optional for Dijkstra specific paths
    private boolean isDirected;

    public GraphRequest() {}

    public int getNumVertices() { return numVertices; }
    public void setNumVertices(int numVertices) { this.numVertices = numVertices; }

    public List<Edge> getEdges() { return edges; }
    public void setEdges(List<Edge> edges) { this.edges = edges; }

    public int getStartNode() { return startNode; }
    public void setStartNode(int startNode) { this.startNode = startNode; }

    public boolean isDirected() { return isDirected; }
    public void setDirected(boolean directed) { isDirected = directed; }

    public Integer getTargetNode() { return targetNode; }
    public void setTargetNode(Integer targetNode) { this.targetNode = targetNode; }
}
