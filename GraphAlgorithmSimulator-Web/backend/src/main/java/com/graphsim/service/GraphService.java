package com.graphsim.service;

import com.graphsim.algorithms.BFS;
import com.graphsim.algorithms.DFS;
import com.graphsim.algorithms.Dijkstra;
import com.graphsim.model.Edge;
import com.graphsim.model.Graph;
import com.graphsim.model.GraphRequest;
import com.graphsim.model.TraversalResponse;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private Graph buildGraphFromRequest(GraphRequest request) {
        if (request.getNumVertices() <= 0) {
            throw new IllegalArgumentException("Number of vertices must be greater than 0");
        }
        
        Graph graph = new Graph(request.getNumVertices(), request.isDirected());
        if (request.getEdges() != null) {
            for (Edge edge : request.getEdges()) {
                graph.addEdge(edge.getU(), edge.getV(), edge.getWeight());
            }
        }
        return graph;
    }

    public TraversalResponse createGraph(GraphRequest request) {
        try {
            buildGraphFromRequest(request); // Validates structure
            TraversalResponse response = new TraversalResponse();
            response.setSuccess(true);
            response.setMessage("Graph constructed and validated successfully.");
            return response;
        } catch (Exception e) {
            TraversalResponse response = new TraversalResponse();
            response.setSuccess(false);
            response.setMessage("Failed to create graph: " + e.getMessage());
            return response;
        }
    }

    public TraversalResponse runBFS(GraphRequest request) {
        try {
            Graph graph = buildGraphFromRequest(request);
            return BFS.execute(graph, request.getStartNode());
        } catch (Exception e) {
            TraversalResponse response = new TraversalResponse();
            response.setSuccess(false);
            response.setMessage("BFS error: " + e.getMessage());
            return response;
        }
    }

    public TraversalResponse runDFS(GraphRequest request) {
        try {
            Graph graph = buildGraphFromRequest(request);
            return DFS.execute(graph, request.getStartNode());
        } catch (Exception e) {
            TraversalResponse response = new TraversalResponse();
            response.setSuccess(false);
            response.setMessage("DFS error: " + e.getMessage());
            return response;
        }
    }

    public TraversalResponse runDijkstra(GraphRequest request) {
        try {
            Graph graph = buildGraphFromRequest(request);
            return Dijkstra.execute(graph, request.getStartNode(), request.getTargetNode());
        } catch (Exception e) {
            TraversalResponse response = new TraversalResponse();
            response.setSuccess(false);
            response.setMessage("Dijkstra error: " + e.getMessage());
            return response;
        }
    }
}
