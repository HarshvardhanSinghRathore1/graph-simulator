package com.graphsim.controller;

import com.graphsim.model.GraphRequest;
import com.graphsim.model.TraversalResponse;
import com.graphsim.service.GraphService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graph")
@CrossOrigin(origins = "*")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping("/create")
    public TraversalResponse createGraph(@RequestBody GraphRequest request) {
        return graphService.createGraph(request);
    }

    @PostMapping("/bfs")
    public TraversalResponse runBFS(@RequestBody GraphRequest request) {
        return graphService.runBFS(request);
    }

    @PostMapping("/dfs")
    public TraversalResponse runDFS(@RequestBody GraphRequest request) {
        return graphService.runDFS(request);
    }

    @PostMapping("/dijkstra")
    public TraversalResponse runDijkstra(@RequestBody GraphRequest request) {
        return graphService.runDijkstra(request);
    }
}
