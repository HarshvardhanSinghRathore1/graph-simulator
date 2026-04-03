package com.graphsim.model;

import java.util.ArrayList;
import java.util.List;

public class TraversalStep {
    private int currentNode;
    private List<Integer> visitedNodes;
    private String description;

    public TraversalStep() {
        this.visitedNodes = new ArrayList<>();
    }

    public TraversalStep(int currentNode, List<Integer> visitedNodes, String description) {
        this.currentNode = currentNode;
        this.visitedNodes = new ArrayList<>(visitedNodes);
        this.description = description;
    }

    public int getCurrentNode() { return currentNode; }
    public void setCurrentNode(int currentNode) { this.currentNode = currentNode; }

    public List<Integer> getVisitedNodes() { return visitedNodes; }
    public void setVisitedNodes(List<Integer> visitedNodes) { this.visitedNodes = visitedNodes; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
