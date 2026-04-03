package com.graphsim.algorithms;

import com.graphsim.model.*;
import java.util.*;

public class Dijkstra {
    public static TraversalResponse execute(Graph graph, int startNode, Integer targetNode) {
        TraversalResponse response = new TraversalResponse();
        List<TraversalStep> steps = new ArrayList<>();
        int n = graph.getNumVertices();
        
        if (startNode < 0 || startNode >= n || (targetNode != null && (targetNode < 0 || targetNode >= n))) {
            response.setSuccess(false);
            response.setMessage("Invalid start or target node.");
            return response;
        }

        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        
        dist[startNode] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{startNode, 0});
        
        List<Integer> visitedOrder = new ArrayList<>();
        boolean[] finalized = new boolean[n];

        while (!pq.isEmpty()) {
            int[] currentArr = pq.poll();
            int u = currentArr[0];
            int d = currentArr[1];
            
            if (finalized[u]) continue;
            finalized[u] = true;
            visitedOrder.add(u);
            
            steps.add(new TraversalStep(u, new ArrayList<>(visitedOrder), "Finalized shortest distance for node " + u + " as " + d));
            
            if (targetNode != null && u == targetNode) {
                break; // Early exit if we reached target
            }

            for (Edge edge : graph.getAdjList().get(u)) {
                int v = edge.getV();
                int weight = edge.getWeight();
                
                if (!finalized[v] && dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new int[]{v, dist[v]});
                    steps.add(new TraversalStep(u, new ArrayList<>(visitedOrder), "Relaxed edge (" + u + " -> " + v + "), new distance = " + dist[v]));
                }
            }
        }
        
        // Reconstruct path if target is provided and reachable
        List<Integer> shortestPath = new ArrayList<>();
        if (targetNode != null && dist[targetNode] != Integer.MAX_VALUE) {
            int curr = targetNode;
            while (curr != -1) {
                shortestPath.add(0, curr);
                curr = parent[curr];
            }
        }
        
        Map<Integer, Integer> distances = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (dist[i] != Integer.MAX_VALUE) {
                distances.put(i, dist[i]);
            }
        }

        response.setSteps(steps);
        response.setDistances(distances);
        response.setShortestPath(shortestPath);
        response.setSuccess(true);
        response.setMessage("Dijkstra completed.");
        return response;
    }
}
