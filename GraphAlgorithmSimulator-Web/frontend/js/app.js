document.addEventListener('DOMContentLoaded', () => {
    const renderer = new GraphRenderer('graphCanvas');
    
    // UI Elements
    const numVerticesInput = document.getElementById('numVertices');
    const edgesInput = document.getElementById('edges');
    const isDirectedInput = document.getElementById('isDirected');
    const startNodeInput = document.getElementById('startNode');
    const targetNodeInput = document.getElementById('targetNode');
    const animSpeedInput = document.getElementById('animSpeed');
    
    const serverStatusDot = document.getElementById('serverStatusDot');
    const serverStatusText = document.getElementById('serverStatusText');
    const logsContent = document.getElementById('logsContent');
    const loadingOverlay = document.getElementById('loadingOverlay');
    
    let currentAnimation = null;
    let baseGraphRequest = null;

    function log(message, type = 'info') {
        const el = document.createElement('p');
        el.className = `log-entry ${type}`;
        el.textContent = `> ${message}`;
        logsContent.appendChild(el);
        logsContent.scrollTop = logsContent.scrollHeight;
    }

    function parseEdges(text) {
        const lines = text.trim().split('\n');
        const edges = [];
        for (let line of lines) {
            const parts = line.trim().split(/\s+/);
            if (parts.length >= 3) {
                edges.push({
                    u: parseInt(parts[0]),
                    v: parseInt(parts[1]),
                    weight: parseInt(parts[2])
                });
            }
        }
        return edges;
    }

    function buildGraphRequest() {
        const numVer = parseInt(numVerticesInput.value);
        if (isNaN(numVer) || numVer <= 0) {
            log('Invalid number of vertices', 'error');
            return null;
        }

        const edges = parseEdges(edgesInput.value);
        const req = {
            numVertices: numVer,
            edges: edges,
            startNode: parseInt(startNodeInput.value) || 0,
            targetNode: parseInt(targetNodeInput.value) || null,
            directed: isDirectedInput.checked
        };
        return req;
    }

    function stopAnimation() {
        if (currentAnimation) {
            clearTimeout(currentAnimation);
            currentAnimation = null;
        }
    }

    async function handleApiCall(apiFunc, requestData, algoName) {
        if (!requestData) return;
        
        stopAnimation();
        renderer.resetStates();
        loadingOverlay.classList.remove('hidden');
        log(`Starting ${algoName}...`, 'system');

        try {
            const response = await apiFunc(requestData);
            loadingOverlay.classList.add('hidden');

            if (!response.success) {
                log(`Error: ${response.message}`, 'error');
                return;
            }

            log(`${algoName} successful. Animating...`, 'success');
            animateTraversal(response.steps, response.shortestPath);
            
        } catch (error) {
            loadingOverlay.classList.add('hidden');
            log(`Failed to connect to backend: ${error.message}`, 'error');
            serverStatusDot.className = 'status-dot red';
            serverStatusText.textContent = 'Server Offline';
        }
    }

    function animateTraversal(steps, finalPath) {
        let stepIndex = 0;
        
        function nextStep() {
            if (stepIndex >= steps.length) {
                renderer.resetStates(); // Clear active highlights
                // Mark all visited in end state
                if (steps.length > 0) {
                    const lastStep = steps[steps.length - 1];
                    lastStep.visitedNodes.forEach(node => {
                        renderer.setNodeState(node, 'visited');
                    });
                }
                
                if (finalPath && finalPath.length > 0) {
                    renderer.setPathEdges(finalPath);
                    log(`Shortest path finalized: ${finalPath.join(' -> ')}`, 'path');
                }
                renderer.draw();
                log('Animation complete.', 'success');
                return;
            }

            const step = steps[stepIndex];
            
            // Rebuild visual state for this step
            renderer.resetStates();
            step.visitedNodes.forEach(node => {
                renderer.setNodeState(node, 'visited');
            });
            renderer.setNodeState(step.currentNode, 'active');
            renderer.draw();

            log(step.description);

            const speed = parseInt(animSpeedInput.value) || 800;
            currentAnimation = setTimeout(nextStep, speed);
            stepIndex++;
        }

        nextStep();
    }

    // Event Listeners
    document.getElementById('btnCreateGraph').addEventListener('click', async () => {
        const req = buildGraphRequest();
        if(!req) return;
        
        baseGraphRequest = req;
        renderer.setGraph(req.numVertices, req.edges, req.directed);
        log(`Graph created with ${req.numVertices} vertices and ${req.edges.length} edges.`, 'success');

        try {
            const resp = await API.createGraph(req);
            if(resp.success) {
                serverStatusDot.className = 'status-dot green';
                serverStatusText.textContent = 'System Ready';
            } else {
                log(resp.message, 'error');
            }
        } catch (e) {
            log('Backend not reachable.', 'error');
            serverStatusDot.className = 'status-dot red';
            serverStatusText.textContent = 'Server Offline';
        }
    });

    document.getElementById('btnBFS').addEventListener('click', () => {
        handleApiCall(API.runBFS, buildGraphRequest(), 'BFS');
    });

    document.getElementById('btnDFS').addEventListener('click', () => {
        handleApiCall(API.runDFS, buildGraphRequest(), 'DFS');
    });

    document.getElementById('btnDijkstra').addEventListener('click', () => {
        handleApiCall(API.runDijkstra, buildGraphRequest(), 'Dijkstra');
    });

    document.getElementById('btnReset').addEventListener('click', () => {
        stopAnimation();
        renderer.resetStates();
        log('Simulation reset. Setup new graph or rerun algorithm.', 'info');
    });

    document.getElementById('btnClearLogs').addEventListener('click', () => {
        logsContent.innerHTML = '';
        log('Logs cleared.', 'system');
    });

    // Initialize Default Graph explicitly on startup
    setTimeout(() => {
        document.getElementById('btnCreateGraph').click();
    }, 500);
});
