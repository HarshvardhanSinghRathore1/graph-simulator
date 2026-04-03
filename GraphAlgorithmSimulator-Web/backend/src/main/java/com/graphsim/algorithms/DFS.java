package com.graphsim.algorithms;

import com.graphsim.model.*;
import java.util.*;

public class DFS {
    public static TraversalResponse execute(Graph graph, int startNode) {
        TraversalResponse response = new TraversalResponse();
        List<TraversalStep> steps = new ArrayList<>();
        
        if (startNode < 0 || startNode >= graph.getNumVertices()) {
            response.setSuccess(false);
            response.setMessage("Invalid start node.");
            return response;
        }

        boolean[] visited = new boolean[graph.getNumVertices()];
        List<Integer> visitedOrder = new ArrayList<>();

        dfsRecursive(graph, startNode, visited, visitedOrder, steps, -1);

        response.setSteps(steps);
        response.setSuccess(true);
        response.setMessage("DFS completed.");
        return response;
    }

    private static void dfsRecursive(Graph graph, int current, boolean[] visited, List<Integer> visitedOrder, List<TraversalStep> steps, int parent) {
        visited[current] = true;
        visitedOrder.add(current);
        steps.add(new TraversalStep(current, new ArrayList<>(visitedOrder), "Visited node " + current + (parent != -1 ? " from " + parent : "")));

        for (Edge edge : graph.getAdjList().get(current)) {
            int neighbor = edge.getV();
            if (!visited[neighbor]) {
                dfsRecursive(graph, neighbor, visited, visitedOrder, steps, current);
            }
        }
        
        steps.add(new TraversalStep(current, new ArrayList<>(visitedOrder), "Backtracking from node " + current));
    }
}
