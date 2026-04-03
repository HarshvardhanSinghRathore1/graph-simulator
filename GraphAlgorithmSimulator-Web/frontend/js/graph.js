class GraphRenderer {
    constructor(canvasId) {
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext('2d');
        this.nodes = [];
        this.edges = [];
        this.isDirected = false;
        
        // Colors mapping from CSS variables roughly
        this.colors = {
            unvisited: '#000000', // black
            visited: '#93c5fd',   // light blue
            active: '#3b82f6',    // bright blue
            path: '#1e40af',      // dark blue
            edge: '#cbd5e1',      // light slate for edges
            text: '#ffffff'       // white text for contrast inside black nodes
        };

        this.nodeStates = {}; // Map of node ID -> state ('unvisited', 'visited', 'active', 'path')
        this.pathEdges = []; // Array of {u, v}

        this.resize();
        window.addEventListener('resize', () => this.resize());
    }

    resize() {
        const container = this.canvas.parentElement;
        this.canvas.width = container.clientWidth;
        this.canvas.height = container.clientHeight;
        if (this.nodes.length > 0) {
            this.calculateLayout(this.nodes.length);
        }
        this.draw();
    }

    setGraph(numVertices, edges, isDirected) {
        this.isDirected = isDirected;
        this.edges = edges;
        this.nodeStates = {};
        this.pathEdges = [];
        this.calculateLayout(numVertices);
        this.draw();
    }

    calculateLayout(numVertices) {
        this.nodes = [];
        const centerX = this.canvas.width / 2;
        const centerY = this.canvas.height / 2;
        const radius = Math.min(centerX, centerY) * 0.7; // Radius of the circle

        for (let i = 0; i < numVertices; i++) {
            const angle = (Math.PI * 2 * i) / numVertices - Math.PI / 2;
            this.nodes.push({
                id: i,
                x: centerX + radius * Math.cos(angle),
                y: centerY + radius * Math.sin(angle)
            });
            this.nodeStates[i] = 'unvisited';
        }
    }

    setNodeState(nodeId, state) {
        this.nodeStates[nodeId] = state;
    }

    setPathEdges(pathNodes) {
        this.pathEdges = [];
        for (let i = 0; i < pathNodes.length - 1; i++) {
            this.pathEdges.push({u: pathNodes[i], v: pathNodes[i+1]});
        }
        pathNodes.forEach(node => this.setNodeState(node, 'path'));
    }

    resetStates() {
        for (let key in this.nodeStates) {
            this.nodeStates[key] = 'unvisited';
        }
        this.pathEdges = [];
        this.draw();
    }

    draw() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

        // Draw edges
        this.edges.forEach(edge => {
            const u = this.nodes[edge.u];
            const v = this.nodes[edge.v];
            
            if (!u || !v) return;

            // Check if this edge is part of the path
            const isPath = this.pathEdges.some(pe => 
                (pe.u === edge.u && pe.v === edge.v) || 
                (!this.isDirected && pe.u === edge.v && pe.v === edge.u)
            );

            this.ctx.beginPath();
            this.ctx.moveTo(u.x, u.y);
            this.ctx.lineTo(v.x, v.y);
            this.ctx.strokeStyle = isPath ? this.colors.path : this.colors.edge;
            this.ctx.lineWidth = isPath ? 4 : 2;
            this.ctx.stroke();

            // Draw weight
            const midX = (u.x + v.x) / 2;
            const midY = (u.y + v.y) / 2;
            
            this.ctx.fillStyle = '#bbf7d0'; // light green background for text cutoff
            this.ctx.beginPath();
            this.ctx.arc(midX, midY, 12, 0, Math.PI * 2);
            this.ctx.fill();

            this.ctx.fillStyle = '#000000'; // black text
            this.ctx.font = 'bold 12px Arial';
            this.ctx.textAlign = 'center';
            this.ctx.textBaseline = 'middle';
            this.ctx.fillText(edge.weight, midX, midY);

            // Draw arrow for directed, slightly offset from center
            if (this.isDirected) {
                const angle = Math.atan2(v.y - u.y, v.x - u.x);
                const arrowX = v.x - Math.cos(angle) * 25; // Stop before node
                const arrowY = v.y - Math.sin(angle) * 25;
                
                this.ctx.beginPath();
                this.ctx.moveTo(arrowX, arrowY);
                this.ctx.lineTo(arrowX - 10 * Math.cos(angle - Math.PI/6), arrowY - 10 * Math.sin(angle - Math.PI/6));
                this.ctx.lineTo(arrowX - 10 * Math.cos(angle + Math.PI/6), arrowY - 10 * Math.sin(angle + Math.PI/6));
                this.ctx.fillStyle = isPath ? this.colors.path : this.colors.edge;
                this.ctx.fill();
            }
        });

        // Draw nodes
        this.nodes.forEach(node => {
            const state = this.nodeStates[node.id] || 'unvisited';
            
            this.ctx.beginPath();
            this.ctx.arc(node.x, node.y, 20, 0, Math.PI * 2);
            this.ctx.fillStyle = this.colors[state];
            this.ctx.fill();
            
            if (state === 'active') {
                this.ctx.lineWidth = 3;
                this.ctx.strokeStyle = '#fff';
                this.ctx.stroke();
            }

            this.ctx.fillStyle = this.colors.text;
            this.ctx.font = '14px Arial';
            this.ctx.textAlign = 'center';
            this.ctx.textBaseline = 'middle';
            this.ctx.fillText(node.id, node.x, node.y);
        });
    }
}
