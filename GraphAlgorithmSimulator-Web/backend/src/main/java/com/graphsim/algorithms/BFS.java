package com.graphsim.algorithms;

import com.graphsim.model.*;
import java.util.*;

public class BFS {
    public static TraversalResponse execute(Graph graph, int startNode) {
        TraversalResponse response = new TraversalResponse();
        List<TraversalStep> steps = new ArrayList<>();
        boolean[] visited = new boolean[graph.getNumVertices()];
        Queue<Integer> queue = new LinkedList<>();

        // Validate start node
        if (startNode < 0 || startNode >= graph.getNumVertices()) {
            response.setSuccess(false);
            response.setMessage("Invalid start node.");
            return response;
        }

        queue.add(startNode);
        visited[startNode] = true;
        List<Integer> visitedOrder = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            visitedOrder.add(current);
            steps.add(new TraversalStep(current, new ArrayList<>(visitedOrder), "Visited node " + current));

            for (Edge edge : graph.getAdjList().get(current)) {
                int neighbor = edge.getV();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                    steps.add(new TraversalStep(current, new ArrayList<>(visitedOrder), "Found neighbor " + neighbor + " of node " + current));
                }
            }
        }
        
        response.setSteps(steps);
        response.setSuccess(true);
        response.setMessage("BFS completed.");
        return response;
    }
}
