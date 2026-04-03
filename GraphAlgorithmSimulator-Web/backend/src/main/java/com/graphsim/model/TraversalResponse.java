package com.graphsim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TraversalResponse {
    private boolean success;
    private String message;
    private List<TraversalStep> steps;
    
    // Specifically for Dijkstra
    private Map<Integer, Integer> distances;
    private List<Integer> shortestPath; // Can be empty for BFS/DFS without a target

    public TraversalResponse() {
        this.steps = new ArrayList<>();
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<TraversalStep> getSteps() { return steps; }
    public void setSteps(List<TraversalStep> steps) { this.steps = steps; }

    public Map<Integer, Integer> getDistances() { return distances; }
    public void setDistances(Map<Integer, Integer> distances) { this.distances = distances; }

    public List<Integer> getShortestPath() { return shortestPath; }
    public void setShortestPath(List<Integer> shortestPath) { this.shortestPath = shortestPath; }
}
